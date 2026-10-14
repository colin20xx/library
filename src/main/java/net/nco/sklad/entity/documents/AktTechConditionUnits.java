package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_akt_tech_condition_units")
public class AktTechConditionUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;

    @Column
    @Min(1)
    @Max(5)
    private Integer category;

    @Column(name = "qty_bts", precision = 13, scale = 5)
    private BigDecimal qtyBTS;

    @Column(name = "qty_ps", precision = 13, scale = 5)
    private BigDecimal qtyPS;

    @Column(name = "quantity", precision = 13, scale = 5, nullable = false)
    private BigDecimal quantity;

    @Column(name = "price", precision = 13, scale = 5, nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private AktTechCondition atc;

    @ManyToOne(fetch = FetchType.LAZY)
    private Denomination denomination;

    @ManyToOne(fetch = FetchType.LAZY)
    private Nomenclature nomenclature;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "atcu_cards",
            joinColumns = @JoinColumn(name = "atcu_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<Card>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public @Min(1) @Max(5) Integer getCategory() {
        return category;
    }

    public void setCategory(@Min(1) @Max(5) Integer category) {
        this.category = category;
    }

    public BigDecimal getQtyBTS() {
        return qtyBTS;
    }

    public void setQtyBTS(BigDecimal qtyBTS) {
        this.qtyBTS = qtyBTS;
    }

    public BigDecimal getQtyPS() {
        return qtyPS;
    }

    public void setQtyPS(BigDecimal qtyPS) {
        this.qtyPS = qtyPS;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public AktTechCondition getAktTechCondition() {
        return atc;
    }

    public void setAktTechCondition(AktTechCondition aktTechCondition) {
        this.atc = aktTechCondition;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public AktTechCondition getAtc() {
        return atc;
    }

    public void setAtc(AktTechCondition atc) {
        this.atc = atc;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.getAtcUnits().add(this);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void removeCard(Long idCard) {
        Card card = this.cards.stream().filter(c -> c.getId().equals(idCard)).findFirst().orElse(null);
        if(card != null) {
            this.cards.remove(card);
            card.getAtcUnits().remove(this);
        }
    }
}//end class
