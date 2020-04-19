package com.johanlind.websalescrm.Services;

import com.johanlind.websalescrm.Repository.RepositoryUser;
import com.johanlind.websalescrm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SalesUserDetailService implements UserDetailsService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repositoryUser.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new PrincipalUser(user);
    }
}
