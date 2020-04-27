package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCustomer extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE customer_employee_id = ?1 AND customer_id = ?2", nativeQuery = true)
    Customer findByEmployeeAndCustomerId(Long employee, Long customerId);
}
