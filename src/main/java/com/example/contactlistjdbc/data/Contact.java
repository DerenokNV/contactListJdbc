package com.example.contactlistjdbc.data;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Contact {

  private long id;

  private String firstName;

  private String lastName;

  private String email;

  private String phone;

}
