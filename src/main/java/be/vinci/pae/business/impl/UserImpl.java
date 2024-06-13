package be.vinci.pae.business.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

/**
 * UserImpl class.
 */
public class UserImpl implements User {

  private static final String[] POSSIBLE_UTILISATEUR_TYPE = {"professeur", "étudiant",
      "administratif"};
  private int userId;
  private String email;
  // @JsonIgnore
  private String password;
  // @JsonIgnore
  private String newPassword;
  // @JsonIgnore
  private String newPasswordConfirmation;
  private String lastName;
  private String phone;
  private String firstName;
  private Date registrationDate;
  private String academicYear;
  private String utilisateurType;
  private int version;
  private int stageId;

  /**
   * pojo User.
   */
  public UserImpl() {
  }

  @Override
  public int getStageId() {
    return stageId;
  }

  @Override
  public void setStageId(int stageId) {
    this.stageId = stageId;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
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
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
  public Date getRegistrationDate() {
    return registrationDate;
  }

  @Override
  public void setRegistrationDate(Date registrationDate) {
    this.registrationDate = registrationDate;
  }

  @Override
  public String getAcademicYear() {
    return academicYear;
  }

  @Override
  public void setAcademicYear(String academicYear) {
    this.academicYear = academicYear;
  }

  @Override
  public String getUtilisateurType() {
    return utilisateurType;
  }

  @Override
  public void setUtilisateurType(String utilisateurType) {
    this.utilisateurType = utilisateurType;
  }

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.password);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getNewPassword() {
    return newPassword;
  }

  @Override
  public String getNewPasswordConfirmation() {
    return newPasswordConfirmation;
  }

  @Override
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  @Override
  public void setNewPasswordConfirmation(String newPasswordConfirmation) {
    this.newPasswordConfirmation = newPasswordConfirmation;
  }

  /**
   * UtilisateurType enum.
   */
  public enum UtilisateurType {

    /**
     * PROFESSOR.
     */
    PROFESSOR("professeur"),
    /**
     * STUDENT.
     */
    STUDENT("étudiant"),
    /**
     * ADMIN.
     */
    ADMIN("administratif");

    private String type;

    UtilisateurType(String type) {
      this.type = type;
    }

    /**
     * getType.
     *
     * @return type
     */
    public String getType() {
      return type;
    }
  }

  @Override
  public boolean isEmailValid(String email) {
    Pattern pattern = Pattern.compile("^[a-zA-Z]+\\.[a-zA-Z]+@(student\\.)?vinci\\.be$");
    return pattern.matcher(email).matches();
  }

  @Override
  public boolean isRoleValid(String role) {
    return Arrays.asList(POSSIBLE_UTILISATEUR_TYPE).contains(role.toLowerCase());
  }

}
