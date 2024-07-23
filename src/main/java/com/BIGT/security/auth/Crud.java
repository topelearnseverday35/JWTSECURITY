package com.BIGT.security.auth;

import com.BIGT.security.config.JwtService;
import com.BIGT.security.user.*;
import com.BIGT.security.utils.EmailService;
import com.BIGT.security.utils.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Crud {
    private final ProfileRepo userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final OtpRepository otpRepository;
    private final OTPService otpService;
    final long OTP_EXPIRATION_MINUTES = 3; // OTP validity period in minutes

    public String register(RegisterRequest request) {
        Optional<ProfileCreationDb> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            return "Email Already Exists";

        } else {
            ProfileCreationDb user2 = new ProfileCreationDb();
            user2.setFirstname(request.getFirstname());
            user2.setLastname(request.getLastname());
            user2.setEmail(request.getEmail());
            user2.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user2);
            sendEmail(request);

            return "Profile Created Successfully";
        }
    }

    @Async
    public void sendEmail(RegisterRequest request) {
        String otpValue = otpService.generateOTP();
        otpService.OtpStorage(otpValue, request);
        emailService.sendEmail(request.getEmail(), "CONFIRMATION OF EMAIL", "Hello " + request.getFirstname() + " " + request.getLastname() + "\n" +
                "YourOTP is " + otpValue + "\n" + "Note this OTP lasts for only 3 Minutes "
        );
    }


    public String verifyEmailByOtp(VerificationRequest verificationRequest) {

        Optional<ProfileCreationDb> user = userRepository.findByEmail(verificationRequest.getEmail());
        if (user.isPresent()) {
            OtpDb otpDb = otpRepository.findOtpDbByOTPAndEmailIs(verificationRequest.getOtpInput(),
                    verificationRequest.getEmail());

            if (otpDb != null) {
                if (otpDb.getOTP().equalsIgnoreCase(verificationRequest.getOtpInput())) {

                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime otpCreationTime = otpDb.getGeneratedDate();
                    long minutesElapsed = ChronoUnit.MINUTES.between(otpCreationTime, now);


                    if (minutesElapsed <= OTP_EXPIRATION_MINUTES) {
                        emailService.sendEmail(verificationRequest.getEmail(), "Registration Successful", "Welcome " + user.get().getFirstname() + " to BIG T's Service \n" +
                                "Your Name is  " + user.get().getFirstname() + " " + user.get().getLastname() + "\n" +
                                "Your UserName is  " + user.get().getEmail() + "\n");
                        return "Email has been verified";

                    }return "OTP has expired";

                }  return "Invalid Otp";

            }return "Invalid Otp";


        }return "User Not Found, Invalid email!!!";


    }




    public ResponseEntity<AuthenticationResponse> updateUserProfile(String jwtToken, UpdateRequest updateRequest) {
        // Validate the JWT token and extract user details
        String userEmail = jwtService.extractUsername(jwtToken);

        // Find the user by email
        Optional<ProfileCreationDb> optionalUser = userRepository.findByEmail(userEmail);

        if (optionalUser.isPresent()) {
            ProfileCreationDb user = optionalUser.get();

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
