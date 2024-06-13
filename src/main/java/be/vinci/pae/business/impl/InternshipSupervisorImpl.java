package be.vinci.pae.business.impl;

import java.util.regex.Pattern;

/**
 * This class is used to obtain the implementation of the interface InternshipSupervisor.
 */
public class InternshipSupervisorImpl implements InternshipSupervisor {

  private int id;
  private String firstname;
  private String lastname;
  private String phone;
  private String email;
  private int company;

  /**
   * pojo InternshipSupervisor.
   */
  public InternshipSupervisorImpl() {
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getFirstname() {
    return firstname;
  }

  @Override
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @Override
  public String getLastname() {
    return lastname;
  }

  @Override
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Override
  public String getPhone() {
    return phone;
  }

  @Override
  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int getCompany() {
    return company;
  }

  @Override
  public void setCompany(int company) {
    this.company = company;
  }

  @Override
  public boolean isEmailValidInInternshipSupervisor(String email) {
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    return pattern.matcher(email).matches();
  }
}
