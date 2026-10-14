package net.nco.sklad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_department", schema = "sklad")
public class SubDepartment {
    @Id
    @Column(name = "id_sub_department", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_department", nullable = false)
    private Department idDepartment;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "responsible_person")
    private String responsiblePerson;

    @Column(name = "sorting_number", nullable = false, unique = true)
    private Double sortingNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Department idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

}