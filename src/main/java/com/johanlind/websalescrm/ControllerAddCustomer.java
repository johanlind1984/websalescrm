package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class ControllerAddCustomer {

    private SessionFactory sessionFactory;

    @RequestMapping("/addcustomer")
    public String addProduct(Model theModel) {
        theModel.addAttribute("customeradded", new Customer());
        return "add-customer";
    }

    @RequestMapping("/customerconfirmed")
    public String confirmProduct(@ModelAttribute("customeradded") Customer customer) {
        saveCustomerToDatabase(customer);
        return "confirmation-customer-added";
    }

    private void saveCustomerToDatabase(Customer customer) {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
