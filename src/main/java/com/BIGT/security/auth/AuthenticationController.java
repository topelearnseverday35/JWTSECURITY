package com.BIGT.security.auth;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final Crud crud;



    @PostMapping("/register")
    public String register(
          @Valid @RequestBody RegisterRequest request
    ) {
        return crud.register(request);
    }

    @PostMapping("/verify-email")
    public String verifyEmail(
            @RequestBody VerificationRequest verificationRequest

    ){

        return crud.verifyEmailByOtp(verificationRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request

    ) {
        return ResponseEntity.ok(service.authenticate(request));

    }

    @PutMapping("/update")
    public ResponseEntity<AuthenticationResponse> updateUserDetails(
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwtToken,
            @RequestBody UpdateRequest updateRequest
    ) {


        // Call service method to update user details
        return crud.updateUserProfile(jwtToken,updateRequest);


    }
}