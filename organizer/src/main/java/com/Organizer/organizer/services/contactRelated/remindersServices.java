package com.Organizer.organizer.services.contactRelated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.Organizer.organizer.entity.Reminders;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.repoistory.remindersRepo;



    @Service
    public class remindersServices {
        @Autowired
        private remindersRepo remindersRepo;
    
        public void save(Reminders reminders, user user) {
            reminders.setUsersss(user); // Set the user object before saving
            remindersRepo.save(reminders);
        }
    
        public List<Reminders> getAllReminders(user user) {
            return remindersRepo.findByUsersss(user);
        }
    
        public void delete(Long id) {
            remindersRepo.deleteById(id);
        }
    }
    

