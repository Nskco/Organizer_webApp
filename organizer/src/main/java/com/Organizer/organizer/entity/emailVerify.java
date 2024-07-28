package com.Organizer.organizer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class emailVerify {
    @Id
    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    private String otp;
    private String otp2;
}