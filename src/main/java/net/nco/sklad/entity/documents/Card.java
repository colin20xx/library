package net.nco.sklad.entity.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import net.nco.sklad.entity.Denomination;
import net.nco.sklad.entity.Nomenclature;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cards", schema = "sklad")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card", nullable = false)
    private Long id;

    @Column(name = "card_number", nullable = false)
    private Long cardNumber;

    @Column(name = "doc_type", nullable = false)
    private int docType;

    @Column(name="date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime cardDateTime;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Column(name = "closed_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime closedAt;

    @Column(name = "card_status")
    private String cardStatus;

    @Column(name = "comments")
    private String comments;

    @Column(name = "price", precision = 16, scale = 5, nullable = false)
    private BigDecimal price;

    @Min(1)
    @Max(5)
    @Column(name = "category", nullable = false)
    private Integer category;

    @Column(name = "qty_ps")
    private Double qtyPS;

    @Column(name = "qty_bts")
    private Double qtyBTS;

    @Column(name = "qty_deps")
    private Double qtyDEPS;

    @Column(name = "total_qty")
    private double totalQty;

    @Column(name = "total_sum", precision = 16, scale = 5)
    private BigDecimal totalSum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denomination_id")
    private Denomination denomination;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomenclature_id")
    private Nomenclature nomenclature;

    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<InvoiceMovement> invoiceMovements = new HashSet<>() {};

    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AktAcceptanceTransferUnits> aktAcceptanceTransferUnits = new HashSet<>() {};

    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<AktTechConditionUnits> atcUnits = new HashSet<>() {};

    public Card() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCardDateTime() {
        return cardDateTime;
    }

    public void setCardDateTime(LocalDateTime cardDateTime) {
        this.cardDateTime = cardDateTime;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getQtyPS() {
        return qtyPS;
    }

    public void setQtyPS(Double qtyPS) {
        this.qtyPS = qtyPS;
    }

    public Double getQtyBTS() {
        return qtyBTS;
    }

    public void setQtyBTS(Double qtyBTS) {
        this.qtyBTS = qtyBTS;
    }

    public Double getQtyDEPS() {
        return qtyDEPS;
    }

    public void setQtyDEPS(Double qtyDEPS) {
        this.qtyDEPS = qtyDEPS;
    }

    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(Nomenclature nomenclature) {
        this.nomenclature = nomenclature;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public Set<InvoiceMovement> getInvoiceMovements() {
        return invoiceMovements;
    }

    public void setInvoiceMovements(Set<InvoiceMovement> invoiceMovements) {
        this.invoiceMovements = invoiceMovements;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<AktAcceptanceTransferUnits> getAktAcceptanceTransferUnits() {
        return aktAcceptanceTransferUnits;
    }

    public void setAktAcceptanceTransferUnits(Set<AktAcceptanceTransferUnits> aktAcceptanceTransferUnits) {
        this.aktAcceptanceTransferUnits = aktAcceptanceTransferUnits;
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public Set<AktTechConditionUnits> getAtcUnits() {
        return atcUnits;
    }

    public void setAtcUnits(Set<AktTechConditionUnits> atcUnits) {
        this.atcUnits = atcUnits;
    }
}