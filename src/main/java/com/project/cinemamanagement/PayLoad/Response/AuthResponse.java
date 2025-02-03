package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Entity.User;
import com.project.cinemamanagement.Enum.ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String accessToken;
    private ROLE role;

    public AuthResponse(User user, String accessToken) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.accessToken = accessToken;
        this.role = user.getRole();
    }

}
