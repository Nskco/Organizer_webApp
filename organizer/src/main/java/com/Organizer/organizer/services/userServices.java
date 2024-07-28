package com.Organizer.organizer.services;

import com.Organizer.organizer.exceptions.ContantsAPP;
import com.Organizer.organizer.exceptions.EmailExists;
import com.Organizer.organizer.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.repoistory.userRepoistory;

@Service
public class userServices {
    @Autowired
    private userRepoistory userRepoistory;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Constructor injection
    // private final userRepoistory userRepoistory;
    // private final PasswordEncoder passwordEncoder;

    // @Autowired
    // public userServices(userRepoistory userRepoistory, @Lazy PasswordEncoder passwordEncoder) {
    //     this.userRepoistory = userRepoistory;
    //     this.passwordEncoder = passwordEncoder;
    // }

    public user save(user user) {
        String id = UUID.randomUUID().toString();
        user.setUserID(id);
        user.setPass(passwordEncoder.encode(user.getPass()));
        userRepoistory.save(user);
        user.setRoles(List.of(ContantsAPP.ROLE_USER));
        return user;
    }

    public user findById(String id) {
        return userRepoistory.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Optional<user> findByEmail(String email) {
        return userRepoistory.findByEmail(email);
    }

    public boolean isUserExists(String email) {
        return userRepoistory.findByEmail(email).isPresent();
    }

    public void updateUser(user user) throws EmailExists {
        user u1 = userRepoistory.findById(user.getUserID())
                .orElseThrow(() -> new NotFoundException("User not found"));
        u1.setAbout(user.getAbout());
        u1.setEmail(user.getEmail());
        u1.setNumber(user.getNumber());
        u1.setPass(user.getPass());
        u1.setPic(user.getPic());
        userRepoistory.save(u1);
    }
}
