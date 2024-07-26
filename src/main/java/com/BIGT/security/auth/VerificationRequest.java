package com.BIGT.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class VerificationRequest {


    @NotBlank(message = "Otp is empty")
    private String otpInput;


    @Email
    @NotNull  (message = "This Email Field can not be null")
    private String email;



}
