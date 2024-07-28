package com.Organizer.organizer.services;

import java.util.List;
import java.util.UUID;

import javax.swing.text.html.Option;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.Organizer.organizer.entity.contact;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.exceptions.NotFoundException;
import com.Organizer.organizer.repoistory.ContactRepo;

import lombok.var;

@Service
public class contactService {
    @Autowired
    private ContactRepo contactRepo;

    @org.springframework.transaction.annotation.Transactional
    public contact save(contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    public List<contact> getAll() {
        return contactRepo.findAll();
    }
    public List<contact> getListOfContacts(user user) {
        return contactRepo.findByUserr(user);
    }

    public contact findById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
    }

    public void delete(String id) {
        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found with given id " + id));
        contactRepo.delete(contact);
    }

    public List<contact> getByUser(user user) {
        return contactRepo.findByUserr(user);
    }

    public Page<contact> getByUser(user user, Pageable pageable) {
        return contactRepo.findByUserr(user, pageable);
    }


    
    //serach
    public List<contact> searchByName(String name){
        return contactRepo.findByName(name);

    }
    public List<contact> searchByLinkdn(String l){
        return contactRepo.findByLinkdn(l);
    }

    public contact update(contact contact){
        contact old=contactRepo.findById(contact.getId()).orElseThrow(()->new NotFoundException("Contact not found"));
        old.setName(contact.getName());
        old.setAddress(contact.getAddress());
        old.setC_email(contact.getC_email());
        old.setDescrip(contact.getDescrip());
        old.setFavourite(contact.isFavourite());
        old.setGitHub(contact.getGitHub());
        old.setLinkdn(contact.getLinkdn());
        old.setPNo(contact.getPNo());
        old.setPicture(contact.getPicture());
        System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        System.out.println(contact.getPicture());
        old.setId(contact.getId());
        contactRepo.save(old);
        System.out.println(old.getPicture());
        return old;
    }
   
    

    // public Page<contact> serachByEmail(String email, int size, int page, String sortBy, String order){
    //     Sort sort=order.equals("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    //     var pageable=PageRequest.of(page,size,sort);
    //     // return contactRepo.findByC_emailContaining(email,pageable);
    // }
    // public List<contact> searchByPno(String pno){
    //     return contactRepo.findByPNoContaining(pno);        
    // }
}