package be.vinci.pae.business.impl;

import java.util.Arrays;

/**
 * ContactImpl class.
 */
public class ContactImpl implements Contact {
  private String declineReason;
  private String contactStatus;
  private String meetingType;
  private int studentId;
  private int companyId;
  private int version;

  private String studentName;
  private String studentFirstName;
  private String studentEmail;

  private String companyTradeName;
  private String companyDesignation;
  private String companyTradeNameDesignation;
  private String companyPhone;
  private String academicYear;

  /**
   * pojo Contact.
   */
  public ContactImpl() {

  }

  @Override
  public String getContactStatus() {
    return contactStatus;
  }

  @Override
  public void setContactStatus(String contactStatus) {
    this.contactStatus = Arrays.stream(State.values())
        .filter(e -> e.getState().equals(contactStatus))
        .findFirst()
        .map(State::getState)
        .orElse(null);
  }

  @Override
  public String getMeetingType() {
    return meetingType;
  }

  @Override
  public void setMeetingType(String meetingType) {
    this.meetingType = Arrays.stream(MeetingType.values())
        .filter(e -> e.getMeetingType().equals(meetingType))
        .findFirst()
        .map(MeetingType::getMeetingType)
        .orElse(null);
  }

  @Override
  public String getDeclineReason() {
    return declineReason;
  }

  @Override
  public void setDeclineReason(String declineReason) {
    this.declineReason = declineReason;
  }

  @Override
  public int getStudentId() {
    return studentId;
  }

  @Override
  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  @Override
  public int getCompanyId() {
    return companyId;
  }

  @Override
  public void setCompanyId(int companyId) {
    this.companyId = companyId;
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
  public String getStudentName() {
    return studentName;
  }

  @Override
  public void setStudentName(String studentName) {
    this.studentName = studentName;
  }

  @Override
  public String getStudentFirstName() {
    return studentFirstName;
  }

  @Override
  public void setStudentFirstName(String studentFirstName) {
    this.studentFirstName = studentFirstName;
  }

  @Override
  public String getStudentEmail() {
    return studentEmail;
  }

  @Override
  public void setStudentEmail(String studentEmail) {
    this.studentEmail = studentEmail;
  }

  @Override
  public String getCompanyTradeName() {
    return companyTradeName;
  }

  @Override
  public void setCompanyTradeName(String companyTradeName) {
    this.companyTradeName = companyTradeName;
  }

  @Override
  public String getCompanyDesignation() {
    return companyDesignation;
  }

  @Override
  public void setCompanyDesignation(String getCompanyDesignation) {
    this.companyDesignation = getCompanyDesignation;
  }

  @Override
  public String getCompanyTradeNameDesignation() {
    return companyTradeNameDesignation;
  }

  @Override
  public void setCompanyTradeNameDesignation(String companyTradeNameDesignation) {
    this.companyTradeNameDesignation = companyTradeNameDesignation;
  }

  @Override
  public String getCompanyPhone() {
    return companyPhone;
  }

  @Override
  public void setCompanyPhone(String companyPhone) {
    this.companyPhone = companyPhone;
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
  public  boolean checkState(String before, String after) {
    if (before == null || after == null) {
      throw new IllegalArgumentException("The state is null");
    }

    if (before.equals(State.INITIE.getState())) {
      return after.equals(State.PRIS.getState()) || after.equals(State.PLUS_SUIVI.getState());
    } else if (before.equals(State.PRIS.getState())) {
      return after.equals(State.PLUS_SUIVI.getState())
          || after.equals(State.ACCEPTE.getState())
          || after.equals(State.REFUSE.getState());
    } else if (before.equals(State.PLUS_SUIVI.getState())) {
      return false;
    } else if (before.equals(State.ACCEPTE.getState())) {
      return false;
    } else if (before.equals(State.REFUSE.getState())) {
      return false;
    } else if (before.equals(State.SUSPENDU.getState())) {
      return false;
    }
    return false;
  }

  @Override
  public boolean checkMeetingType(String meetingType) {
    return Arrays.stream(MeetingType.values())
        .anyMatch(e -> e.getMeetingType().equals(meetingType));
  }

  /**
   * State enum.
   */
  public enum State {
    /**
     * State INITIE.
     */
    INITIE("initie"),

    /**
     * State PRIS.
     */
    PRIS("pris"),

    /**
     * State ACCEPTE.
     */
    ACCEPTE("accepte"),

    /**
     * State REFUSE.
     */
    REFUSE("refuse"),

    /**
     * State SUSPENDU.
     */
    SUSPENDU("suspendu"),

    /**
     * State PLUS_SUIVI.
     */
    PLUS_SUIVI("plus suivi");

    private String state;

    /**
     * State constructor.
     *
     * @param state state value
     */
    State(String state) {
      this.state = state;
    }

    /**
     * getState.
     *
     * @return state
     */
    public String getState() {
      return state;
    }
  }

  /**
   * MeetingType enum.
   */
  public enum MeetingType {
    /**
     * MeetingType DISTANCE.
     */
    DISTANCE("distance"),

    /**
     * MeetingType EN_ENTREPRISE.
     */
    EN_ENTREPRISE("en entreprise");

    private String meetingType;

    /**
     * MeetingType constructor.
     *
     * @param meetingType meetingType value
     */
    MeetingType(String meetingType) {
      this.meetingType = meetingType;
    }

    /**
     * getMeetingType.
     *
     * @return meetingType
     */
    public String getMeetingType() {
      return meetingType;
    }
  }
}
