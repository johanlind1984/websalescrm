package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@EnableAutoConfiguration
public class ControllerAddProduct {

    private SessionFactory sessionFactory;

    @RequestMapping("/addproduct")
    public String addProduct(Model theModel) {
        theModel.addAttribute("productadded", new Product());
        return "add-product";
    }

    @RequestMapping("/productconfirmed")
    public String confirmProduct(@ModelAttribute("productadded") Product product) {
        saveProductToDatabase(product);
        return "confirmation-product-added";
    }

    private void saveProductToDatabase(Product product) {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
