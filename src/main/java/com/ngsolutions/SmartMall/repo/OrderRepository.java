package com.ngsolutions.SmartMall.repo;

import com.ngsolutions.SmartMall.model.entity.Order;
import com.ngsolutions.SmartMall.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
