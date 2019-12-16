package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Product;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class HelloWorld {

    @RequestMapping("/")
    public String addProduct(Model theModel) {
        theModel.addAttribute("productAdded", new Product());
        return "start-page";
    }

    @RequestMapping("/productConfirmed")
    public String confirmProduct(@ModelAttribute("productAdded") Product product) {
        System.out.println("================IN PRODUCT CONFIRMED ====================");
        System.out.println(product.getName());
        return "confirmation-product-added";
    }
}
