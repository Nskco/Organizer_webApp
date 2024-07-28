package com.Organizer.organizer.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.repoistory.userRepoistory;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private userRepoistory userRepoistory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        user u = userRepoistory.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPass(), new ArrayList<>());
    }
}
