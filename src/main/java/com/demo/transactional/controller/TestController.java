package com.demo.transactional.controller;

import com.demo.transactional.entity.Order;
import com.demo.transactional.service.OrderService;
import com.demo.transactional.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class TestController {
    private UserService userService;
    private OrderService orderService;

    @GetMapping("/user-error")
    public String testUserError() {
        try {
            userService.createUserWithError();
        } catch (Exception e) {
            return "Rolled back: " + e.getMessage();
        }
        return "Should not reach here";
    }

    @GetMapping("/order")
    public String testOrder() {
        Order order = Order.builder().item("bool").price(BigDecimal.valueOf(10000)).build();
        orderService.createOrder(1L, order);
        return "Order created";
    }

    @GetMapping("/tx-a")
    public String runTxA() {
        new Thread(() -> userService.updateUserBalance(1L)).start();
        return "Started Tx A - Update with delay";
    }

    @GetMapping("/tx-b")
    public String runTxB() throws InterruptedException {
        return "Balance read: " + userService.checkUserBalance(1L);
    }

}
