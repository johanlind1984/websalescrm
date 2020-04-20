package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.Repository.RepositoryEmployee;
import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.entity.Employee;
import com.johanlind.websalescrm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@EnableAutoConfiguration
public class MainViewController {

    @Autowired
    private RepositoryCustomer repositoryCustomer;

    @Autowired
    RepositoryUser repositoryUser;

    @Autowired
    private RepositoryEmployee employeeRepository;

    @RequestMapping("/")
    public String getCustomerListForMainView(Model theModel, Principal principal) {
        User user = repositoryUser.findByUserName(principal.getName());
        Employee employee = employeeRepository.findById(user.getId()).orElse(null);
        System.out.println(employee.getCompany().getName());
        theModel.addAttribute("customerlist", employee.getCustomerList());
        return "start";
    }
}
