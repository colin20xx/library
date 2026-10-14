package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "invoice_movements")
public class InvoiceMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "denom_id")
    private Integer idDemonination;

    @Column(name = "nomencl_id")
    private long idNomenclature;

    @Column(name = "qty_bts", precision = 13, scale = 5, nullable = false)
    private BigDecimal qtyBTS;

    @Column(name = "qty_ps", precision = 13, scale = 5, nullable = false)
    private BigDecimal qtyPS;

    @Column(name = "quantity", precision = 13, scale = 5, nullable = false)
    private BigDecimal quantity;



    @Column(precision = 13, scale = 5, nullable = false)
    private BigDecimal price;

    @Column
    @Min(1)
    @Max(5)
    private Integer category;

    private LocalDateTime createdAt;

    private LocalDateTime operationDate;

    @Column(name = "commited_at")
    private LocalDateTime commitedAt;

    @Column(name = "card_id")
    private Long idCard;

    @Column(name = "card_number")
    private Long cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Denomination denomination;

    @ManyToOne(fetch = FetchType.LAZY)
    private Nomenclature nomenclature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "im_cards",
            joinColumns = @JoinColumn(name = "im_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<Card>();

    public InvoiceMovement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDemonination() {
        return idDemonination;
    }

    public void setIdDemonination(Integer idDemonination) {
        this.idDemonination = idDemonination;
    }

    public long getIdNomenclature() {
        return idNomenclature;
    }

    public void setIdNomenclature(long idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    public LocalDateTime getCommitedAt() {
        return commitedAt;
    }

    public void setCommitedAt(LocalDateTime commitedAt) {
        this.commitedAt = commitedAt;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
        card.getInvoiceMovements().add(this);
    }

    public void removeCard(Long idCard) {
        Card card = this.cards.stream().filter(c -> c.getId().equals(idCard)).findFirst().orElse(null);
        if(card != null) {
            this.cards.remove(card);
            card.getInvoiceMovements().remove(this);
        }
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public @Min(1) @Max(5) Integer getCategory() {
        return category;
    }

    public void setCategory(@Min(1) @Max(5) Integer category) {
        this.category = category;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InvoiceMovement{" +
                "id=" + id +
                ", idDemonination=" + idDemonination +
                ", idNomenclature=" + idNomenclature +
                ", createdAt=" + createdAt +
                ", operationDate=" + operationDate +
                ", commitedAt=" + commitedAt +
                ", idCard=" + idCard +
                ", invoice=" + invoice +
                ", cards=" + cards +
                '}';
    }
}
