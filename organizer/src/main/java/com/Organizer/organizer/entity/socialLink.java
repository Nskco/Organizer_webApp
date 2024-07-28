package com.Organizer.organizer.entity;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity(name = "Social_media")
public class socialLink {
    @Id
    private String Id;
    private String title;
    private String link;

    @ManyToOne
    private contact contacts;
}
