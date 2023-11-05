package com.example.contactlistjdbc.listener;

import com.example.contactlistjdbc.data.Contact;
import com.example.contactlistjdbc.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@ConditionalOnProperty("app.init-contact.enable")
@Slf4j
public class InitContact {

  private final ContactRepository contactRepository;

  public void createContactData() {

    List<Contact> results = new ArrayList<>();

    for ( int i = 0; i < 10; i++ ) {
      int value = i + 1;
      Contact contact = new Contact();
      contact.setId( value );
      contact.setFirstName( "FirstName " + i );
      contact.setLastName( "LastName " + i );
      contact.setEmail( "sdf" + i + "@mail.ru" );
      contact.setPhone( "+" + Math.random() );

      results.add( contact );
    }

    contactRepository.batchInsert( results );
  }

}
