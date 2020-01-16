package com.johanlind.websalescrm;

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
    private DataAccessObject DataAccessObject = new DataAccessObject();

    @RequestMapping("/addcustomer")
    public String addCustomer(Model theModel) {
        theModel.addAttribute("customeradded", new Customer());
        return "add-customer";
    }

    @RequestMapping(value="/deletecustomer", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam("id") int customerId, Model theModel) {
        DataAccessObject.deleteCustomer(customerId);
        List<Customer> customerList = DataAccessObject.getCustomerList();
        theModel.addAttribute("customerlist", customerList);
        return "customer-view";
    }

    @RequestMapping(value="/updatecustomerform", method = RequestMethod.GET)
    public String updateCustomerForm(@RequestParam("id") int customerId, Model theModel) {
        Customer customer = DataAccessObject.getCustomer(customerId);
        theModel.addAttribute("customer", customer);
        return "update-customer";
    }


    @RequestMapping(value="/updatecustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer, Model theModel) {
        DataAccessObject.updateCustomer(customer);
        List<Customer> customerList = DataAccessObject.getCustomerList();
        theModel.addAttribute("customerlist", customerList);
        return "customer-view";
    }

    @RequestMapping("/customerconfirmed")
    public String confirmCustomer(@ModelAttribute("customeradded") Customer customer) {
        DataAccessObject.saveCustomerToDatabase(customer);
        return "confirmation-customer-added";
    }

    @RequestMapping(value="/customercard", method = RequestMethod.GET)
    public String customerCard(@RequestParam("id") int customerId, Model theModel) {
        // Get customer with ID as in paramater

        Customer customer = DataAccessObject.getCustomer(customerId);
        theModel.addAttribute("customer", customer);
        return "customercard";
    }

    @RequestMapping("/customerlist")
    public String customerView(Model theModel) {
        List<Customer> customerList = DataAccessObject.getCustomerList();
        theModel.addAttribute("customerlist", customerList);

        return "customer-view";
    }
}
