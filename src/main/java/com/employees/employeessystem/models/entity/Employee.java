package com.employees.employeessystem.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author sebastian
 * Entity con todos sus campos para los empleados
 */

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_lastname", length = 20)
    private String firstLastName;

    @Column(name = "second_lastname", length = 20)
    private String secondLastName;

    @Column(name = "first_name", length = 20)
    private String fisrtName;

    @Column(name = "other_names", length = 50)
    private String otherNames;

    @Column(name = "country_job")
    private String countryJob;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_number", length = 20)
    private String documentNumber;

    @Column(name = "email",unique = true, length = 300)
    private String email;

    @Column(name = "ingress_date")
    private LocalDate ingressDate;

    @Column(name = "area")
    private String area;

    @Column(name = "state")
    private String state;

    @Column(name = "create_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createAt;

    @PrePersist
    public void prePersistCreateDateAndState() {
        Date date = new Date();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.createAt = LocalDateTime.parse(DateFor.format(date), format1);

        this.state = "Activo";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getCountryJob() {
        return countryJob;
    }

    public void setCountryJob(String countryJob) {
        this.countryJob = countryJob;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getIngressDate() {
        return ingressDate;
    }

    public void setIngressDate(LocalDate ingressDate) {
        this.ingressDate = ingressDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
