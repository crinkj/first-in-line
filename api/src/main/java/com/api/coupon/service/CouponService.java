package com.api.coupon.service;

import com.api.coupon.domain.Coupon;
import com.api.coupon.domain.persistence.CouponCountRepository;
import com.api.coupon.domain.persistence.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;

    public void apply(Long userId) {
        Long count = couponCountRepository.increment();
        if(count > 100){
            return;
        }
         couponRepository.save(Coupon.create(userId));
    }

}
