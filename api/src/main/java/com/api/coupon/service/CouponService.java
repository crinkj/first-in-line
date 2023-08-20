package com.api.coupon.service;

import com.api.coupon.domain.Coupon;
import com.api.coupon.domain.persistence.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void apply(Long userId) {
        long count = couponRepository.count();
        if(count > 100){
            return;
        }
         couponRepository.save(Coupon.create(userId));
    }

}
