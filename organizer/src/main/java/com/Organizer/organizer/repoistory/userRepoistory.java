package com.Organizer.organizer.repoistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Organizer.organizer.entity.user;
 //<which entity we are working with and its id datatype 
public interface userRepoistory extends JpaRepository<user,String> {
        Optional<user> findByEmail(String email);
        void deleteUserByEmail(String email);
        Optional<user> findByEmailAndPass(String email,String password);    
}