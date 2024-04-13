package com.project.cinemamanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tbl_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_description")
    private String  Description;

}
