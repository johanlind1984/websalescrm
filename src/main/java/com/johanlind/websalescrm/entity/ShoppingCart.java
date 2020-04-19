package com.johanlind.websalescrm.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shopping_cart_id")
    private long id;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "customer_shopping_cart",
            joinColumns = { @JoinColumn(name = "csc_cart_id") },
            inverseJoinColumns = { @JoinColumn(name = "csc_product_id") }
    )
    private List<Product> productList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_customer_id")
    private Customer customer;

    public ShoppingCart() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
