package com.demo.transactional.service;

import com.demo.transactional.entity.Order;
import com.demo.transactional.entity.User;
import com.demo.transactional.repository.OrderRepository;
import com.demo.transactional.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createOrder(Long userId, Order order) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("<UNK>");
        }
        orderRepository.save(order);
    }
}
