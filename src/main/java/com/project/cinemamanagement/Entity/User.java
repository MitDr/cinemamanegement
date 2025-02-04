package com.project.cinemamanagement.Entity;

import com.project.cinemamanagement.Enum.ROLE;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_username", unique = true)
    private String userName;
    @Column(name = "user_password")
    private String passWord;
    @Column(name = "user_fullname")
    private String fullName;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_phone")
    private String phone;
    @Column(name = "user_address")
    private String address;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;
    @Column(name = "user_refresh_token")
    private String refreshToken;
    @Column(name = "user_verified")
    private boolean verified = false;
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;


}
