package net.nco.sklad.dto;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CardMovementDTO {
    private Long idDoc;
    private Timestamp dateOfRecord;
    private String title;
    private String docNumber;
    private Timestamp docDate;
    private String sender;
    private String receiver;
    private Integer category;
    private BigDecimal qtyPS;
    private BigDecimal qtyBTS;
    private BigDecimal quantity;
    private BigDecimal currentQuantity;
    private Timestamp commitedAt;

    public CardMovementDTO() {    }

    public CardMovementDTO(Long idDoc, Timestamp dateOfRecord, String title, String docNumber, Timestamp docDate, String sender, String receiver, Integer category, BigDecimal qtyPS, BigDecimal qtyBTS, BigDecimal quantity, Timestamp commitedAt) {
        this.idDoc = idDoc;
        this.dateOfRecord = dateOfRecord;
        this.title = title;
        this.docNumber = docNumber;
        this.docDate = docDate;
        this.sender = sender;
        this.receiver = receiver;
        this.category = category;
        this.qtyPS = qtyPS;
        this.qtyBTS = qtyBTS;
        this.quantity = quantity;
        this.commitedAt = commitedAt;
    }

    public Timestamp getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Timestamp dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Timestamp getDocDate() {
        return docDate;
    }

    public void setDocDate(Timestamp docDate) {
        this.docDate = docDate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getQtyPS() {
        return qtyPS;
    }

    public void setQtyPS(BigDecimal qtyPS) {
        this.qtyPS = qtyPS;
    }

    public BigDecimal getQtyBTS() {
        return qtyBTS;
    }

    public void setQtyBTS(BigDecimal qtyBTS) {
        this.qtyBTS = qtyBTS;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Long getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(Long idDoc) {
        this.idDoc = idDoc;
    }

    public Timestamp getCommitedAt() {
        return commitedAt;
    }

    public void setCommitedAt(Timestamp commitedAt) {
        this.commitedAt = commitedAt;
    }

    @Override
    public String toString() {
        return "CardMovementDTO{" +
                "dateOfRecord='" + dateOfRecord + '\'' +
                ", title='" + title + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", docDate=" + docDate +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
