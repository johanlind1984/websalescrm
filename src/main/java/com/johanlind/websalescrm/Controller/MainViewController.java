package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryCompany;
import com.johanlind.websalescrm.Repository.RepositoryCustomer;
import com.johanlind.websalescrm.Repository.RepositoryEmployee;
import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.Utility.WebSalesUtilities;
import com.johanlind.websalescrm.entity.Comment;
import com.johanlind.websalescrm.entity.Customer;
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

    @Autowired
    private RepositoryCompany repositoryCompany;

    @RequestMapping("/employee/mainview")
    public String getCustomerListForMainView(Model theModel, Principal principal) {
        Customer customer = repositoryCustomer.findById((long) 32).orElse(null);

        for (Comment comment : customer.getCommentList()) {
            System.out.println(comment.getCustomer().getName());
            System.out.println(comment.getCommentId());
            System.out.println(comment.getTimestamp());
            System.out.println(comment.getComment());
            System.out.println("========EOF==========");

        }

        User user = repositoryUser.findByUserName(principal.getName());
        Employee employee = employeeRepository.findById(user.getId()).orElse(null);
        theModel.addAttribute("header", WebSalesUtilities.getHeaderString(user));
        theModel.addAttribute("customerList", repositoryCustomer.findByEmployeeOrderByNextContactDateAsc(employee));
        theModel.addAttribute("employee", employee);
        return "start";
    }
}
