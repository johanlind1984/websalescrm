package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Employee;
import com.johanlind.websalescrm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryOrder extends JpaRepository<Order, Long> {

    Order findByOrderIdAndEmployee(long id, Employee employee);
    List<Order> findByEmployee(Employee employee);

}
