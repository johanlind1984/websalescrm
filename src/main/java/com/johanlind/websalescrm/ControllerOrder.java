package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class ControllerOrder {

    private ServiceDataBase serviceDataBase = new ServiceDataBase();
    private Order tempOrder;
    private Customer tempCustomer;

    @RequestMapping(value="/addorder", method = RequestMethod.GET)
    public String addOrder(@RequestParam("customer") Customer customer, Model theModel) {
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", new Order());
        return "add-customer";
    }

    @RequestMapping(value="/orderconfirmed")
    public String confirmOrder(@ModelAttribute("orderadded") Order order, @ModelAttribute("customer") Customer customer) {
        customer.addOrder(order);
        serviceDataBase.saveCustomerToDatabase(customer);
        return "confirmation-order-added";
    }

    @RequestMapping(value="/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") int orderId, Model theModel) {
        Order order = serviceDataBase.getOrder(orderId);
        theModel.addAttribute("order", order);
        return "ordercard";
    }

    @RequestMapping("/orderlist")
    public String orderView(Model theModel) {
        List<Order> orderList = serviceDataBase.getOrderList();
        theModel.addAttribute("orderlist", orderList);

        return "order-view";
    }
    @RequestMapping(value="/addtoorder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("productid") int productId, @RequestParam("customerid") int customerId, Model theModel) {
        Customer customer = serviceDataBase.getCustomer(customerId);
        Product product = serviceDataBase.getProduct(productId);
        tempOrder.addProduct(product);
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", tempOrder);
        theModel.addAttribute("productlist", serviceDataBase.getProductList());
        return "add-order";
    }

    @RequestMapping(value="/addorderform", method = RequestMethod.POST)
    public String addOrderForm(@RequestParam("customer") int customerId, Model theModel) {
        List<Product> productList = serviceDataBase.getProductList();
        Customer customer = serviceDataBase.getCustomer(customerId);
        tempOrder = new Order();
        tempOrder.setProductsOrdered(new ArrayList<Product>());
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", tempOrder);
        theModel.addAttribute("product", new Product());
        theModel.addAttribute("productlist", productList);

        return "add-order";
    }

    @RequestMapping(value="/finalizeorder", method = RequestMethod.POST)
    public String finalizeOrder(@RequestParam("customerid") int CustomerId, Model theModel) {
        tempCustomer = serviceDataBase.getCustomer(CustomerId);
        tempCustomer.addOrder(tempOrder);
        serviceDataBase.saveCustomerToDatabase(tempCustomer);
        theModel.addAttribute("orderlist", serviceDataBase.getOrderList());
        return "order-view";
    }
}
