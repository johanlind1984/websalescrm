package com.johanlind.websalescrm.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private long id;

    @Column(name="organisation_number")
    private String orgnaisationNumber;

    @Column(name="company_name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    public Company() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrgnaisationNumber() {
        return orgnaisationNumber;
    }

    public void setOrgnaisationNumber(String orgnaisationNumber) {
        this.orgnaisationNumber = orgnaisationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
