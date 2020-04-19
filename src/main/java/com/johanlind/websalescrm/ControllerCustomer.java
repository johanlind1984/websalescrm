package com.johanlind.websalescrm;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class ControllerCustomer {
    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @RequestMapping("/addcustomer")
    public String addCustomer(Model theModel) {
        theModel.addAttribute("customeradded", new Customer());
        return "add-customer";
    }

    @RequestMapping(value="/deletecustomer", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam("id") long customerId, Model theModel) {
        repositoryCustomer.deleteById(customerId);
        List<Customer> customerList = repositoryCustomer.findAll();
        theModel.addAttribute("customerlist", customerList);
        return "customer-view";
    }

    @RequestMapping(value="/updatecustomerform", method = RequestMethod.GET)
    public String updateCustomerForm(@RequestParam("id") long customerId, Model theModel) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        theModel.addAttribute("customer", customer);
        return "update-customer";
    }


    @RequestMapping(value="/updatecustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, Model theModel) {
        repositoryCustomer.save(customer);
        List<Customer> customerList = repositoryCustomer.findAll();
        theModel.addAttribute("customerlist", customerList);
        return "customer-view";
    }

    @RequestMapping("/customerconfirmed")
    public String confirmCustomer(@ModelAttribute("customeradded") Customer customer) {
        repositoryCustomer.save(customer);
        return "confirmation-customer-added";
    }

    @RequestMapping(value="/customercard", method = RequestMethod.GET)
    public String customerCard(@RequestParam("id") long customerId, Model theModel) {
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        theModel.addAttribute("customer", customer);
        return "customercard";
    }

    @RequestMapping("/customerlist")
    public String customerView(Model theModel) {
        List<Customer> customerList = repositoryCustomer.findAll();
        theModel.addAttribute("customerlist", customerList);

        return "customer-view";
    }
}
