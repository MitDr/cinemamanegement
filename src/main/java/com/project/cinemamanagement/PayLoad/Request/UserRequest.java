package com.project.cinemamanagement.PayLoad.Request;

import com.project.cinemamanagement.Entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "username cannot be blank")
    private String userName;
    @NotNull(message = "password cannot be blank")
    private String passWord;
    @NotNull(message = "Fullname cannot be blank")
    private String fullName;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
