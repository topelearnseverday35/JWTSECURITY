package com.BIGT.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder


public class AuthenticationResponse {

    private String token;
    private String response;

}
