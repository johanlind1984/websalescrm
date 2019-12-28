package com.johanlind.websalescrm;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Order;
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
public class ControllerOrder {

    private ServiceDataBase serviceDataBase = new ServiceDataBase();

    @RequestMapping(value="/addorder", method = RequestMethod.GET)
    public String addOrder(@RequestParam("customer") Customer customer, Model theModel) {
        theModel.addAttribute("customer", customer);
        theModel.addAttribute("order", new Order());
        return "add-customer";
    }

    @RequestMapping(value="/orderconfirmed")
    public String confirmOrder(@ModelAttribute("orderadded") Order order, @ModelAttribute("customer") Customer customer) {
        customer.addOrder(order);
        serviceDataBase.saveCustomerToDatabase(customer);
        return "confirmation-order-added";
    }

    @RequestMapping(value="/ordercard", method = RequestMethod.GET)
    public String orderCard(@RequestParam("id") int orderId, Model theModel) {
        Order order = serviceDataBase.getOrder(orderId);
        theModel.addAttribute("order", order);
        return "ordercard";
    }

    @RequestMapping("/orderlist")
    public String orderView(Model theModel) {
        List<Order> orderList = serviceDataBase.getOrderList();
        theModel.addAttribute("orderlist", orderList);

        return "order-view";
    }
}
