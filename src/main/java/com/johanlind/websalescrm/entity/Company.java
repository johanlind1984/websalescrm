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
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "company")
    private List<Customer> customerList;

    @OneToMany(mappedBy = "company")
    private List<Order> orderList;

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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
