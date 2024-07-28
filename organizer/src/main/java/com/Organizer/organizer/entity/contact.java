package com.Organizer.organizer.entity;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Contacts")
public class contact {
    @Id
    private String id;
    private String name;
    private String c_email;
    private String address;
    private String picture;
    private String pNo;
    private String descrip;
    private boolean favourite;
    private String linkdn;
    private String gitHub;
    
    // @Transient
    // private MultipartFile p2;//for update

    @ManyToOne
    @JsonIgnore //json doesnot return user then ...as this will form a recursion as user also contains contacts and contacts cointain user
    private user userr;
    // @OneToMany(mappedBy = "contacts",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    // private List<socialLink> contacts=new ArrayList<>();
}