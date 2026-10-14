package net.nco.sklad.controller;

import net.nco.sklad.common.CardStatus;
import net.nco.sklad.dto.CardDTO;
import net.nco.sklad.dto.CardMovementDTO;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.repository.CardRepository;
import net.nco.sklad.repository.DenominationRepository;
import net.nco.sklad.repository.NomenclatureRepository;
import net.nco.sklad.repository.ObjectsMilitaryRepository;
import net.nco.sklad.services.CardsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CardController {

    private final ObjectsMilitaryRepository objectsMilitaryRepository;
    private CardRepository cardRepository;
    private DenominationRepository denominationRepository;
    private NomenclatureRepository nomenclatureRepository;
    private CardsService cardsService;
    private Long tempCardNumber;
    private ArrayList<CardMovementDTO> arrListCardMovement;

    public CardController(CardRepository cardRepo, DenominationRepository denomRepo, NomenclatureRepository nomRepo, ObjectsMilitaryRepository objectsMilitaryRepo, CardsService cardsService) {
        this.cardRepository = cardRepo;
        this.cardsService = cardsService;
        this.denominationRepository = denomRepo;
        this.nomenclatureRepository = nomRepo;
        this.objectsMilitaryRepository = objectsMilitaryRepo;
    }

    @GetMapping("/cards/search")
    public ResponseEntity <List<CardDTO>> cardsSearch(@PageableDefault(size = 10) Pageable pageable,
                                @RequestParam(required = false) Long field_search_app_number,
                                @RequestParam(required = false) String field_search_denomination
                                ,Model model) {
        return ResponseEntity.ok(cardsService.search(field_search_app_number, field_search_denomination, null, pageable));
    }

    @GetMapping("/cards/list")
    public String cardsList(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Card> cards = cardRepository.findAll(pageable);
        model.addAttribute("cards", cards);

        int currentPage = cards.getNumber();
        int totalPages = cards.getTotalPages();
        long totalCards = cards.getTotalElements();

        int startPage = Math.max(0, currentPage -2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalCards);

        return "cards/cards_common_list";
    }



    @PostMapping("/cards/ajax/saveNewCard")
//    @ModelAttribute("card") CardDTO cardDTO, Model model
    public ResponseEntity<Long> saveNewCard(@RequestParam("id_denom") int idDenom, @RequestParam("id_nomenclature") Long idNomenclature) {
        Card card = new Card();
        if(cardRepository.findTopByOrderByCardNumberDesc() == null) {
            card.setCardNumber(1L);
        }else card.setCardNumber(cardRepository.findTopByOrderByCardNumberDesc().getCardNumber()+1L);

        card.setCardDateTime(LocalDateTime.now());
        card.setCreatedAt(LocalDateTime.now());
        card.setDenomination(denominationRepository.findById(idDenom).get());
        card.setNomenclature(nomenclatureRepository.findById(idNomenclature).get());

        cardRepository.save(card);
        return ResponseEntity.ok(card.getCardNumber());
    }

    @GetMapping("/new_card_form")
    public String newCardForm(Model model, @RequestParam("doc_type") String docType) {
        Card card = new Card();
        card.setCardNumber(1L);
        card.setPrice(BigDecimal.valueOf(7250));
        card.setQtyBTS(10.00);
        card.setQtyPS(5.00);
        card.setComments("примітка");
        card.setDocType(18);
        model.addAttribute("new_card", card);
        model.addAttribute("denom_list", denominationRepository.getDenominationsWithOMandNomenclaturesCount(""));
        model.addAttribute("om_list", objectsMilitaryRepository.findAll());
        return "/docs/new_card_form";
    }

    @PostMapping("/doc/save_card_and_get_content_form")
    public ResponseEntity<?> saveCardAndGetContentForm(Model model,
                                                    @ModelAttribute Card new_card,
                                                    @RequestParam(name = "denom_id") int denomination_id,
                                                    @RequestParam(name= "nomencl_id", required = false) Long nomenclature_id) {
        new_card.setDenomination(denominationRepository.getReferenceById(denomination_id));
        new_card.setNomenclature(nomenclature_id!=null? nomenclatureRepository.getReferenceById(nomenclature_id):null);
        new_card.setCreatedAt(LocalDateTime.now());
        new_card.setCardStatus(CardStatus.NEW.getValue());
        new_card.setTotalQty(new_card.getTotalQty());
        new_card.setTotalSum(new_card.getTotalSum());
        new_card.setCardDateTime(LocalDateTime.now());
        new_card.setQtyDEPS(10.00);
        cardRepository.save(new_card);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/cards/ajax/list")
    public ResponseEntity<List<CardDTO>> listCards(Model model, @RequestParam(name = "denom_id") int idDenom) {
        return ResponseEntity.ok(cardRepository.findCardsByNomenclatureName(idDenom));
    }

    @GetMapping("/cards/ajax/last_card_number")
    public ResponseEntity<Long> lastCardNumber() {
        if(cardRepository.findTopByOrderByCardNumberDesc() == null) {
            return ResponseEntity.ok(1L);
        } else return ResponseEntity.ok(cardRepository.findTopByOrderByCardNumberDesc().getCardNumber());
    }

    @GetMapping("/cards/{id}/details")
    public String details(@PathVariable("id") Long id, Model model) {
        model.addAttribute("card_selected", cardRepository.getReferenceById(id));
        arrListCardMovement = (ArrayList<CardMovementDTO>)cardRepository.getCardMovements(id);

        double currentQty = 0L;

        for(int i=0; i < arrListCardMovement.size(); i++) {

            currentQty += arrListCardMovement.get(i).getQuantity().doubleValue();
            arrListCardMovement.get(i).setCurrentQuantity(BigDecimal.valueOf(currentQty));
        }
        model.addAttribute("card_movements", arrListCardMovement);

        return "cards/details";
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable() Long id) {
        if(!cardRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cardRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
