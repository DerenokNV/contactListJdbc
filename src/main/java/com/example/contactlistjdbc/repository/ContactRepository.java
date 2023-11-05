package com.example.contactlistjdbc.repository;

import com.example.contactlistjdbc.data.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

  List<Contact> findAll();

  Optional<Contact> findById(Long id );

  Contact save( Contact contact );

  Contact update( Contact contact );

  void deleteById( Long id );

  void batchInsert( List<Contact> contacts );
}
