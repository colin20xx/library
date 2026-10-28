package net.nco.sklad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "units")
public class Unit extends BaseUnit{

    @Column(precision = 16, scale = 5)
    private BigDecimal price;

    @OneToOne
    @JsonIgnore
    private Unit parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "denomination_id", nullable = false)
    private Denomination denomination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "nomenclature_id", nullable = true)
    private Nomenclature nomenclature;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="idDep", column = @Column(name = "current_department_id")),
            @AttributeOverride(name="idSubdep", column = @Column(name = "current_subdep_id"))
    })
    private DepSubdepPair currentDepartment;


    public Unit getParent() {
        return parent;
    }

    public void setParent(Unit parent) {
        this.parent = parent;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DepSubdepPair getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(DepSubdepPair currentDepartment) {
        this.currentDepartment = currentDepartment;
    }
}
