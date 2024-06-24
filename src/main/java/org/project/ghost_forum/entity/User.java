package org.project.ghost_forum.entity;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "email")
//    @NotNull
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "registration_date")
    @NotNull
    private LocalDate registrationDate;

    @Column(name = "is_banned")
    @Builder.Default
    private Boolean isBanned = false;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @JoinTable(name = "user_role_relation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;
}
