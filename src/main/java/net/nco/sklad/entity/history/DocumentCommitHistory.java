package net.nco.sklad.entity.history;

import jakarta.persistence.*;
import net.nco.sklad.common.CommitAction;
import net.nco.sklad.entity.Documents;
import net.nco.sklad.entity.documents.DocumentsType;
import org.w3c.dom.DocumentType;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents_commit_history")
public class DocumentCommitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime commitDateTime;

    private LocalDateTime recordDateTime;

    private Long documentId;

    @ManyToOne
    private DocumentsType documentType;

    private String comment;

    private CommitAction commitAction;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCommitDateTime() {
        return commitDateTime;
    }

    public void setCommitDateTime(LocalDateTime commitDateTime) {
        this.commitDateTime = commitDateTime;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(LocalDateTime recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public DocumentsType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentsType documentType) {
        this.documentType = documentType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommitAction getCommitAction() {
        return commitAction;
    }

    public void setCommitAction(CommitAction commitAction) {
        this.commitAction = commitAction;
    }
}//end class
