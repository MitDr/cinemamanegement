package com.project.cinemamanagement.PayLoad.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "password must not be blank")
    private String newPassword;
    @NotBlank(message = "password must not be blank")
    private String confirmPassword;
    @NotBlank(message = "otp must not be blank")
    private String otp;
}
