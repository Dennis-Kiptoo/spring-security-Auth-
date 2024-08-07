package com.Stoicodex.Spring.Security.ServiceImpl;

import com.Stoicodex.Spring.Security.Services.OTPService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPServiceImpl implements OTPService {

    private final Map<String, String> otpStore = new HashMap<>();

    private final Random random = new Random();

    @Override

    public String generateOTP(String key) {
        String otp = String.valueOf(100000 + random.nextInt(900000));
        otpStore.put(key, otp);
        return otp;
    }

    @Override
    public boolean validateOTP(String key, String otp) {
        String storedOTP = otpStore.get(key);
        return storedOTP != null && storedOTP.equals(otp);
    }


}
