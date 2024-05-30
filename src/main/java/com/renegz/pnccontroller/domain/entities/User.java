package com.renegz.pnccontroller.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sec01_users")
@Data

public class User implements UserDetails {
// Estos son los atributos propios de la entidad User
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) private UUID id;

    private String username;
    private String password;
    private String email;

    @Column(name = "active", insertable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokens;

// Aqui empiezan las implementaciones necesarias para que User implemente UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //getUsername is already overridden

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

}
