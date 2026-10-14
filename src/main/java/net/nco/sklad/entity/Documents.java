package net.nco.sklad.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import net.nco.sklad.entity.documents.DocumentsType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Documents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "ввести номер документу!")
    @Column(name = "doc_number")
    private String number;

    @Column(name = "doc_date_time")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime docDateTime;

    @Column(name = "doc_date_time_operation")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime docDateTimeOperation;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "date_proveden")
    private LocalDateTime dateProveden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_doc_type", nullable = false)
    private DocumentsType document;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_sender")
    private Department sender;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id_receiver")
    private Department receiver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDocDateTime() {
        return docDateTime;
    }

    public void setDocDateTime(LocalDateTime docDateTime) {
        this.docDateTime = docDateTime;
    }

    public LocalDateTime getDocDateTimeOperation() {
        return docDateTimeOperation;
    }

    public void setDocDateTimeOperation(LocalDateTime docDateTimeOperation) {
        this.docDateTimeOperation = docDateTimeOperation;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DocumentsType getDocument() {
        return document;
    }

    public void setDocument(DocumentsType document) {
        this.document = document;
    }

    public Department getSender() {
        return sender;
    }

    public void setSender(Department sender) {
        this.sender = sender;
    }

    public Department getReceiver() {
        return receiver;
    }

    public void setReceiver(Department receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getDateProveden() {
        return dateProveden;
    }

    public void setDateProveden(LocalDateTime dateProveden) {
        this.dateProveden = dateProveden;
    }
}//end class
