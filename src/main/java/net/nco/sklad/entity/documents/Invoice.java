package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import net.nco.sklad.entity.DepSubdepPair;
import net.nco.sklad.services.CommittableDocument;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseDocument {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="idDep", column = @Column(name = "sender_dep_id")),
            @AttributeOverride(name="idSubdep", column = @Column(name = "sender_subdep_id"))
    })
    private DepSubdepPair sender;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="idDep", column = @Column(name = "receiver_dep_id")),
            @AttributeOverride(name="idSubdep", column = @Column(name = "receiver_subdep_id"))
    })
    private DepSubdepPair receiver;

    @Column(name = "responsible_person_sender", nullable = true)
    private String responsiblePersonSender;

    @Column(name = "responsible_person_receiver", nullable = true)
    private String responsiblePersonReceiver;

//    @ManyToOne
//    @JoinColumn(name = "doc_type_id")
//    private DocumentsType documentsType;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceMovement> invoiceMovements;

    public DepSubdepPair getSender() {
        return sender;
    }

    public void setSender(DepSubdepPair sender) {
        this.sender = sender;
    }

    public DepSubdepPair getReceiver() {
        return receiver;
    }

    public void setReceiver(DepSubdepPair receiver) {
        this.receiver = receiver;
    }

    public String getResponsiblePersonSender() {
        return responsiblePersonSender;
    }

    public void setResponsiblePersonSender(String responsiblePersonSender) {
        this.responsiblePersonSender = responsiblePersonSender;
    }

    public String getResponsiblePersonReceiver() {
        return responsiblePersonReceiver;
    }

    public void setResponsiblePersonReceiver(String responsiblePersonReceiver) {
        this.responsiblePersonReceiver = responsiblePersonReceiver;
    }

    public List<InvoiceMovement> getInvoiceMovements() {
        return invoiceMovements;
    }

    public void setInvoiceMovements(List<InvoiceMovement> invoiceMovements) {
        this.invoiceMovements = invoiceMovements;
    }

//    public DocumentsType getDocumentsType() {
//        return documentsType;
//    }
//    public void setDocumentsType(DocumentsType documentsType) {
//        this.documentsType = documentsType;
//    }

    public String toString() {
        return "number: " + getNumber() + ", date: " + getDocumentDate();
    }
}
