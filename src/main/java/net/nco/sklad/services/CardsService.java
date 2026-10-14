package net.nco.sklad.services;

import jakarta.persistence.criteria.Predicate;
import net.nco.sklad.dto.CardDTO;
import net.nco.sklad.entity.documents.Card;
import net.nco.sklad.repository.CardRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CardsService {
    private final CardRepository cardRepository;

    public CardsService(CardRepository cardRepo) {
        this.cardRepository = cardRepo;
    }

    public List<CardDTO> search(Long cardNumber, String denom, Timestamp cardDate, Pageable pageable) {
        System.out.println(cardNumber + " " + denom + " " + cardDate);
        List<Card> listCards = cardRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cardNumber != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.toString(root.get("cardNumber")),
                                criteriaBuilder.literal("%" + cardNumber + "%")
                        )
                );
            }

            if(denom != null && !denom.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("denom")), "%" + denom.toLowerCase() + "%")
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
        return listCards.stream()
                .map(
                        card -> new CardDTO(
                            card.getId(),
                            card.getCardNumber(),
                            card.getQtyPS(),
                            card.getQtyBTS(),
                            card.getPrice(),
                            card.getNomenclature() != null ? card.getNomenclature().getId() : 0,
                            "---",
                            card.getCategory()
                        )
                ).collect(Collectors.toList());
    }
}//end class
