package com.Organizer.organizer.controller;

import java.security.Principal;
import java.util.*;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Organizer.organizer.entity.Reminders;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.services.userServices;
import com.Organizer.organizer.services.contactRelated.remindersServices;

@Controller
@RequestMapping("/user")
public class userController {
   @Autowired
   private userServices userServices;


    @GetMapping
    public String user(){
        return "user";
    }
    @RequestMapping("/dashboard")
    public  String dashboard(){

        return "user/dashboard";
    }

    @Autowired
    private remindersServices remindersServices;
    @RequestMapping("/showTasks")
    public String profile(Model model, Principal principal){
        String name=principal.getName();
        user user=userServices.findByEmail(name).orElse(null);
        List<Reminders> list=remindersServices.getAllReminders(user);
        model.addAttribute("list", list);
        return "user/tasks";
    }

    @DeleteMapping("/deleteContactTask/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes){
        remindersServices.delete(id);
        return "/user/tasks";
}

}
