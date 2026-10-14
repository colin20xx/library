package net.nco.sklad.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class AktAcceptTransferDTO {
    private Long id;
    private String number;
    @JsonFormat(pattern = "dd.MM.yy")
    private Timestamp date;
    private String senderIDs;
    private String receiverIDs;
    private Long pdfId;
    private String pdfName;
    private boolean isFirstPdf;
    private String comments;
    private String sender;
    private String receiver;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private Timestamp commitedDate;


    public AktAcceptTransferDTO(Long id, String number, Timestamp date, String senderIDs, String receiverIDs, Long pdfId, String pdfName, boolean isFirstPdf, String comments, String sender, String receiver, Timestamp commitedDate) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.senderIDs = senderIDs;
        this.receiverIDs = receiverIDs;
        this.pdfId = pdfId;
        this.pdfName = pdfName;
        this.isFirstPdf = isFirstPdf;
        this.comments = comments;
        this.sender = sender;
        this.receiver = receiver;
        this.commitedDate = commitedDate;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSenderIDs() {
        return senderIDs;
    }

    public void setSenderIDs(String senderIDs) {
        this.senderIDs = senderIDs;
    }

    public String getReceiverIDs() {
        return receiverIDs;
    }

    public void setReceiverIDs(String receiverIDs) {
        this.receiverIDs = receiverIDs;
    }

    public Long getPdfId() {
        return pdfId;
    }

    public void setPdfId(Long pdfId) {
        this.pdfId = pdfId;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public boolean isFirstPdf() {
        return isFirstPdf;
    }

    public void setFirstPdf(boolean firstPdf) {
        isFirstPdf = firstPdf;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Timestamp getCommitedDate() {
        return commitedDate;
    }

    public void setCommitedDate(Timestamp commitedDate) {
        this.commitedDate = commitedDate;
    }
}
