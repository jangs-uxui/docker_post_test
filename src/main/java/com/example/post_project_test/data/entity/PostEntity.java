package com.example.post_project_test.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "posttbl")

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postnum", nullable = false)
    private Integer id;

    @Size(max = 225)
    @NotNull
    @Column(name = "title", nullable = false, length = 225)
    private String title;

    @Lob
    @Column(name = "body")
    private String body;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private AuthEntity user;

}