package net.nco.sklad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit extends BaseUnit{

    @OneToOne
    @JsonIgnore
    private Unit parent;

    public Unit getParent() {
        return parent;
    }

    public void setParent(Unit parent) {
        this.parent = parent;
    }
}
