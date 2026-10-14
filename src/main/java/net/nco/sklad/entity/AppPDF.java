package net.nco.sklad.entity;

import jakarta.persistence.*;
import net.nco.sklad.entity.documents.DocumentsType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "pdf_app")
public class AppPDF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentsType documentType;

    private Long documentId;

    private String docNumber;

    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime createdAt;

    private String fileName;
    private String filePath;
    private String fileType;

    @Column(name = "first_pdf", nullable = true)
    private boolean firstPDF;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public DocumentsType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentsType documentType) {
        this.documentType = documentType;
    }

    public boolean isFirstPDF() {
        return firstPDF;
    }

    public void setFirstPDF(boolean firstPDF) {
        this.firstPDF = firstPDF;
    }
}
