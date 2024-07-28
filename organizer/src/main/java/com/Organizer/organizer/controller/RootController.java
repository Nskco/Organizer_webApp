package com.Organizer.organizer.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.Organizer.organizer.helpers.helpers;

import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.repoistory.userRepoistory;
import com.Organizer.organizer.services.userServices;

@ControllerAdvice
public class RootController {
    @Autowired
    private userServices userServices;

    @ModelAttribute
   public void getLoggedUserInfo(Model model, Principal principal){
    if(principal==null){
        return;
    }
    String ename=helpers.loggedInUserEmail(principal);

    System.out.println(ename);
    user user=userServices.findByEmail(ename).orElse(null);
    if(user==null){
            model.addAttribute("logUser", null);
    }
    else{
    System.out.println(user.getName());
    model.addAttribute("logUser", user);
   }
}
}