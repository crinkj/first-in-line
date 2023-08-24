package com.api.coupon.service;

import com.api.coupon.domain.persistence.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Test
    @DisplayName("유저가 응모를 했다.")
    void userApplies() {
        // given
        Long userId = 1L;

        // when
        couponService.apply(userId);
        long count = couponRepository.count();

        // then
        Assertions.assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("여러명이 응모를 한번에 했다.")
    public void manyUsersApply() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try {
                    couponService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        long count = couponRepository.count();
        Assertions.assertThat(count).isEqualTo(100);
    }

}