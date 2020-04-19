package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCustomer extends JpaRepository<Customer, Long> {

}
