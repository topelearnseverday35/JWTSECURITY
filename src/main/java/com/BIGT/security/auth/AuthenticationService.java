package com.BIGT.security.auth;

import com.BIGT.security.config.JwtService;
import com.BIGT.security.user.Role;
import com.BIGT.security.user.User;
import com.BIGT.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
    public ResponseEntity<AuthenticationResponse> update(UpdateRequest updateRequest, String jwtToken, UserDetails userDetails) {


        if (jwtService.validateToken(jwtToken, userDetails)) {
            String email = jwtService.extractUsername(jwtToken);
            var user = userRepository.findByEmail(email);



            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            User user1 = user.get();
            user1.setFirstname(updateRequest.getFirstname());
            user1.setLastname(updateRequest.getLastname());
            user1.setEmail(updateRequest.getEmail());
            user1.setPassword(passwordEncoder.encode(updateRequest.getPassword()));

            userRepository.save(user1);

        }


        return new ResponseEntity<>(new AuthenticationResponse(jwtToken,"Email Has been Updated"), HttpStatus.OK);

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail()
                        , request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
           String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}