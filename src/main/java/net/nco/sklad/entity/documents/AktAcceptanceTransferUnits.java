package net.nco.sklad.entity.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;
import net.nco.sklad.entity.Unit;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document_akt_acceptance_transfer_units")
public class AktAcceptanceTransferUnits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AktAcceptanceTransfer aktAcceptanceTransfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Denomination denomination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Nomenclature nomenclature;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(
            name = "aatu_cards",
            joinColumns = @JoinColumn(name = "aatu_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<Card>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Unit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AktAcceptanceTransfer getAktAcceptanceTransfer() {
        return aktAcceptanceTransfer;
    }

    public void setAktAcceptanceTransfer(AktAcceptanceTransfer aktAcceptanceTransfer) {
        this.aktAcceptanceTransfer = aktAcceptanceTransfer;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.getAktAcceptanceTransferUnits().add(this);
    }

    public void removeCard(Long idCard) {
        Card card = this.cards.stream().filter(c -> c.getId().equals(idCard)).findFirst().orElse(null);
        if(card != null) {
            this.cards.remove(card);
            card.getAktAcceptanceTransferUnits().remove(this);
        }
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}//end class
