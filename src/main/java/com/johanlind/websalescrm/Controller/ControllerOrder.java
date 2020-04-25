package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.*;
import com.johanlind.websalescrm.Utility.WebSalesUtilities;
import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Employee;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class ControllerOrder {

    @Autowired
    private RepositoryOrder repositoryOrder;

    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryEmployee repositoryEmployee;

    // denna method är jag osäker på vad den gör???
//    @RequestMapping(value="order/addorder", method = RequestMethod.GET)
//    public String addOrder(@RequestParam("customer") Customer customer, Model theModel) {
//        theModel.addAttribute("customer", customer);
//        theModel.addAttribute("order", new Order());
//        return "order/add-order";
//    }

    @RequestMapping(value="order/orderconfirmed")
    public String confirmOrder(@ModelAttribute("orderadded") Order order, @ModelAttribute("customer") Customer customer, Model theModel, Principal principal) {
        customer.addOrder(order);
        repositoryCustomer.save(customer);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/confirmation-order-added";
    }

    @RequestMapping(value="order/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") long orderId, Model theModel, Principal principal) {
        Order order = repositoryOrder.findById(orderId).orElse(null);
        theModel.addAttribute("order", order);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/ordercard";
    }

    @RequestMapping("order/orderlist")
    public String orderView(Model theModel, Principal principal) {
        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/order-view";
    }
    @RequestMapping(value="order/deleteorder", method = RequestMethod.GET)
    public String deleteOrder(@RequestParam("id") long orderId,  Model theModel, Principal principal) {
        repositoryOrder.deleteById(orderId);
        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/order-view";
    }
    @RequestMapping(value="order/addtoorder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("productid") long productId, @RequestParam("customerid") long customerId, Model theModel, Principal principal) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Product product = repositoryProduct.findById(productId).orElse(null);
        customer.getShoppingCart().getProductList().add(product);
        repositoryCustomer.save(customer);

        List<Product> productList = repositoryProduct.findAll();
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("shoppingcart", customer.getShoppingCart());
        theModel.addAttribute("productlist", productList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));

        return "order/add-order";
    }

    @RequestMapping(value="order/addorderform")
    public String addOrderForm(@RequestParam("customer") long customerId, Model theModel, Principal principal) {
        List<Product> productList = repositoryProduct.findAll();
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("shoppingcart", customer.getShoppingCart());
        theModel.addAttribute("product", new Product());
        theModel.addAttribute("productlist", productList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/add-order";
    }


    // Denna borde vara ModelAndView till order/orderlist
    @RequestMapping(value="order/finalizeorder", method = RequestMethod.POST)
    public String finalizeOrder(@RequestParam("customerid") long customerId, Model theModel, Principal principal) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);
        Order order = new Order();
        order.setCustomer(customer);
        order.setEmployee(employee);

        for (Product product : customer.getShoppingCart().getProductList()) {
            order.addProduct(product);
        }

        customer.getOrders().add(order);
        repositoryCustomer.save(customer);

        customer.getShoppingCart().setProductList(new ArrayList<>());
        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/order-view";
    }
}
