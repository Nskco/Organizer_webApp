package com.Organizer.organizer.repoistory;
import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Organizer.organizer.entity.contact;
import com.Organizer.organizer.entity.user;

public interface ContactRepo extends JpaRepository<contact, String> {
    List<contact> findByUserr(user userr);

    Page<contact> findByUserr(user userr, Pageable pageable);

    public List<contact> findByName(String name);
    public List<contact> findByLinkdn(String l);

    // public abstract Page<contact> findByC_emailContaining(String c_email, Pageable pageable);
    
    // public List<contact> findByPNoContaining(String pno);
}