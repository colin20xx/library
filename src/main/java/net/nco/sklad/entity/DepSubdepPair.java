package net.nco.sklad.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class DepSubdepPair {
    private int idDep;
    private int idSubdep;

    public DepSubdepPair() { }

    public DepSubdepPair(int idDep, int idSubdep) {
        this.idDep = idDep;
        this.idSubdep = idSubdep;
    }

    public int getIdDep() {
        return idDep;
    }

    public void setIdDep(int idDep) {
        this.idDep = idDep;
    }

    public int getIdSubdep() {
        return idSubdep;
    }

    public void setIdSubdep(int idSubdep) {
        this.idSubdep = idSubdep;
    }

}
