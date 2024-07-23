package com.BIGT.security.auth;


import lombok.Builder;
import lombok.Data;


@Data
@Builder


public class AuthenticationResponse {

    private String token;
    private String response;

}
