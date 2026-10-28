package net.nco.sklad.dto;

public class NomenclatureDTO {

    private Long id;
    private String title;
    private Long idDenom;

    public NomenclatureDTO(Long id, String title, Long idDenom) {
        this.id = id;
        this.title = title;
        this.idDenom = idDenom;
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

    public Long getIdDenom() {
        return idDenom;
    }

    public void setIdDenom(Long idDenom) {
        this.idDenom = idDenom;
    }

    @Override
    public String toString() {
        return "NomenclatureDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", idDenom=" + idDenom +
                '}';
    }
}
