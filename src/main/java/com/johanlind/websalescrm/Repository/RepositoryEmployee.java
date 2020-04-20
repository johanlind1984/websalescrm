package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEmployee extends JpaRepository<Employee, Long> {
}
