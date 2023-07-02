package com.techcompany.sugcm.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    @Column(name = "user_creation")
    private String userCreation;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @Column(name = "user_modification")
    private String userModification;

    @Column(name = "modification_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modificationDate;
}
