package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends JpaRepository<User, Long> {
    User findByUserName(String username);
    User findById(long id);
}
