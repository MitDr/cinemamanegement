package com.project.cinemamanagement.Ultility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

@Component
public class OTPGenerator {
    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "otp", key = "#email")
    public String generateOTP(String email) {
        return String.valueOf(new Random().nextInt(999999));
    }

    @CacheEvict(value = "otp", key = "#email")
    public void invalidCache(String email) {
//        System.out.println("invalid cache "+email);
    }
    public boolean validateCache(String email, String otp){
        String cacheOTP = Objects.requireNonNull(cacheManager.getCache("otp")).get(email, String.class);
        System.out.println(otp + " " + cacheOTP);
        return cacheOTP!= null && cacheOTP.equals(otp);
    }
}
