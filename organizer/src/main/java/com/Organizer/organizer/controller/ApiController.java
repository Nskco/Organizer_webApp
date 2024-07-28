package com.Organizer.organizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Organizer.organizer.entity.contact;
import com.Organizer.organizer.services.contactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private contactService contactService;
    @GetMapping("/contactInfo/{id}")
        public contact contactInfo(@PathVariable String id){
            return contactService.findById(id);
        }
}