package com.johanlind.websalescrm.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="company")
@PrimaryKeyJoinColumn(name = "company_user_id")
public class Company extends User {

    @Column(name="organisation_number")
    private String orgnaisationNumber;

    @Column(name="company_name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<Employee> employees;

    public Company() {
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
