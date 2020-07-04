package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryProduct;
import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.Utility.WebSalesUtilities;
import com.johanlind.websalescrm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("/product")
public class ControllerProduct {

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Autowired
    private RepositoryUser repositoryUser;

    @RequestMapping("/crud/addproduct")
    public String addProduct(Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        theModel.addAttribute("productadded", new Product());
        return "product/crud/add-product";
    }

    @RequestMapping("/crud/productconfirmed")
    public String confirmProduct(@ModelAttribute("productadded") Product product, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        repositoryProduct.save(product);
        return "product/crud/confirmation-product-added";
    }

    @RequestMapping("/productlist")
    public String listProducts(Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        List<Product> productList = repositoryProduct.findAll();
        theModel.addAttribute("productlist", productList);
        return "product/list/product-list";
    }

    @RequestMapping(value="/crud/deleteproduct", method = RequestMethod.GET)
    public String deleteProduct(@RequestParam("id") long productId, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        repositoryProduct.deleteById(productId);
        List<Product> productList = repositoryProduct.findAll();
        theModel.addAttribute("productlist", productList);
        return "product/list/product-list";
    }

    @RequestMapping(value="/crud/updateproductform", method = RequestMethod.GET)
    public String updateProductForm(@RequestParam("id") long productId, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        Product product = repositoryProduct.findById(productId).orElse(null);
        theModel.addAttribute("product", product);
        return "product/crud/update-product";
    }


    @RequestMapping(value="/updateproduct")
    public String updateProduct(@ModelAttribute("product") Product product, Model theModel, Principal principal) {
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(repositoryUser.findByUserName(principal.getName())));
        repositoryProduct.save(product);
        List<Product> productList = repositoryProduct.findAll();
        theModel.addAttribute("productlist", productList);
        return "product/list/product-list";
    }
}
