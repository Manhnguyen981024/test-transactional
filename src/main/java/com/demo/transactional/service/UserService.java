package com.demo.transactional.service;

import com.demo.transactional.entity.User;
import com.demo.transactional.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepo;
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public void createUserWithError() {
        User user = User.builder()
                .balance(BigDecimal.valueOf(10000))
                .username("manhpro")
                .password("abc123")
                .build();
        userRepo.save(user);
    }

    @Transactional( propagation = Propagation.REQUIRED)
    public void updateUserBalance(Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        user.setBalance(user.getBalance().add(BigDecimal.valueOf(1000)));
        userRepo.save(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal checkUserBalance(Long userId) throws InterruptedException {
        User user = userRepo.findById(userId).orElseThrow();

        log.info("USER 1: {} " , user);
        Thread.sleep(10000);

        entityManager.clear();

        User user1 = userRepo.findById(userId).orElseThrow();

        log.info("USER 2: {} " , user1);
        return user1.getBalance();
    }

}
