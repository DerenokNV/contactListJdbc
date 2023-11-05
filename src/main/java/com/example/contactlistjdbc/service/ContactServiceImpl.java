package com.example.contactlistjdbc.service;

import com.example.contactlistjdbc.data.Contact;
import com.example.contactlistjdbc.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {

  private final ContactRepository contactRepository;

  @Override
  public List<Contact> findAll() {
    return contactRepository.findAll();
  }

  @Override
  public Contact findById(Long id) {
    return contactRepository.findById( id ).orElse( null );
  }

  @Override
  public Contact save(Contact contact) {
    return contactRepository.save( contact );
  }

  @Override
  public Contact update(Contact contact) {
    return contactRepository.update( contact );
  }

  @Override
  public void deleteById(Long id) {
    contactRepository.deleteById( id );
  }
}
