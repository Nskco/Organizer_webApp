package com.Organizer.organizer.forms;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class R_form {
  
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 4, max = 8, message = "Password must be between 4 and 8 characters")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{4,8}$", message = "Password must contain at least one special character")
    private String pass;

    @NotEmpty(message = "Contact number is required")
    @Pattern(regexp = "\\d{8,12}", message = "Contact number must be between 8 and 12 digits")
    // \ant digit
    private String number;
    private String about;
    private String confirmPass;
}
