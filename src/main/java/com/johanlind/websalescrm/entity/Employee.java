package com.johanlind.websalescrm.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// Class not used at the moment, should be used in future for use of multiple useraccounts in case there is need for
// more than one salesrep.

@Entity
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="first_name")
    private String name;
    @Column(name="sales")
    private double sales;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orderList;

    public Employee(String name) {
        this.name = name;
    }

    public void addOrder(Order order) {
        if(orderList == null) {
            orderList = new ArrayList<Order>();
        }

        orderList.add(order);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sales=" + sales +
                ", orderList=" + orderList +
                '}';
    }
}
