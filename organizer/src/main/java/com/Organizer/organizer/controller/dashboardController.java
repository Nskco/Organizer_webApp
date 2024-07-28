package com.Organizer.organizer.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.Organizer.organizer.entity.Reminders;
import com.Organizer.organizer.entity.contact;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.repoistory.remindersRepo;
import com.Organizer.organizer.services.contactService;
import com.Organizer.organizer.services.userServices;
import com.Organizer.organizer.services.contactRelated.remindersServices;

import java.util.*;

@Controller
@RequestMapping("user")
public class dashboardController {
    @Autowired
    private contactService contactService;

    @Autowired
    private userServices userServices;

    @Autowired
    private remindersServices remindersServices;
    @RequestMapping("/view/dashboard")
    public String dashboard(Model model, Principal principal) {
        String name = principal.getName();
        user user = userServices.findByEmail(name).orElse(null);
        
        if (user == null) {
            // Handle the case where the user is not found (optional)
            model.addAttribute("error", "User not found");
            return "error";
        }
    
        List<contact> listt = contactService.getListOfContacts(user);
        int n = listt.size();
    
        List<Reminders> listtt = remindersServices.getAllReminders(user);
        int m = listtt.size();
    
        // Count the number of priority tasks
        long p = listtt.stream()
                                   .filter(Reminders::isPriority) // Filter for priority tasks
                                   .count(); // Count them
    
        model.addAttribute("n", n);
        model.addAttribute("m", m);
        model.addAttribute("p", p);
    
       
    
        return "user/dashboard";
    }
    
}