// package com.Organizer.organizer.services;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// @Service
// public class sercurityServices implements UserDetailsService {

//     private final userServices userServices;

//     @Autowired
//     public sercurityServices(userServices userServices) {
//         this.userServices = userServices;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         return userServices.findByEmail(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//     }
// }
