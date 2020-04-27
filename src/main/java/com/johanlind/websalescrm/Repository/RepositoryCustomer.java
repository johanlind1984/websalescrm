package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Customer;
import com.johanlind.websalescrm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryCustomer extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer WHERE customer_employee_id = ?1 AND customer_id = ?2", nativeQuery = true)
    Customer findByEmployeeAndCustomerId(Long employeeId, Long customerId);

    List<Customer> findByEmployeeOrderByNextContactDateAsc(Employee employee);
    List<Customer> findByEmployeeOrderByNameAsc(Employee employee);

}
