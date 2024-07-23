package com.BIGT.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpDb,Long> {
    OtpDb findOtpDbByOTPAndEmailIs(String OTP, String email);
}
