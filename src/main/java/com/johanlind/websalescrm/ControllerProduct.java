package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Product;
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
public class ControllerProduct {

    private ServiceDataBase serviceDataBase = new ServiceDataBase();

    @RequestMapping("/addproduct")
    public String addProduct(Model theModel) {
        theModel.addAttribute("productadded", new Product());
        return "add-product";
    }

    @RequestMapping("/productconfirmed")
    public String confirmProduct(@ModelAttribute("productadded") Product product) {
        serviceDataBase.saveProductToDatabase(product);
        return "confirmation-product-added";
    }

    @RequestMapping("/productlist")
    public String listProducts(Model theModel) {
        List<Product> productList = serviceDataBase.getProductList();
        theModel.addAttribute("productlist", productList);
        return "product-view";
    }

    @RequestMapping(value="/deleteproduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") int productId, Model theModel) {
        serviceDataBase.deleteProduct(productId);
        List<Product> productList = serviceDataBase.getProductList();
        theModel.addAttribute("productlist", productList);
        return "product-view";
    }

    @RequestMapping(value="/updateproductform", method = RequestMethod.GET)
    public String updateProductForm(@RequestParam("id") int productId, Model theModel) {
        Product product = serviceDataBase.getProduct(productId);
        theModel.addAttribute("product", product);
        return "update-product";
    }


    @RequestMapping(value="/updateproduct")
    public String updateProduct(@ModelAttribute("product") Product product, Model theModel) {
        serviceDataBase.updateProduct(product);
        List<Product> productList = serviceDataBase.getProductList();
        theModel.addAttribute("productlist", productList);
        return "product-view";
    }
}
