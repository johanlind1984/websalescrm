package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.*;
import com.johanlind.websalescrm.Utility.WebSalesUtilities;
import com.johanlind.websalescrm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/order")
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

    @RequestMapping("/orderlist")
    public String orderView(Model theModel, Principal principal) {
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);
        List<Order> orderList = repositoryOrder.findByEmployee(repositoryEmployee.findById(
                repositoryUser.findByUserName(principal.getName()).getId()).orElse(null));
        theModel.addAttribute("orderlist", orderList);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        return "order/order-view";
    }

    @RequestMapping(value="/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") long orderId, Model theModel, Principal principal) {
        Order order = repositoryOrder.findById(orderId).orElse(null);
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);

        if(doesEmployeesCompanyOwnCustomer(employee, order.getCustomer())) {
            theModel.addAttribute("order", order);
            theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
            return "order/order-card";
        }

        return "error/your-order-could-not-be-found";
    }

    @RequestMapping(value="/deleteorder", method = RequestMethod.GET)
    public String deleteOrder(@RequestParam("id") long orderId,  Model theModel, Principal principal) {
        User user = repositoryUser.findByUserName(principal.getName());
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(user));
        Employee employee = repositoryEmployee.findById(user.getId()).orElse(null);
        Order order = repositoryOrder.findById(orderId).orElse(null);

        if(order == null) {
            return "error/your-order-could-not-be-found";
        } else if(doesEmployeesCompanyOwnCustomer(employee, order.getCustomer())) {
            repositoryOrder.deleteById(orderId);
            List<Order> orderList = employee.getOrderList();
            theModel.addAttribute("orderlist", orderList);
            return "order/order-view";
        }

        return "error/your-order-could-not-be-found";
    }

    @RequestMapping(value="/addtoorder", method = RequestMethod.POST)
    public String addToOrder(@RequestParam("productid") long productId, @RequestParam("customerid") long customerId, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));

        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Product product = repositoryProduct.findById(productId).orElse(null);
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);

        if(doesEmployeesCompanyOwnCustomer(employee, customer)) {
            customer.getShoppingCart().getProductList().add(product);
            repositoryCustomer.save(customer);
            List<Product> productList = repositoryProduct.findAll();
            theModel.addAttribute("customer", customer);
            theModel.addAttribute("shoppingcart", customer.getShoppingCart());
            theModel.addAttribute("productlist", productList);
            return "order/add-order";
        }

        return "error/your-order-could-not-be-found";
    }

    @RequestMapping(value="/addorderform")
    public String addOrderForm(@RequestParam("customer") long customerId, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));

        List<Product> productList = repositoryProduct.findAll();
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);

        if(doesEmployeesCompanyOwnCustomer(employee, customer)) {
            theModel.addAttribute("customer", customer);
            theModel.addAttribute("shoppingcart", customer.getShoppingCart());
            theModel.addAttribute("product", new Product());
            theModel.addAttribute("productlist", productList);
            return "order/add-order";
        }

        return "error/your-order-could-not-be-found";
    }

    @RequestMapping(value="/finalizeorder", method = RequestMethod.POST)
    public ModelAndView finalizeOrder(@RequestParam("customerid") long customerId, Model theModel, Principal principal) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);

        if(doesEmployeesCompanyOwnCustomer(employee, customer)) {
            Order order = new Order();
            order.setCustomer(customer);
            order.setEmployee(employee);
            order.setCompany(employee.getCompany());

            for (Product product : customer.getShoppingCart().getProductList()) {
                order.addProduct(product);
            }

            customer.getOrders().add(order);
            customer.getShoppingCart().getProductList().clear();
            repositoryCustomer.save(customer);
            return new ModelAndView("redirect:/order/orderlist");
        }

        return new ModelAndView("redirect:/error/your-order-could-not-be-found");
    }

    @RequestMapping(value="/orderconfirmed")
    public String confirmOrder(@ModelAttribute("orderadded") Order order, @ModelAttribute("customer") Customer customer, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        customer.addOrder(order);
        repositoryCustomer.save(customer);
        return "order/confirmation-order-added";
    }

    private Boolean doesEmployeesCompanyOwnCustomer(Employee employee, Customer customer) {
        if(customer == null) {
            return false;
        } else if(employee.getCompany().getId() == customer.getCompany().getId()) {
            return true;
        }

        return false;
    }
}
