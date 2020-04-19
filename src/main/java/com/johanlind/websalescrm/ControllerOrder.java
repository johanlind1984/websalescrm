package com.johanlind.websalescrm;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.Repository.RepositoryOrder;
import com.johanlind.websalescrm.Repository.RepositoryProduct;
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

@Controller
@EnableAutoConfiguration
public class ControllerOrder {

    @Autowired
    private RepositoryOrder repositoryOrder;

    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @Autowired
    private RepositoryProduct repositoryProduct;

    @RequestMapping(value="/addorder", method = RequestMethod.GET)
    public String addOrder(@RequestParam("customer") Customer customer, Model theModel) {
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", new Order());
        return "add-customer";
    }

    @RequestMapping(value="/orderconfirmed")
    public String confirmOrder(@ModelAttribute("orderadded") Order order, @ModelAttribute("customer") Customer customer) {
        customer.addOrder(order);
        repositoryCustomer.save(customer);
        return "confirmation-order-added";
    }

    @RequestMapping(value="/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") long orderId, Model theModel) {
        Order order = repositoryOrder.findById(orderId).orElse(null);
        theModel.addAttribute("order", order);
        return "ordercard";
    }

    @RequestMapping("/orderlist")
    public String orderView(Model theModel) {
        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        return "order-view";
    }
    @RequestMapping(value="/deleteorder", method = RequestMethod.GET)
    public String deleteOrder(@RequestParam("id") long orderId,  Model theModel) {
        repositoryOrder.deleteById(orderId);
        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        return "order-view";
    }
    @RequestMapping(value="/addtoorder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("productid") long productId, @RequestParam("customerid") long customerId, Model theModel) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Product product = repositoryProduct.findById(productId).orElse(null);
        customer.getShoppingCart().getProductList().add(product);
        repositoryCustomer.save(customer);

        List<Product> productList = repositoryProduct.findAll();
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("shoppingcart", customer.getShoppingCart());
        theModel.addAttribute("productlist", productList);
        return "add-order";
    }

    @RequestMapping(value="/addorderform", method = RequestMethod.POST)
    public String addOrderForm(@RequestParam("customer") long customerId, Model theModel) {
        List<Product> productList = repositoryProduct.findAll();
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("shoppingcart", customer.getShoppingCart());
        theModel.addAttribute("product", new Product());
        theModel.addAttribute("productlist", productList);

        return "add-order";
    }

    @RequestMapping(value="/finalizeorder", method = RequestMethod.POST)
    public String finalizeOrder(@RequestParam("customerid") long customerId, Model theModel) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Order order = new Order();

        for (Product product : customer.getShoppingCart().getProductList()) {
            order.addProduct(product);
        }

        order.setCustomer(customer);
        customer.getOrders().add(order);
        customer.getShoppingCart().setProductList(new ArrayList<>());
        repositoryCustomer.save(customer);

        List<Order> orderList = repositoryOrder.findAll();
        theModel.addAttribute("orderlist", orderList);
        return "order-view";
    }
}
