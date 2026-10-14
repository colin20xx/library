package net.nco.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "objects_military", schema = "sklad")
public class ObjectsMilitary {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "object_name", nullable = false, unique = true)
//    @Pattern(regexp = "", message = "This value already exists!")
    @NotBlank(message = "Поле не може бути пустим!")
    private String objectName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parentObjMil", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Denomination> denominations = new ArrayList<Denomination>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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

    public void addChild(Denomination child) {
        denominations.add(child);
        child.setParentObjMil(this);
    }

    public void removeChild(Denomination child) {
        denominations.remove(child);
        child.setParentObjMil(null);
    }

    public ObjectsMilitary() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @Override
//    public String toString() {
//        return "ObjectsMilitary{" +
//                "id=" + id +
//                ", objectName='" + objectName + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }
}