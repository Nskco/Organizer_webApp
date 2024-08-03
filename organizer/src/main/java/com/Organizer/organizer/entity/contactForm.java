package com.Organizer.organizer.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class contactForm {
    @Id
    private String id;
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "\\d{8,12}", message = "Contact number must be between 8 and 12 digits")
    private String phoneNumber;
    private String address;

    private MultipartFile picture;
    private String description;
    private boolean favourite;
    private String gitHubLink;
    private String linkedInLink;
    
    @Transient
    private String pic;
   
    // private String upict; //for update purposes
    //private List<String> socialLinks=new ArrayList<>();
    // private String cloudinaryImagePublicId;
}
