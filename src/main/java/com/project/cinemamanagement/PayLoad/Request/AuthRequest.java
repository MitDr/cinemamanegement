package com.project.cinemamanagement.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "username must not be blank")
    @Size(min = 4, max = 20, message = "username must be between 4 and 20 characters")
    private String username;

    @Size(min = 4, max = 20, message = "password must be between 4 and 20 characters")
    @NotBlank(message = "password must not be blank")
    private String password;
}
