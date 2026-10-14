package net.nco.sklad.dto;

import java.sql.Timestamp;

public class BaseDTO {
    private Long id;
    private String number;
    private String sender;
    private String receiver;
    private String senderRespPerson;
    private String receiverRespPerson;
    private Timestamp dateDocument;
    private Timestamp dateOperation;
    private Timestamp dateExpires;
    private Timestamp dateCommit;
    private String comment;

//    public BaseDTO() {    }

    public BaseDTO(Long id, String number, String sender, String receiver, String senderRespPerson, String receiverRespPerson, Timestamp dateDocument, Timestamp dateOperation, Timestamp dateExpires, Timestamp dateCommit, String comment) {
        this.id = id;
        this.number = number;
        this.sender = sender;
        this.receiver = receiver;
        this.senderRespPerson = senderRespPerson;
        this.receiverRespPerson = receiverRespPerson;
        this.dateDocument = dateDocument;
        this.dateOperation = dateOperation;
        this.dateExpires = dateExpires;
        this.dateCommit = dateCommit;
        this.comment = comment;
    }

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderRespPerson() {
        return senderRespPerson;
    }

    public void setSenderRespPerson(String senderRespPerson) {
        this.senderRespPerson = senderRespPerson;
    }

    public String getReceiverRespPerson() {
        return receiverRespPerson;
    }

    public void setReceiverRespPerson(String receiverRespPerson) {
        this.receiverRespPerson = receiverRespPerson;
    }

    public Timestamp getDateDocument() {
        return dateDocument;
    }

    public void setDateDocument(Timestamp dateDocument) {
        this.dateDocument = dateDocument;
    }

    public Timestamp getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Timestamp dateOperation) {
        this.dateOperation = dateOperation;
    }

    public Timestamp getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Timestamp dateExpires) {
        this.dateExpires = dateExpires;
    }

    public Timestamp getDateCommit() {
        return dateCommit;
    }

    public void setDateCommit(Timestamp dateCommit) {
        this.dateCommit = dateCommit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
