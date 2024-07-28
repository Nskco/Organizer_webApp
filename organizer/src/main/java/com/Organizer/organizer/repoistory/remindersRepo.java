package com.Organizer.organizer.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Organizer.organizer.entity.Reminders;
import com.Organizer.organizer.entity.user;
import java.util.List;

public interface remindersRepo extends JpaRepository<Reminders, Long> {
    List<Reminders> findByUsersss(user user);
}

