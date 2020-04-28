package com.johanlind.websalescrm.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name="order_id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private long orderId;

    @ManyToOne
    @JoinColumn(name="fk_customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="fk_employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="fk_company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(
            name = "order_product",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> productsOrdered;

    public Order() {
    }

    public void addProduct(Product product) {
        if(productsOrdered == null) {
            productsOrdered = new ArrayList<Product>();
        }
        productsOrdered.add(product);
    }

    public long getId() {
        return orderId;
    }

    public void setId(int id) {
        this.orderId = id;
    }

    public List<Product> getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(List<Product> productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {

        if (productsOrdered == null) {
            return "Det finns ingar ordrar att hämta";
        } else {
            return "" + productsOrdered;
        }
    }
}
