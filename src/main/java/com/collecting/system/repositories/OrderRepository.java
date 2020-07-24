package com.collecting.system.repositories;

import com.collecting.system.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
public interface OrderRepository extends JpaRepository<Order, Long> {

}
