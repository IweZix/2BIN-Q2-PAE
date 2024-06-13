package be.vinci.pae.business.impl;

import be.vinci.pae.business.dto.ContactDTO;

/**
 * Contact class.
 */
public interface Contact extends ContactDTO {

  /**
   * Check the state.
   *
   * @param before state before update
   * @param after state after update
   * @return true if the state is correct
   */
  boolean checkState(String before, String after);

  /**
   * Check the meeting type.
   *
   * @param meetingType the meeting type
   * @return true if the meeting type is correct
   */
  boolean checkMeetingType(String meetingType);

}
