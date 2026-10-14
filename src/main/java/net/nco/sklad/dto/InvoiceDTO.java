package net.nco.sklad.dto;


import java.sql.Timestamp;

public class InvoiceDTO {

    private Long id;
    private String title;
    private String number;
    private String senderRespPerson;
    private String receiverRespPerson;
    private Timestamp documentDate;
    private Timestamp operationDate;
    private Timestamp expiresDate;
    private Timestamp commitedAt;
    private String sender;
    private String receiver;
    private String comments;
    private int docTypeId;
    private boolean firstPdf;
    private String fileName;
    private String filePath;
    private Long fileId;

    public InvoiceDTO(Long id, String title, String number, String senderRespPerson, String receiverRespPerson, Timestamp documentDate, Timestamp operationDate, Timestamp expiresDate, Timestamp commitedAt, String sender, String receiver, String comments, int docTypeId, boolean firstPdf, String fileName, String filePath, Long fileId) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.senderRespPerson = senderRespPerson;
        this.receiverRespPerson = receiverRespPerson;
        this.documentDate = documentDate;
        this.operationDate = operationDate;
        this.expiresDate = expiresDate;
        this.commitedAt = commitedAt;
        this.sender = sender;
        this.receiver = receiver;
        this.comments = comments;
        this.docTypeId = docTypeId;
        this.firstPdf = firstPdf;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public Timestamp getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Timestamp documentDate) {
        this.documentDate = documentDate;
    }

    public Timestamp getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Timestamp operationDate) {
        this.operationDate = operationDate;
    }

    public Timestamp getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Timestamp expiresDate) {
        this.expiresDate = expiresDate;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getCommitedAt() {
        return commitedAt;
    }

    public void setCommitedAt(Timestamp commitedAt) {
        this.commitedAt = commitedAt;
    }

    public boolean isFirstPdf() {
        return firstPdf;
    }

    public void setFirstPdf(boolean firstPdf) {
        this.firstPdf = firstPdf;
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public int getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(int docTypeId) {
        this.docTypeId = docTypeId;
    }
}
