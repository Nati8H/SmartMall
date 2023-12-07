package com.ngsolutions.SmartMall.repo;

import com.ngsolutions.SmartMall.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameOrEmail(String username, String email);

    User findByUsername(String username);

    Optional<User> findByEmail(String email);
}
