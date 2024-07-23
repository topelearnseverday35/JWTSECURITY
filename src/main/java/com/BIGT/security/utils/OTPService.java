package com.BIGT.security.utils;

import com.BIGT.security.auth.AuthenticationService;
import com.BIGT.security.auth.RegisterRequest;
import com.BIGT.security.user.OtpDb;
import com.BIGT.security.user.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class OTPService {

    private final OtpRepository otpRepository;



    public String generateOTP() {
        Random rand = new Random();
        Integer max = 9999;
        Integer min = 1000;
        Integer otp = rand.nextInt(min, max);
        return String.valueOf(otp);


    }

    public  void OtpStorage(String otpValue,RegisterRequest registerRequest) {

        OtpDb otpDb = new OtpDb();
        otpDb.setEmail(registerRequest.getEmail());
        otpDb.setOTP(otpValue);
        otpDb.setGeneratedDate(LocalDateTime.now());
        otpDb.setExpiryDate(LocalDateTime.now().plusMinutes(3));

        otpRepository.save(otpDb);

    }
}