package com.bookcatalog.registration.model;

import com.bookcatalog.model.UserProfile;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(length = 60)    // BCrypt's value is 60 characters long
    private String password;
    private String email;
    private boolean enabled = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;
}
