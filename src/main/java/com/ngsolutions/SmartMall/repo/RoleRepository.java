package com.ngsolutions.SmartMall.repo;

import com.ngsolutions.SmartMall.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
