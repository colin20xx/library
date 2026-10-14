package net.nco.sklad.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.nco.sklad.common.CommitAction;
import net.nco.sklad.dto.ATCUnitDTO;
import net.nco.sklad.entity.documents.AktTechCondition;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.entity.history.DocumentCommitHistory;
import net.nco.sklad.repository.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommitService {

    @PersistenceContext
    private EntityManager em;
    private final CardRepository cardRepository;
    private final ATCUnitsRepository atcUnitsRepository;
    private final AktTechConditionRepository aktTechConditionRepository;
    private final DocumentCommitHistoryRepository documentCommitHistoryRepository;
    private Card tempCard;
    private AktTechCondition tempAtc;
    private DocumentCommitHistory tempDocumentCommitHistory;

    private final Map<Class<? extends CommittableDocument>, DocumentRecalculate> documentRecalculateMap = new HashMap<>();

    public CommitService(CardRepository cardRepo,  AktTechConditionRepository atcRepo,ATCUnitsRepository atcUnitsRepo, List<DocumentRecalculate<?>> recalculates, DocumentCommitHistoryRepository docCommitHistoryRepo) {
        this.cardRepository = cardRepo;
        this.atcUnitsRepository = atcUnitsRepo;
        this.aktTechConditionRepository = atcRepo;
        this.documentCommitHistoryRepository = docCommitHistoryRepo;

        for(DocumentRecalculate<?> recalculate : recalculates) {
            System.out.println("[sklad]--------------->" + getClass() + " > " + recalculate.getSupportedClass().toString() + " > " + recalculate);
            documentRecalculateMap.put(recalculate.getSupportedClass(), recalculate);
        }
    }

    @Transactional
    public <T extends CommittableDocument>T commit(T document, JpaRepository<T, Long> repository) {
        if(document.isCommitted()){throw new IllegalStateException("Document is already committed");}

        recalculate(document, true);
        document.setCommittedAt(LocalDateTime.now());
        return repository.save(document);
    }

    @Transactional
    public <T extends CommittableDocument>T unCommit(T document, JpaRepository<T, Long> repository) {

        if(document.isCommitted()){ throw new IllegalStateException("Document is already committed"); }

        recalculate(document, true);
        document.setCommittedAt(null);
//TODO        documentCommitHistoryRepository.save();
        return repository.save(document);
    }

    @SuppressWarnings("unchecked")
    private <T extends CommittableDocument> void recalculate(T document, boolean isCommiting) {
        DocumentRecalculate<T> recalculator = documentRecalculateMap.get(document.getClass());
        if (recalculator != null) {
            recalculator.recalculate(document, isCommiting);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------[ old methods ]-----

    @Transactional
    public int setCommitedAtByDocIdNative(String tableName, Long docId, LocalDateTime commitDate){
        System.out.println("---------------------> tableName:" + tableName + " docId:" + docId);
        if(commitDate != null){
            tempAtc = aktTechConditionRepository.getReferenceById(docId);

            List<ATCUnitDTO> atcUnitDTOList = atcUnitsRepository.findAllByAtcID(docId);

            switch (tempAtc.getReceiver().getIdDep()){
                case 71:
                    System.out.println("----------------> receiver PAS+");
                    atcUnitDTOList.forEach(atcUnitDTO -> {
                        addQuantityToPSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                    });
                    break;
                case 72:
                    System.out.println("----------------> receiver BTS+");
                    atcUnitDTOList.forEach(atcUnitDTO -> {
                        addQuantityToBTSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                    });
                    break;
            }

            switch (tempAtc.getSender().getIdDep()){
                case 71:
                    System.out.println("----------------> sender PAS-");
                    atcUnitDTOList.forEach(atcUnitDTO -> {
                        cutQuantityFromPSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                    });
                    break;
                case 72:
                    System.out.println("----------------> sender BTS-");
                    atcUnitDTOList.forEach(atcUnitDTO -> {
                        cutQuantityFromBTSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                    });
                    break;
            }

        }//end if

        tempDocumentCommitHistory = new DocumentCommitHistory();
        tempDocumentCommitHistory.setCommitDateTime(commitDate);
        tempDocumentCommitHistory.setDocumentType(tempAtc.getDocumentsType());
        tempDocumentCommitHistory.setComment(tempAtc.getComments());
        tempDocumentCommitHistory.setDocumentId(tempAtc.getId());
        tempDocumentCommitHistory.setRecordDateTime(LocalDateTime.now());
        tempDocumentCommitHistory.setCommitAction(CommitAction.commit);

        documentCommitHistoryRepository.save(tempDocumentCommitHistory);




        String queryStr = """
                UPDATE sklad.%s SET commited_at = :committedAt WHERE id = :docId""".formatted(tableName);
        return em.createNativeQuery(queryStr).setParameter("committedAt", commitDate).setParameter("docId", docId).executeUpdate();

    }//end method

    @Transactional
    public int setUnCommitedAtByIdNative(String tableName, Long docId){

        System.out.println("---------------------> tableName:" + tableName + " docId:" + docId);

        tempAtc = aktTechConditionRepository.getReferenceById(docId);

        List<ATCUnitDTO> atcUnitDTOList = atcUnitsRepository.findAllByAtcID(docId);

        switch (tempAtc.getReceiver().getIdDep()){
            case 71:
                System.out.println("----------------> receiver PAS-");
                atcUnitDTOList.forEach(atcUnitDTO -> {
                    cutQuantityFromPSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                });
                break;
            case 72:
                System.out.println("----------------> receiver BTS-");
                atcUnitDTOList.forEach(atcUnitDTO -> {
                    cutQuantityFromBTSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                });
                break;
        }

        switch (tempAtc.getSender().getIdDep()){
            case 71:
                System.out.println("----------------> sender PAS-");
                atcUnitDTOList.forEach(atcUnitDTO -> {
                    cutQuantityFromPSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                });
                break;
            case 72:
                System.out.println("----------------> sender BTS-");
                atcUnitDTOList.forEach(atcUnitDTO -> {
                    cutQuantityFromBTSofCardID(atcUnitDTO.getCardID(), atcUnitDTO.getQuantity().doubleValue());
                });
                break;
        }

        tempDocumentCommitHistory = new DocumentCommitHistory();
        tempDocumentCommitHistory.setCommitDateTime(tempAtc.getCommittedAt());
        tempDocumentCommitHistory.setDocumentType(tempAtc.getDocumentsType());
        tempDocumentCommitHistory.setComment(tempAtc.getComments());
        tempDocumentCommitHistory.setDocumentId(tempAtc.getId());
        tempDocumentCommitHistory.setRecordDateTime(LocalDateTime.now());
        tempDocumentCommitHistory.setCommitAction(CommitAction.uncommit);
        documentCommitHistoryRepository.save(tempDocumentCommitHistory);

        String queryStr = """
                UPDATE sklad.%s SET commited_at = null WHERE id = :docId""".formatted(tableName);
        return em.createNativeQuery(queryStr).setParameter("docId", docId).executeUpdate();
    }//end method



    @Transactional
    public void addQuantityToPSofCardID(Long cardID, double qty){
        tempCard = cardRepository.getReferenceById(cardID);
        tempCard.setQtyPS(tempCard.getQtyPS() + qty);
        tempCard.setTotalQty(tempCard.getTotalQty() + qty);
        tempCard.setTotalSum(tempCard.getPrice().multiply(BigDecimal.valueOf(tempCard.getTotalQty())));
        tempCard.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(tempCard);
    }

    @Transactional
    public void cutQuantityFromPSofCardID(Long cardID, double qty){
        tempCard = cardRepository.getReferenceById(cardID);
        tempCard.setQtyPS(tempCard.getQtyPS() - qty);
        tempCard.setTotalQty(tempCard.getTotalQty() - qty);
        tempCard.setTotalSum(tempCard.getPrice().multiply(BigDecimal.valueOf(tempCard.getTotalQty())));
        tempCard.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(tempCard);
    }

    @Transactional
    public void addQuantityToBTSofCardID(Long cardID, double qty){
        tempCard = cardRepository.getReferenceById(cardID);
        tempCard.setQtyBTS(tempCard.getQtyBTS() + qty);
        tempCard.setTotalQty(tempCard.getTotalQty() + qty);
        tempCard.setTotalSum(tempCard.getPrice().multiply(BigDecimal.valueOf(tempCard.getTotalQty())));
        tempCard.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(tempCard);
    }

    @Transactional
    public void cutQuantityFromBTSofCardID(Long cardID, double qty){
        tempCard = cardRepository.getReferenceById(cardID);
        tempCard.setQtyBTS(tempCard.getQtyBTS() - qty);
        tempCard.setTotalQty(tempCard.getTotalQty() - qty);
        tempCard.setTotalSum(tempCard.getPrice().multiply(BigDecimal.valueOf(tempCard.getTotalQty())));
        tempCard.setUpdatedAt(LocalDateTime.now());
        cardRepository.save(tempCard);
    }

}//end class
