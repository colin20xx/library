package net.nco.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serialNumber;

    @Min(1)
    @Max(5)
    private int category;

    @Column(nullable = true)
    private int unitYear;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime deletedAt;

    private String comment;

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

    public int getUnitYear() {
        return unitYear;
    }

    public void setUnitYear(int year) {
        this.unitYear = year;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
