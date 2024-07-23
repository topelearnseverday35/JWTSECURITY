package com.BIGT.security.user;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Entity
@Table
@Data
@RequiredArgsConstructor
@Component
public class OtpDb {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;
 @Column(unique = true)
 private String email;
 @Column(unique = true)
 private String OTP;
 @Temporal(TemporalType.TIMESTAMP)
 private LocalDateTime generatedDate;
 @Temporal(TemporalType.TIMESTAMP)
 private LocalDateTime expiryDate;


}
