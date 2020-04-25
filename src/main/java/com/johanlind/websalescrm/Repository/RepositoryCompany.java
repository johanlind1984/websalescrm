package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCompany extends JpaRepository<Company, Long> {

}
