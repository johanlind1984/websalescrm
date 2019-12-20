package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class MainViewController {

    private SessionFactory sessionFactory;

    @RequestMapping("/")
    public String addProduct(Model theModel) {
        return "start";
    }
}
