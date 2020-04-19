package com.johanlind.websalescrm;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class MainViewController {

    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @RequestMapping("/")
    public String getCustomerListForMainView(Model theModel) {
        List<Customer> customerListForMainView = repositoryCustomer.findAll();
        theModel.addAttribute("customerlist", customerListForMainView);
        Customer customer = repositoryCustomer.findById((long) 26).orElse(null);
        System.out.println(customer.getShoppingCart().getId());
        System.out.println(customer.getShoppingCart().getProductList().size());

        for (Product product : customer.getShoppingCart().getProductList()) {
            System.out.println(product.getName());
        }
        return "start";
    }
}
