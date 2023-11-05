package com.example.contactlistjdbc.controller;

import com.example.contactlistjdbc.data.Contact;
import com.example.contactlistjdbc.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ContactController {

  public final ContactService contactService;

  @GetMapping("/")
  public String index( Model model ) {
    model.addAttribute( "contacts", contactService.findAll() );

    return "index";
  }

  @GetMapping("/contact/create")
  public String showCreateForm( Model model ) {
    model.addAttribute( "contact", new Contact() );
    model.addAttribute( "typeAction", "create" );

    return "edit";
  }

  @PostMapping("/contact/create")
  public String createContact(@ModelAttribute Contact contact ) {
    contactService.save( contact );

    return "redirect:/";
  }

  @GetMapping("/contact/edit/{id}")
  public String showEditForm(@PathVariable Long id, Model model ) {
    Contact contact = contactService.findById( id );
    if ( contact != null ) {
      model.addAttribute( "contact", contact );
      model.addAttribute( "typeAction", "edit" );
      return "edit";
    }

    return "redirect:/";
  }

  @PostMapping("/contact/edit")
  public String editContact(@ModelAttribute Contact contact) {
    contactService.update( contact );

    return "redirect:/";
  }

  @GetMapping("/contact/delete/{id}")
  public String deleteContact(@PathVariable Long id) {
    contactService.deleteById( id );

    return "redirect:/";
  }
}
