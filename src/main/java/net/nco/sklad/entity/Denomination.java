package net.nco.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import net.nco.sklad.dto.DenominationRecordDTO;
import net.nco.sklad.entity.documents.AktAcceptanceTransferUnits;
import net.nco.sklad.entity.documents.InvoiceMovement;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Parent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "denominations", schema = "sklad")
public class Denomination {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "denomination_name", nullable = false, unique = false)
    @Pattern(regexp = "", message = "{duplicate}")
    private String denominationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_military_id")
    private ObjectsMilitary parentObjMil;

    @OneToMany(mappedBy = "denomination")
    private List<Nomenclature> nomenclatures;

    @OneToMany(mappedBy = "denomination")
    private List<InvoiceMovement> imList;

    @OneToMany(mappedBy = "denomination")
    private List<AktAcceptanceTransferUnits> unitsList;

    @OneToMany(mappedBy = "denomination")
    private List<AktAcceptanceTransferUnits> atcUnitsList;

    private String objectName;

    private String comments;

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

    public ObjectsMilitary getParentObjMil() {
        return parentObjMil;
    }

    public void setParentObjMil(ObjectsMilitary parentObjMil) {
        this.parentObjMil = parentObjMil;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominationName() {
        return denominationName;
    }

    public void setDenominationName(String denominationName) {
        this.denominationName = denominationName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<Nomenclature> getNomenclatures() { return nomenclatures; }

    public void setNomenclatures(List<Nomenclature> nomenclatures) { this.nomenclatures = nomenclatures; }

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

    public List<AktAcceptanceTransferUnits> getUnitsList() {
        return unitsList;
    }

    public void setUnitsList(List<AktAcceptanceTransferUnits> unitsList) {
        this.unitsList = unitsList;
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

    //    @Override
//    public String toString() {
//        return "Denomination [id=" + id + ", denominationName=" + denominationName + ", parentObjMil=" + parentObjMil;
//    }
}