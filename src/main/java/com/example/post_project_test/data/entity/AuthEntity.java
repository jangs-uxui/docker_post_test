package com.example.post_project_test.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "authtbl")

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthEntity {
    @Id
    @Size(max = 10)
    @Column(name = "username", nullable = false, length = 10)
    private String username;

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Size(max = 10)
    @NotNull
    @Column(name = "role", nullable = false, length = 10)
    private String role;

    @Size(max = 20)
    @NotNull
    @Column(name = "fullname", nullable = false, length = 20)
    private String fullname;

    @Column(name = "joindate")
    private LocalDate joindate;

}