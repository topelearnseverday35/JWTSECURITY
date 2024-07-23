package com.BIGT.security.auth;

import com.BIGT.security.config.JwtService;
import com.BIGT.security.config.ValidationClass;
import com.BIGT.security.user.Role;
import com.BIGT.security.user.User;
import com.BIGT.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ValidationClass helper;


    public String register(RegisterRequest request) {
        boolean isfirstNameValid = helper.firstname_validation(request);
        boolean islastNameValid = helper.lastname_validation(request);
        boolean isemailValid = helper.email_validation(request);
        boolean ispasswordValid = helper.password_validation(request);
        if (!isfirstNameValid) {
            return "Ensure FirstName is properly Inserted";
        }
        if (!islastNameValid) {
            return "Ensure LastName is properly Inserted";
        }
        if(!isemailValid){
            return "Ensure Email is properly Inserted";

        }
        if(!ispasswordValid){
            return "Ensure Password is properly Inserted";
        }
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            userRepository.save(user);


            return "You have been registered";


        }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .response("You have been authenticated")
                .build();


    }


    public ResponseEntity<AuthenticationResponse> updateUserProfile(String jwtToken, UpdateRequest updateRequest) {
        // Validate the JWT token and extract user details
        String userEmail = jwtService.extractUsername(jwtToken);

        // Find the user by email
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update user details if provided in updateRequest
            if (updateRequest.getFirstname() != null) {
                user.setFirstname(updateRequest.getFirstname());
            }
            if (updateRequest.getLastname() != null) {
                user.setLastname(updateRequest.getLastname());
            }
            if (updateRequest.getEmail() != null) {
                user.setEmail(updateRequest.getEmail());
            }
            if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            }

            // Save the updated user
            userRepository.save(user);

            // Return success response
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken, "User details updated successfully"));
        } else {
            // User not found
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }
    }
}




