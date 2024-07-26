package com.BIGT.security.config;


import com.BIGT.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationClass {



    public boolean firstname_validation(RegisterRequest registerRequest) {
        if (registerRequest.getFirstname() != null && registerRequest.getFirstname().length() > 2 && registerRequest.getFirstname().length() < 25) {
            return true;
        }
        return false;
    }

    public boolean lastname_validation(RegisterRequest registerRequest) {
        if (registerRequest.getLastname() != null && registerRequest.getLastname().length() > 2 && registerRequest.getLastname().length() < 25) {
            return true;
        }
        return false;
    }

    public boolean email_validation(RegisterRequest registerRequest) {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return registerRequest.getEmail().matches(regex);
    }

    public boolean password_validation(RegisterRequest registerRequest) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$";
        return registerRequest.getPassword().matches(regex);


    }
}




