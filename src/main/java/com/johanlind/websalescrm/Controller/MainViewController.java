package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.Repository.RepositoryEmployee;
import com.johanlind.websalescrm.entity.Employee;
import com.johanlind.websalescrm.entity.Order;
import com.johanlind.websalescrm.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
public class MainViewController {

    @Autowired
    private RepositoryCustomer repositoryCustomer;


    @Autowired
    private RepositoryEmployee employeeRepository;

    @RequestMapping("/")
    public String getCustomerListForMainView(Model theModel) {
        Employee employee = employeeRepository.findById((long) 3).orElse(null);

        System.out.println(
                "===========================================\n" +
                        employee.getFirstName() + "\n" +
                        employee.getLastName() + "\n" +
                        employee.getEmail() + "\n" +
                        employee.getPhone() + "\n" +
                        employee.getUsername() + "\n" +
                        employee.getPassword() + "\n"
        );


        for (Order order: employee.getOrderList()) {
            System.out.println(order.getOrderId());
            System.out.println(order.getCustomer().getName());
            for (Product product : order.getProductsOrdered()) {
                System.out.println(product.getName());
            }
        }
        return "start";
    }
}
