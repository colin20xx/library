package net.nco.sklad.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public class ATCUnitDTO {
    private Long id;
    private String serialNumber;
    @Column
    @Min(1)
    @Max(5)
    private Integer category;
    private BigDecimal qtyBTS;
    private BigDecimal qtyPS;
    private BigDecimal quantity;
    private BigDecimal price;
    private Long atcID;
    private Long denominationID;
    private Long nomenclatureID;
    private Long cardID;
    private String denominationTitle;
    private String nomenclatureTitle;
    private Long cardNumber;

    public ATCUnitDTO() {    }

    public ATCUnitDTO(Long id, String serialNumber, Integer category, BigDecimal qtyBTS, BigDecimal qtyPS, BigDecimal quantity, BigDecimal price, Long atcID, Long denominationID, Long nomenclatureID, Long cardID, String denominationTitle, String nomenclatureTitle, Long cardNumber) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.category = category;
        this.qtyBTS = qtyBTS;
        this.qtyPS = qtyPS;
        this.quantity = quantity;
        this.price = price;
        this.atcID = atcID;
        this.denominationID = denominationID;
        this.nomenclatureID = nomenclatureID;
        this.cardID = cardID;
        this.denominationTitle = denominationTitle;
        this.nomenclatureTitle = nomenclatureTitle;
        this.cardNumber = cardNumber;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getAtcID() {
        return atcID;
    }

    public void setAtcID(Long atcID) {
        this.atcID = atcID;
    }

    public Long getDenominationID() {
        return denominationID;
    }

    public void setDenominationID(Long denominationID) {
        this.denominationID = denominationID;
    }

    public Long getNomenclatureID() {
        return nomenclatureID;
    }

    public void setNomenclatureID(Long nomenclatureID) {
        this.nomenclatureID = nomenclatureID;
    }

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public String getDenominationTitle() {
        return denominationTitle;
    }

    public void setDenominationTitle(String denominationTitle) {
        this.denominationTitle = denominationTitle;
    }

    public String getNomenclatureTitle() {
        return nomenclatureTitle;
    }

    public void setNomenclatureTitle(String nomenclatureTitle) {
        this.nomenclatureTitle = nomenclatureTitle;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

//    @Override
//    public String toString() {
//        return "ATCUnitDTO{" +
//                "id=" + id +
//                ", serialNumber='" + serialNumber + '\'' +
//                ", category=" + category +
//                ", qtyBTS=" + qtyBTS +
//                ", qtyPS=" + qtyPS +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                ", atcID=" + atcID +
//                ", denominationID=" + denominationID +
//                ", nomenclatureID=" + nomenclatureID +
//                ", cardID=" + cardID +
//                ", denominationTitle='" + denominationTitle + '\'' +
//                ", nomenclatureTitle='" + nomenclatureTitle + '\'' +
//                ", cardNumber=" + cardNumber +
//                '}';
//    }
}
