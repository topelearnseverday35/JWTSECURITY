package com.BIGT.security.auth;

import com.BIGT.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;


    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
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
        return service.updateUserProfile(jwtToken,updateRequest);


    }
}