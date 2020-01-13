package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataAccessObject {

    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    private List<Customer> customerList;
    @Autowired
    private List<Product> productList;
    @Autowired
    private List<Order>  orderList;

    public DataAccessObject() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        customerList = new ArrayList<>();
        productList = new ArrayList<>();
        orderList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            customerList = (List<Customer>) session.createCriteria(Customer.class).list();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return customerList;
    }

    public List<Customer> getCustomerListMainView() {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            String queryDateAsc =  "FROM Customer WHERE next_contact <= CURDATE() order by next_contact ASC";
            customerList = (List<Customer>) session.createQuery(queryDateAsc).list();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return customerList;
    }

    public Customer getCustomer(int customerId) {
        session = sessionFactory.getCurrentSession();
        Customer tempCustomer = new Customer();

        try {
            session.beginTransaction();
            tempCustomer = session.get(Customer.class, customerId);
            tempCustomer.getOrders().size();


        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return tempCustomer;
    }

    public List<Order> getOrders(Customer customer) {
        session = sessionFactory.getCurrentSession();
        List<Order> orders = new ArrayList<>();

        try {
            session.beginTransaction();
            customer = session.get(Customer.class, customer.getCustomerId());
            orders = customer.getOrders();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return orders;
    }



    public List<Product> getProductList() {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            productList = (List<Product>) session.createCriteria(Product.class).list();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return productList;
    }

    public Product getProduct(int productId) {
        session = sessionFactory.getCurrentSession();
        Product tempProduct = new Product();

        try {
            session.beginTransaction();
            tempProduct = session.get(Product.class, productId);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return tempProduct;

    }

    public List<Order> getOrderList() {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            orderList = (List<Order>) session.createCriteria(Order.class).list();

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return orderList;
    }

    public Order getOrder(int orderId) {
        session = sessionFactory.getCurrentSession();
        Order tempOrder = new Order();

        try {
            session.beginTransaction();
            tempOrder = session.get(Order.class, orderId);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return tempOrder;

    }

    public void saveCustomerToDatabase(Customer customer) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(customer);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void saveProductToDatabase(Product product) {
        session = sessionFactory.getCurrentSession();

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


    public void deleteCustomer(int customerId) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Customer customerToDelete = session.get(Customer.class, customerId);
            session.delete(customerToDelete);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateCustomer(Customer customerToUpdate) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.update(customerToUpdate);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteProduct(int productId) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Product productToDelete = session.get(Product.class, productId);
            session.delete(productToDelete);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateProduct(Product productToUpdate) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.update(productToUpdate);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteOrder(int orderId) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Order orderToDelete = session.get(Order.class, orderId);
            session.delete(orderToDelete);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateOrder(Order orderToUpdate) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.update(orderToUpdate);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
