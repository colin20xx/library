package net.nco.sklad.dto;

public class DepsWithSubDepsDTO {

    private Integer idDep;
    private String depsShortName;
    private Integer idSubDep;
    private String subDepsShortName;
    private String responsiblePersone;

    public DepsWithSubDepsDTO(Integer idDep, String depsShortName, Integer idSubDep, String subDepsShortName, String responsiblePersone) {
        this.idDep = idDep;
        this.depsShortName = depsShortName;
        this.idSubDep = idSubDep;
        this.subDepsShortName = subDepsShortName;
        this.responsiblePersone = responsiblePersone;
    }

    public Integer getIdDep() {
        return idDep;
    }

    public void setIdDep(Integer idDep) {
        this.idDep = idDep;
    }

    public String getDepsShortName() {
        return depsShortName;
    }

    public void setDepsShortName(String depsShortName) {
        this.depsShortName = depsShortName;
    }

    public Integer getIdSubDep() {
        return idSubDep;
    }

    public void setIdSubDep(Integer idSubDep) {
        this.idSubDep = idSubDep;
    }

    public String getSubDepsShortName() {
        return subDepsShortName;
    }

    public void setSubDepsShortName(String subDepsShortName) {
        this.subDepsShortName = subDepsShortName;
    }

    public String getResponsiblePersone() {
        return responsiblePersone;
    }

    public void setResponsiblePersone(String responsiblePersone) {
        this.responsiblePersone = responsiblePersone;
    }
}
