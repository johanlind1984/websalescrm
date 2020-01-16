package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
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
    private DataAccessObject dataContainer = new DataAccessObject();

    @RequestMapping("/")
    public String getCustomerListForMainView(Model theModel) {
        List<Customer> customerListForMainView = dataContainer.getCustomerListMainView();
        theModel.addAttribute("customerlist", customerListForMainView);
        return "start";
    }
}
