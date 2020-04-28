package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.Repository.RepositoryEmployee;
import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.Utility.WebSalesUtilities;
import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Employee;
import com.johanlind.websalescrm.entity.ShoppingCart;
import com.johanlind.websalescrm.entity.User;
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
public class ControllerCustomer {

    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryEmployee repositoryEmployee;

    @RequestMapping("/register-customer")
    public String addCustomer(Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        theModel.addAttribute("customeradded", new Customer());
        return "register-customer/add-customer";
    }

    @RequestMapping("/register-customer/save-customer")
    public String saveCustomerToDatabaseAsNewCustomer(@ModelAttribute("customeradded") Customer customer, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);
        setUpCustomerBeforeSaving(customer, employee);
        repositoryCustomer.save(customer);
        return "register-customer/confirmation-customer-added";
    }

    @RequestMapping(value="/manager/deletecustomer", method = RequestMethod.GET)
    public ModelAndView deleteCustomer(@RequestParam("id") long customerId, Model theModel, Principal principal) {
        repositoryCustomer.deleteById(customerId);
        return new ModelAndView("redirect:/employee/mainview");
    }

    @RequestMapping(value="/customer/updatecustomerform", method = RequestMethod.GET)
    public String updateCustomerForm(@RequestParam("id") long customerId, Model theModel, Principal principal) {
        User user = repositoryUser.findByUserName(principal.getName());
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(user));
        Employee employee = repositoryEmployee.findById(user.getId()).orElse(null);
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);

        if(doesEmployeesCompanyOwnCustomer(employee, customer)) {
            theModel.addAttribute("customer", customer);
            return "customer/update-customer";
        }

        return "error/your-customer-could-not-be-found";
    }


    @RequestMapping(value="/customer/updatecustomer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        repositoryCustomer.save(customer);
        return new ModelAndView("redirect:/employee/mainview");
    }

    @RequestMapping(value="customer/customercard", method = RequestMethod.GET)
    public String customerCard(@RequestParam("id") long customerId, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);
        Customer customer = repositoryCustomer.findById(customerId).orElse(null);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));

        if(doesEmployeesCompanyOwnCustomer(employee, customer)) {
            theModel.addAttribute("customer", customer);
            return "customer/customercard";
        }

        return "/error/your-customer-could-not-be-found";
    }

    @RequestMapping("customer/customer-list")
    public String customerView(Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        Employee employee = repositoryEmployee.findById(repositoryUser.findByUserName(principal.getName()).getId()).orElse(null);
        List<Customer> customerList = repositoryCustomer.findByEmployeeOrderByNameAsc(employee);
        theModel.addAttribute("customerList", customerList);
        return "customer/customer-view";
    }

    private void setUpCustomerBeforeSaving(Customer customer, Employee employee) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);
        customer.setEmployee(employee);
        customer.setShoppingCart(shoppingCart);
        customer.setCompany(employee.getCompany());
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
