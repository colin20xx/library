package net.nco.sklad.dto;

import java.math.BigDecimal;

public class AATUnitDTO {

    private Long idApp;
    private Long idAppUnit;
    private Long idDenomination;
    private String denominationName;
    private Long idNomenclature;
    private String nomenclatureName;
    private BigDecimal quantity;
    private int category;
    private Long cardId;
    private BigDecimal price;
    private Long cardNumber;

    public AATUnitDTO() {    }

    public AATUnitDTO(Long idApp, Long idAppUnit, Long idDenomination, String denominationName, Long idNomenclature, String nomenclatureName, BigDecimal quantity, int category, Long cardId, BigDecimal price, Long cardNumber) {
        this.idApp = idApp;
        this.idAppUnit = idAppUnit;
        this.idDenomination = idDenomination;
        this.denominationName = denominationName;
        this.idNomenclature = idNomenclature;
        this.nomenclatureName = nomenclatureName;
        this.quantity = quantity;
        this.category = category;
        this.cardId = cardId;
        this.price = price;
        this.cardNumber = cardNumber;
    }

    public Long getIdApp() {
        return idApp;
    }

    public void setIdApp(Long idApp) {
        this.idApp = idApp;
    }

    public Long getIdAppUnit() {
        return idAppUnit;
    }

    public void setIdAppUnit(Long idAppUnit) {
        this.idAppUnit = idAppUnit;
    }

    public Long getIdDenomination() {
        return idDenomination;
    }

    public void setIdDenomination(Long idDenomination) {
        this.idDenomination = idDenomination;
    }

    public String getDenominationName() {
        return denominationName;
    }

    public void setDenominationName(String denominationName) {
        this.denominationName = denominationName;
    }

    public Long getIdNomenclature() {
        return idNomenclature;
    }

    public void setIdNomenclature(Long idNomenclature) {
        this.idNomenclature = idNomenclature;
    }

    public String getNomenclatureName() {
        return nomenclatureName;
    }

    public void setNomenclatureName(String nomenclatureName) {
        this.nomenclatureName = nomenclatureName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }
}//end class
