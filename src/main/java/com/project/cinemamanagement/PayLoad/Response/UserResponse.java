package com.project.cinemamanagement.PayLoad.Response;

import com.project.cinemamanagement.Entity.Ticket;
import com.project.cinemamanagement.Enum.ROLE;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private ROLE role;
}
