package net.nco.sklad.entity.documents;

import jakarta.persistence.*;
import net.nco.sklad.entity.DepSubdepPair;

import java.util.List;

@Entity
@Table(name = "document_akt_tech_condition")
public class AktTechCondition extends BaseDocument{

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

    @OneToMany(mappedBy = "atc")
    private List<AktTechConditionUnits> unitsList;


    private String reason;

    public List<AktTechConditionUnits> getUnitsList() {
        return unitsList;
    }

    public void setUnitsList(List<AktTechConditionUnits> unitsList) {
        this.unitsList = unitsList;
    }

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}//end class
