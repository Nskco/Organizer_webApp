package com.Organizer.organizer.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "user")
@Component
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@ToString
public class user implements UserDetails {

    @Id
    private String userID;
    @Column(name = "user_n", nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String number;
    private String pass;
    @Column(length = 1000)
    private String about;
    @Column(length = 500)
    private String pic;
    private String gender;

    @Getter(AccessLevel.NONE)
    private boolean enabled = true;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;
    @Enumerated(EnumType.STRING)
    private provider providers = provider.SELF;
    private String p_UserId = null;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.pass;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @OneToMany(mappedBy = "usersss", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reminders> reminders = new ArrayList<>();

    // Other fields and methods...
}
