package net.nco.sklad.entity;

import jakarta.persistence.*;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "departments", schema = "sklad")
public class Department {
    @Id
    @Column(name = "id_department", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "sorting_number")
    private Integer sortingNumber;

    @Column(name = "responsable_persone")
    private String responsablePersone;

    @Column(name="disformed")
    private boolean disformed;

    @Column(name="active_dep", columnDefinition = "boolean default true")
    private boolean activeDep;

    @Column(name="created_at_date")
    private LocalDate createdAtDate;

    @Column(name="created_at_time")
    private LocalTime createdAtTime;

    @Column(name="updated_at_date")
    private LocalDate updatedAtDate;

    @Column(name="updated_at_time")
    private LocalTime updatedAtTime;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "external_dep")
    private boolean externalDep;

    @Column(name = "city")
    private String city;

    @Column(name = "dep_type")
    private String depType;

    @OneToMany()
    private List<SubDepartment> subDepartmentList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getSortingNumber() {
        return sortingNumber;
    }

    public void setSortingNumber(Integer sortingNumber) {
        this.sortingNumber = sortingNumber;
    }

    public String getResponsablePersone() {
        return responsablePersone;
    }

    public void setResponsablePersone(String responsablePersone) {
        this.responsablePersone = responsablePersone;
    }

    public boolean isDisformed() {
        return disformed;
    }

    public void setDisformed(boolean disformed) {
        this.disformed = disformed;
    }

    public List<SubDepartment> getSubDepartmentList() {
        return subDepartmentList;
    }

    public void setSubDepartmentList(List<SubDepartment> subDepartmentList) {
        this.subDepartmentList = subDepartmentList;
    }

    public LocalDate getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(LocalDate createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public LocalTime getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(LocalTime createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    public LocalDate getUpdatedAtDate() {
        return updatedAtDate;
    }

    public void setUpdatedAtDate(LocalDate updatedAtDate) {
        this.updatedAtDate = updatedAtDate;
    }

    public LocalTime getUpdatedAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(LocalTime updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isActiveDep() {
        return activeDep;
    }

    public void setActiveDep(boolean activeDep) {
        this.activeDep = activeDep;
    }

    public boolean isExternalDep() {
        return externalDep;
    }

    public void setExternalDep(boolean externalDep) {
        this.externalDep = externalDep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepType() {
        return depType;
    }

    public void setDepType(String depType) {
        this.depType = depType;
    }
}// end class