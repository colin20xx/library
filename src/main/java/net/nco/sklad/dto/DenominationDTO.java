package net.nco.sklad.dto;

public class DenominationDTO {
    private Long id;
    private String title;
    private int omID;
    private String omTitle;

    public DenominationDTO() {    }

    public DenominationDTO(Long id, String title, int omID, String omTitle) {
        this.id = id;
        this.title = title;
        this.omID = omID;
        this.omTitle = omTitle;
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

    public int getOmID() {
        return omID;
    }

    public void setOmID(int omID) {
        this.omID = omID;
    }

    public String getOmTitle() {
        return omTitle;
    }

    public void setOmTitle(String omTitle) {
        this.omTitle = omTitle;
    }

    @Override
    public String toString() {
        return "DenominationDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", omID=" + omID +
                ", omTitle='" + omTitle + '\'' +
                '}';
    }
}
