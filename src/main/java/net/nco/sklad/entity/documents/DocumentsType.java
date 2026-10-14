package net.nco.sklad.entity.documents;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "documents_type", schema = "sklad")
public class DocumentsType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "title_short", nullable = true)
    private String titleShort;

    @Column(name = "eng_title", nullable = true)
    private String englishTitle;

    @Column(name = "card_in", nullable = true)
    private Boolean cardIn = false;

    @Column(name = "card_out", nullable = true)
    private Boolean cardOut = false;

    @Column(name = "expire_days", nullable = true)
    private Integer expireDays;

    @OneToMany(mappedBy = "documentsType")
    List<AktAcceptanceTransfer> aatList;

    @OneToMany(mappedBy = "documentsType")
    private List<Invoice> invoiceList;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExpireDays() { return expireDays; }

    public void setExpireDays(Integer expireDays) { this.expireDays = expireDays; }

    public Boolean getCardIn() {
        return cardIn;
    }

    public void setCardIn(Boolean cardIn) {
        this.cardIn = cardIn;
    }

    public Boolean getCardOut() {
        return cardOut;
    }

    public void setCardOut(Boolean cardOut) {
        this.cardOut = cardOut;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    public List<AktAcceptanceTransfer> getAatList() {
        return aatList;
    }

    public void setAatList(List<AktAcceptanceTransfer> aatList) {
        this.aatList = aatList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }
}