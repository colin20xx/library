package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import net.nco.sklad.services.CommittableDocument;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseDocument implements CommittableDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(name = "document_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime documentDate;

    @Column(name = "operation_date", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime operationDate;

    @Column(name = "created_at",nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime expiresAt;

    @Column(name = "updated_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime deletedAt;

    @Column(name = "commited_at")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime committedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id")
    private DocumentsType documentsType;

    @Column(name = "comments")
    private String comments;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(LocalDateTime documentDate) {
        this.documentDate = documentDate;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDateTime operationDate) {
        this.operationDate = operationDate;
    }

    @Override
    public LocalDateTime getCommittedAt() {
        return committedAt;
    }

    @Override
    public void setCommittedAt(LocalDateTime committedAt) {
        this.committedAt = committedAt;
    }

    public DocumentsType getDocumentsType() {
        return documentsType;
    }

    public void setDocumentsType(DocumentsType documentsType) {
        this.documentsType = documentsType;
    }

    //    public int getDocType() { return docType; }
//    public void setDocType(int docType) { this.docType = docType; }
}
