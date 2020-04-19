package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryOrder extends JpaRepository<Order, Long> {
}
