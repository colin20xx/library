package net.nco.sklad.dto;

import java.sql.Timestamp;

public class AktTechConditionDTO extends BaseDTO {

    public AktTechConditionDTO(Long id, String number, String sender, String receiver, String senderRespPerson, String receiverRespPerson, Timestamp dateDocument, Timestamp dateOperation, Timestamp dateExpires, Timestamp dateCommit, String comment) {
        super(id, number, sender, receiver, senderRespPerson, receiverRespPerson, dateDocument, dateOperation, dateExpires, dateCommit, comment);
    }
}
