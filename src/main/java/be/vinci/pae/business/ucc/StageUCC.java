package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.StageDTO;

/**
 * This is the interface of StageUCCImpl.
 */
public interface StageUCC {

  /**
   * Get a stage by its id.
   *
   * @param stageId the id of the stage
   * @return the stageDTO
   */
  StageDTO getStageById(int stageId);

  /**
   * Create a stage.
   *
   * @param stage the stage to create
   * @return the stageDTO
   */
  StageDTO createStage(StageDTO stage);

  /**
   * Retrieve data of accepted stages for a given student.
   *
   * @param studentId id of the student
   * @return the stageDTO
   */
  StageDTO getAcceptedStage(int studentId);

  /**
   * Update the internship project of a student.
   *
   * @param stage with the new internship project and the student id
   * @return boolean
   */
  boolean updateInternshipProject(StageDTO stage);

}
