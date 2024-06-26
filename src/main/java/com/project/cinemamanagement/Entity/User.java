package com.project.cinemamanagement.Entity;

import com.project.cinemamanagement.Enum.ROLE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "tbl_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_username")
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
    @OneToMany(mappedBy = "user")
    private List<Ticket> ticket = new ArrayList<>();
}
