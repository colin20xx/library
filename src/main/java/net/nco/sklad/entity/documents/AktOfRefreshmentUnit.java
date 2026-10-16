package net.nco.sklad.entity.documents;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "document_akt_of_refreshment_units")
public class AktOfRefreshmentUnit extends BaseDocument{

    private String reason;
    private String responsiblePerson;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
}
