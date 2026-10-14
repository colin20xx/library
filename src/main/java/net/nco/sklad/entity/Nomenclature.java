package net.nco.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import net.nco.sklad.entity.documents.AktTechConditionUnits;
import net.nco.sklad.entity.documents.InvoiceMovement;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nomenclatures", schema = "sklad")
public class Nomenclature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nomenclature_name")
    private String nomenclatureName;

    private String  comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "denomination_id")
    private Denomination denomination;

    @OneToMany(mappedBy = "nomenclature", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceMovement> imList;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime deletedAt;

    @Column(name = "commited_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime commited_at;

    @OneToMany(mappedBy = "nomenclature")
    private List<AktTechConditionUnits> atcUnitsList;

    public Nomenclature() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomenclatureName() {
        return nomenclatureName;
    }

    public void setNomenclatureName(String nomenclatureName) {
        this.nomenclatureName = nomenclatureName;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenomination(Denomination denomination) {
        this.denomination = denomination;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<InvoiceMovement> getImList() {
        return imList;
    }

    public void setImList(List<InvoiceMovement> imList) {
        this.imList = imList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCommited_at() {
        return commited_at;
    }

    public void setCommited_at(LocalDateTime commited_at) {
        this.commited_at = commited_at;
    }

    public List<AktTechConditionUnits> getAtcUnitsList() {
        return atcUnitsList;
    }

    public void setAtcUnitsList(List<AktTechConditionUnits> atcUnitsList) {
        this.atcUnitsList = atcUnitsList;
    }
}