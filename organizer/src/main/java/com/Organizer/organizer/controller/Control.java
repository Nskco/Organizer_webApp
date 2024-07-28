package com.Organizer.organizer.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Organizer.organizer.entity.emailVerify;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.exceptions.EmailExists;
import com.Organizer.organizer.forms.R_form;
import com.Organizer.organizer.services.EmailService;
import com.Organizer.organizer.services.userServices;

import jakarta.validation.Valid;

@Controller
public class Control {
    @Autowired
    userServices uServices;

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("currentPage", "home");
        return "home";
    }

    @GetMapping("/")
    public String homee() {
        return "redirect:/home";
    }

    @RequestMapping("/contact")
    public String contact(Model model){
        model.addAttribute("currentPage", "contact");
        return "contact";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("currentPage", "login");
        return "login";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("currentPage", "about");
        return "about";
    }

    @RequestMapping("/services")
    public String services(Model model){
        model.addAttribute("currentPage", "services");
        return "services";
    }
    @GetMapping("/verify")
    public String verify(Model model){
        emailVerify emailVerify=new emailVerify();
        model.addAttribute("emailVerify", emailVerify);
        return "verifyEmail";
    }








    // for registration purpose
    //here email verification
    @Autowired
    private EmailService emailService;

@PostMapping("/verify")
public String verifyEmail(@ModelAttribute("emailVerify") @Valid emailVerify emailVerify, BindingResult bindingResult, Model model) {
    if (uServices.isUserExists(emailVerify.getEmail())) {
        bindingResult.rejectValue("email", "error.email", "Mail exists");
        return "verifyEmail";
    }

    // Generate OTP code
    String code = new java.security.SecureRandom().ints(4, 0, 10)
            .mapToObj(Integer::toString)
            .reduce("", (a, b) -> a + b);
    String codeMessage="Your OTP is: "+code;
    // Send email with OTP
    String mail = emailVerify.getEmail();
    String subject = "Your verification code for registration on Organizer is";
    emailService.sendEmail(mail, subject, codeMessage);

    // Prepare the model for the next view
    emailVerify emailVerify2 = new emailVerify();
    emailVerify2.setEmail(mail);
    emailVerify2.setOtp(code);
    model.addAttribute("emailVerify2", emailVerify2);

    return "verifyEmail2";
}

    @PostMapping("/verifyOtp")
    public String otp(Model model,@ModelAttribute("emailVerify2")emailVerify emailVerify2, BindingResult bindingResult){
        if(!emailVerify2.getOtp().equals(emailVerify2.getOtp2()))
        {
            bindingResult.rejectValue("otp2", "error.otp2", "otp do not match");
            return "verifyEmail2";
        }

        R_form uForm = new R_form();
        uForm.setAbout("");
        uForm.setEmail(emailVerify2.getEmail());
        model.addAttribute("uForm", uForm);
        model.addAttribute("currentPage", "register");
        return "register";
        
    }

    

    //saving of user data
    @PostMapping("/registerData")
    public String processRegistration(Model model,@Valid @ModelAttribute("uForm") R_form uForm, BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!uForm.getPass().equals(uForm.getConfirmPass())) {
            bindingResult.rejectValue("confirmPass", "error.confirmPass", "Passwords do not match");
            return "register";
        }

        try {
            user u = new user();
            u.setEmail(uForm.getEmail());
            u.setAbout(uForm.getAbout());
            u.setPass(uForm.getPass());
            u.setName(uForm.getName());
            u.setPic("/resources/static/images/defaultPic.png");
            u.setNumber(uForm.getNumber());

            uServices.save(u);
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already in use. Please use a different email.");
        }

        return "redirect:/register";
    }

    @GetMapping("/register")
public String showRegisterForm(Model model) {
    if (!model.containsAttribute("uForm")) {
        model.addAttribute("uForm", new R_form());
    }
    return "register";
}

}
