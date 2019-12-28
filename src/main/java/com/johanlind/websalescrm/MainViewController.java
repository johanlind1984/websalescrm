package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class MainViewController {

//    private SessionFactory sessionFactory;
    private ServiceDataBase dataContainer = new ServiceDataBase();

    @RequestMapping("/")
    public String getCustomerListForMainView(Model theModel) {
        List<Customer> customerList = dataContainer.getCustomerList();
        theModel.addAttribute("customerlist", customerList);
        return "start";
    }
}
