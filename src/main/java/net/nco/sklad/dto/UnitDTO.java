package net.nco.sklad.dto;

import java.math.BigDecimal;

public class UnitDTO {

    private Long id;
    private String serialNumber;
    private int category;
    private int year;
    private Long denominationID;
    private Long nomenclatureID;
    private String denomination;
    private String nomenclature;
    private String comment;
    private BigDecimal price;
    private String currentDepartment;
    private Integer departmentID;
    private Integer subDepartmentID;

    public UnitDTO(Long id, String serialNumber, int category, int year, Long denominationID, Long nomenclatureID, String denomination, String nomenclature, String comment, BigDecimal price, String currentDepartment, Integer departmentID, Integer subDepartmentID) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.category = category;
        this.year = year;
        this.denominationID = denominationID;
        this.nomenclatureID = nomenclatureID;
        this.denomination = denomination;
        this.nomenclature = nomenclature;
        this.comment = comment;
        this.price = price;
        this.currentDepartment = currentDepartment;
        this.departmentID = departmentID;
        this.subDepartmentID = subDepartmentID;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(String currentDepartment) {
        this.currentDepartment = currentDepartment;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public Integer getSubDepartmentID() {
        return subDepartmentID;
    }

    public void setSubDepartmentID(Integer subDepartmentID) {
        this.subDepartmentID = subDepartmentID;
    }
}
