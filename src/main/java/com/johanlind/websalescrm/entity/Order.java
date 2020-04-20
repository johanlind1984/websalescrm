package com.johanlind.websalescrm.entity;

import org.springframework.beans.factory.annotation.Autowired;
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

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "order_product",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )

    @Autowired
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

    @Override
    public String toString() {

        if (productsOrdered == null) {
            return "There's no orders";
        } else {
            return "Order Summary: " + productsOrdered;
        }
    }
}
