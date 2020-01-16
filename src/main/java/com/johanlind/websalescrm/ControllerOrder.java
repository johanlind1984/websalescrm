package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@EnableAutoConfiguration
public class ControllerOrder {

    @Autowired
    private DataAccessObject DataAccessObject = new DataAccessObject();
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
        DataAccessObject.saveCustomerToDatabase(customer);
        return "confirmation-order-added";
    }

    @RequestMapping(value="/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") int orderId, Model theModel) {
        Order order = DataAccessObject.getOrder(orderId);
        theModel.addAttribute("order", order);
        return "ordercard";
    }

    @RequestMapping("/orderlist")
    public String orderView(Model theModel) {
        List<Order> orderList = DataAccessObject.getOrderList();
        orderList = orderList.stream().distinct().collect(Collectors.toList());
        theModel.addAttribute("orderlist", orderList);
        return "order-view";
    }
    @RequestMapping(value="/deleteorder", method = RequestMethod.GET)
    public String deleteOrder(@RequestParam("id") int orderId,  Model theModel) {
        DataAccessObject.deleteOrder(orderId);
        theModel.addAttribute("orderlist", DataAccessObject.getOrderList());
        return "order-view";
    }
    @RequestMapping(value="/addtoorder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("productid") int productId, @RequestParam("customerid") int customerId, Model theModel) {
        Customer customer = DataAccessObject.getCustomer(customerId);
        Product product = DataAccessObject.getProduct(productId);
        tempOrder.addProduct(product);
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", tempOrder);
        theModel.addAttribute("productlist", DataAccessObject.getProductList());
        return "add-order";
    }

    @RequestMapping(value="/addorderform", method = RequestMethod.POST)
    public String addOrderForm(@RequestParam("customer") int customerId, Model theModel) {
        List<Product> productList = DataAccessObject.getProductList();
        Customer customer = DataAccessObject.getCustomer(customerId);
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
        tempCustomer = DataAccessObject.getCustomer(CustomerId);
        tempCustomer.addOrder(tempOrder);
        DataAccessObject.saveCustomerToDatabase(tempCustomer);
        ArrayList<Order> orderList = (ArrayList<Order>) DataAccessObject.getOrderList().stream().distinct().collect(Collectors.toList());
        theModel.addAttribute("orderlist", orderList);
        return "order-view";
    }
}
