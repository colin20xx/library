package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import net.nco.sklad.entity.DepSubdepPair;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document_akt_acceptance_transfer")
public class AktAcceptanceTransfer extends BaseDocument {

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

    @OneToMany(mappedBy = "aktAcceptanceTransfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AktAcceptanceTransferUnits> unitsList;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_type_id")
//    private DocumentsType documentsType;

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

    public List<AktAcceptanceTransferUnits> getUnitsList() {
        return unitsList;
    }

    public void setUnitsList(List<AktAcceptanceTransferUnits> unitsList) {
        this.unitsList = unitsList;
    }

//    public DocumentsType getDocumentsType() {
//        return documentsType;
//    }
//
//    public void setDocumentsType(DocumentsType documentsType) {
//        this.documentsType = documentsType;
//    }
}
