package com.example.contactlistjdbc.repository;

import com.example.contactlistjdbc.data.Contact;
import com.example.contactlistjdbc.exception.ContactNotFoundException;
import com.example.contactlistjdbc.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class ContactPgRepository implements ContactRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<Contact> findAll() {
    String sql = "select * from CONTACTS";
    return jdbcTemplate.query( sql, new ContactRowMapper() );
  }

  @Override
  public Optional<Contact> findById( Long id ) {
    String sql = "select * from CONTACTS where id = ?";
    Contact contact = DataAccessUtils.singleResult(
            jdbcTemplate.query(
                    sql,
                    new ArgumentPreparedStatementSetter( new Object[] {id} ),
                    new RowMapperResultSetExtractor<>( new ContactRowMapper(), 1 )
            )
    );

    return Optional.ofNullable( contact );
  }

  @Override
  public Contact save( Contact contact ) {
    contact.setId( System.currentTimeMillis() );
    String sql = "insert into CONTACTS( id, firstName, lastName, email, phone ) values( ?, ?, ?, ?, ? )" ;
    jdbcTemplate.update( sql, contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone() );

    return contact;
  }

  @Override
  public Contact update( Contact contact ) {
    Contact existedContact = findById( contact.getId() ).orElse( null );
    if ( existedContact != null ) {
      String sql = "update CONTACTS set FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PHONE = ? WHERE ID = ?";
      jdbcTemplate.update( sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId() );
      return contact;
    }

    log.warn( "Contact with ID {} not found", contact.getId() );

    throw new ContactNotFoundException( "Contact for update not found" );
  }

  @Override
  public void deleteById(Long id) {
    String sql = "delete from CONTACTS where ID = ?";
    jdbcTemplate.update( sql, id );
  }

  @Override
  public void batchInsert(List<Contact> contacts) {
    log.debug( "batchInsert" );

    String sql = "insert into CONTACTS( id, firstName, lastName, email, phone ) values(?, ?, ?, ?, ?)" ;

    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Contact contact = contacts.get( i );
        ps.setLong( 1, contact.getId() );
        ps.setString( 2, contact.getFirstName() );
        ps.setString( 3, contact.getLastName() );
        ps.setString( 4, contact.getEmail() );
        ps.setString( 5, contact.getPhone() );
      }

      @Override
      public int getBatchSize() {
        return contacts.size();
      }
    });
  }
}
