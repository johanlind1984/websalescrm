package com.johanlind.websalescrm.Repository;

import com.johanlind.websalescrm.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryAuthority extends JpaRepository<Authority, Long> {
}
