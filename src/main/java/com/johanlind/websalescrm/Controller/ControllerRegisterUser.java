package com.johanlind.websalescrm.Controller;

import com.johanlind.websalescrm.Repository.RepositoryAuthority;
import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class ControllerRegisterUser {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryAuthority repositoryAuthority;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String loadModelRegisterUser(Model theModel) {
        theModel.addAttribute("user", new User());
        return "register-form";
    }

    @RequestMapping("/saveuser")
    public String registerUserToDatabase(@ModelAttribute("user") User user) {
        User doesUserExist = repositoryUser.findByUsername(user.getUsername());

        if(doesUserExist == null) {
            setUpAndSaveUser(user);
        } else {
            return "something-went-wrong";
        }

        return "";
    }

    private void setUpAndSaveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority(repositoryAuthority.findById((long) 2).orElse(null));
        user.setEnabled(true);
        repositoryUser.save(user);

    }
}
