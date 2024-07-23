package com.BIGT.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegisterRequest {
    @NotNull
    @Length(min = 2, max = 25, message = "First Name should be between 2 and 25 characters")
    private String firstname;

    @NotNull
    @Length(min = 3, max = 25,message = "Last Name should be between 3 and 25 characters")
    private String lastname;


    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$",message  = "Password should contain at least ,One Capital Letter,Small Letter,a special character and should be at least 8 characters")
    @NotNull  (message = "The Password Field can not be null")
    private String password;

    @Email
    @NotNull  (message = "This Email Field can not be null")
    private String email;


}

