package com.johanlind.websalescrm;
import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

public class test {

    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass((Product.class))
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Customer testCustomer = session.get(Customer.class, 2);
            Order order = new Order();

            Product ball = new Product("neptune", 2.00);
            Product ball2 = new Product("saturn", 3.00);
            Product ball3 = new Product("jupiter", 5.00);

            testCustomer.setName("Orvar");
            order.addProduct(ball);
            order.addProduct(ball2);
            order.addProduct(ball3);
            testCustomer.addOrder(order);


            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }



    }
}
