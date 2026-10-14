package net.nco.sklad.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CardDTO {
    private Long id;
    private Long cardNumber;
    private Double qtyPS;
    private Double qtyBTS;
    private BigDecimal price;
    private Long nomenclatureID;
    private String nomenclatureName;
    private int category;
    private Timestamp cardDate;


    public CardDTO(Long id, Long cardNumber, Double qtyPS, Double qtyBTS, BigDecimal price, Long nomenclatureID, String nomenclatureName, int category) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.qtyPS = qtyPS;
        this.qtyBTS = qtyBTS;
        this.price = price;
        this.nomenclatureID = nomenclatureID;
        this.nomenclatureName = nomenclatureName;
        this.category = category;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNomenclatureID() {
        return nomenclatureID;
    }

    public void setNomenclatureID(Long nomenclatureID) {
        this.nomenclatureID = nomenclatureID;
    }

    public String getNomenclatureName() {
        return nomenclatureName;
    }

    public void setNomenclatureName(String nomenclatureName) {
        this.nomenclatureName = nomenclatureName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
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

    public Timestamp getCardDate() {
        return cardDate;
    }

    public void setCardDate(Timestamp cardDate) {
        this.cardDate = cardDate;
    }
}
