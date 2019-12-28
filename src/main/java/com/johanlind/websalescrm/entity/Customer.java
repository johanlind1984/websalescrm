package com.johanlind.websalescrm.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private int customerId;

    @Column(name="name")
    private String name;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="next_contact")
    private String nextContactDate;

    @Column(name="comments")
    private String comments;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public void addOrder(Order order) {
        if(orders == null) {
            orders = new ArrayList<Order>();
        }

        order.setCustomer(this);
        orders.add(order);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextContactDate() {
        return nextContactDate;
    }

    public void setNextContactDate(String nextContactDate) {
        this.nextContactDate = nextContactDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", name='" + name + '\'' +
                ", nextContactDate='" + nextContactDate + '\'' +
                ", comments='" + comments + '\'' +
                ", orders=" + orders +
                '}';
    }
}
