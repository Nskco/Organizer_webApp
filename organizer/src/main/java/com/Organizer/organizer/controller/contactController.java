package com.Organizer.organizer.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Organizer.organizer.entity.Email;
import com.Organizer.organizer.entity.contact;
import com.Organizer.organizer.entity.contactForm;
import com.Organizer.organizer.entity.Reminders;
import com.Organizer.organizer.entity.user;
import com.Organizer.organizer.services.EmailService;
import com.Organizer.organizer.services.ImageServices;
import com.Organizer.organizer.services.contactService;
import com.Organizer.organizer.services.userServices;
import com.Organizer.organizer.services.contactRelated.remindersServices;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/user/contact")
public class contactController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private contactService contactService;

    @Autowired
    private userServices userServices;    

    @Autowired
    private ImageServices imageServices;

    private static final Logger logger = LoggerFactory.getLogger(contactController.class);

    @RequestMapping("/add")
    public String addContact(Model model) {
        contactForm cf = new contactForm();
        model.addAttribute("cf", cf);
        return "/user/addContact";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("cf") contactForm cf, Principal principal, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors: " + bindingResult.getAllErrors());
            return "user/addContact";
        }

        String userName = principal.getName();
        user u = userServices.findByEmail(userName).orElse(null);

        String filename = UUID.randomUUID().toString();
        logger.info("Generated filename: " + filename);
       
        String picURL = imageServices.image(cf.getPicture(), filename);
        try {
            contact contact = new contact();
            contact.setName(cf.getName());
            contact.setC_email(cf.getEmail());
            contact.setAddress(cf.getAddress());
            contact.setDescrip(cf.getDescription());
            contact.setLinkdn(cf.getLinkedInLink());
            contact.setGitHub(cf.getGitHubLink());
            contact.setFavourite(cf.isFavourite());
            contact.setPNo(cf.getPhoneNumber());
            contact.setUserr(u);
            contact.setPicture(picURL);
            // contact.setP2(cf.getPicture());
            contactService.save(contact);

            redirectAttributes.addFlashAttribute("successmessage", "Contact Added Successfully");
        } catch (Exception e) {
            logger.error("Error adding contact", e);
            redirectAttributes.addFlashAttribute("errormessage", "Contact Not Added");
        }
        return "redirect:/user/contact/add";
    }

    @RequestMapping("/contacts")
    public String showContacts(Model model, Principal principal, 
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        String userName = principal.getName();
        user u = userServices.findByEmail(userName).orElse(null);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<contact> contactsPage = contactService.getByUser(u, pageable);
        
        model.addAttribute("contactsPage", contactsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", contactsPage.getTotalPages());
        
        return "user/contacts";
    }


    ///search controller
    @RequestMapping("/search")
    public String search(@RequestParam("keyword") String keyword,@RequestParam("field") String field, Model model) {
        // Fetch contacts based on the name
        List<contact> listContact=null;
        if(field.equalsIgnoreCase("name")){
          listContact = contactService.searchByName(keyword);
        }
        else if(field.equalsIgnoreCase("link")){
             listContact = contactService.searchByLinkdn(keyword);

        }

        // Add the search results to the model
        model.addAttribute("pageContact", listContact);
        return "user/searchPage";
    }
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") String contactId, Model model) {
    contact contact = contactService.findById(contactId);
    model.addAttribute("contact", contact);



    
    return "user/view";
}

@RequestMapping("/delete/{id}")
public String deleteContact(@PathVariable String id){
    contactService.delete(id);
    return "redirect:/user/contact";

}

@GetMapping("/update/{id}")
public String updateContact(@PathVariable String id, Model model){
    contact contact = contactService.findById(id);
    contactForm cf=new contactForm();
    cf.setEmail(contact.getC_email());
    cf.setName(contact.getName());
    cf.setDescription(contact.getDescrip());
    cf.setFavourite(contact.isFavourite());
    cf.setAddress(contact.getAddress());
    cf.setPhoneNumber(contact.getPNo());
    cf.setGitHubLink(contact.getGitHub());
    cf.setLinkedInLink(contact.getLinkdn());
    cf.setPic(contact.getPicture());

    cf.setId(id);
    model.addAttribute("cf", cf);
    model.addAttribute("id", id);
    return "user/update";
}    

@PostMapping("/update/{id}")
public String update(@PathVariable String id, @ModelAttribute contactForm cf,Model model,RedirectAttributes redirectAttributes){
    contact contact=contactService.findById(id);
    contact.setC_email(cf.getEmail());
    contact.setAddress(cf.getAddress());
    contact.setDescrip(cf.getDescription());
    contact.setName(cf.getName());
    contact.setPNo(cf.getPhoneNumber());
    contact.setLinkdn(cf.getLinkedInLink());
    contact.setGitHub(cf.getGitHubLink());
    contact.setId(id);
    System.out.println(cf.getPic());
    contact.setPicture(cf.getPic());

    contactService.update(contact);
    System.out.println(contact.getPicture());
    redirectAttributes.addFlashAttribute("successmessage", "Contact updated successfully!");

    return "redirect:/user/contact/update/"+contact.getId();

}

@GetMapping("/mail/{em}/{name}")
public String sendEmail(@PathVariable String em,@PathVariable String name, Model model, @ModelAttribute("successMsg") String successMsg) throws UnsupportedEncodingException {
    // Decode the email address
    String decodedEmail = URLDecoder.decode(em, StandardCharsets.UTF_8.toString());
    Email email = new Email();
    email.setClient(decodedEmail);
    email.setUser(name);
    model.addAttribute("email", email);
    if (!successMsg.isEmpty()) {
        model.addAttribute("successMsg", successMsg);
    }
    return "user/emailForm";
}

@PostMapping("/mail")
public String mailSender(@ModelAttribute Email email, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
    String receiver = email.getClient();
    String subject = "From our client:"+email.getUser()+" - "+email.getSub();
    String msg = email.getMsg();

    emailService.sendEmail(receiver, subject, msg);
    redirectAttributes.addFlashAttribute("successMsg", "Email sent successfully.");

    return "redirect:/user/contact/mail/" + email.getClient() +"/"+email.getUser();
}


@RequestMapping("/addTask/{name}")
public String addTask(Model model, @PathVariable String name) {
    Reminders reminders = new Reminders();
    reminders.setRemName(name);

    model.addAttribute("reminders", reminders);
    return "user/addTask";
}

@Autowired
private remindersServices remindersServices;

  @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTask(@ModelAttribute Reminders reminder, Principal principal, RedirectAttributes redirectAttributes) {
        String name = principal.getName();
        user user = userServices.findByEmail(name).orElse(null);

        if (user != null) {
            remindersServices.save(reminder, user); // Pass the user object
            redirectAttributes.addFlashAttribute("successMessage", "Task added successfully!");
        }
        
        return "redirect:/user/contact/addTask/" + reminder.getRemName(); // Redirect to the addTask page with the name parameter
    }


@RequestMapping("/showTasks")
public String profile(Model model, Principal principal) {
    String name = principal.getName();
    user user = userServices.findByEmail(name).orElse(null);
    if (user != null) {
        List<Reminders> list = remindersServices.getAllReminders(user);
        model.addAttribute("list", list);
    }
    return "user/tasks";
}

@DeleteMapping("/deleteContactTask/{id}")
public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes){
    remindersServices.delete(id);
    return "redirect:/user/showTasks";
}   

}