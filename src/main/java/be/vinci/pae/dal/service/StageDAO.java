package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.StageDTO;

/**
 * This is the interface of StageDAOImpl.
 */
public interface StageDAO {

  /**
   * Get a stage by its id.
   *
   * @param stageId the id of the stage
   * @return the stageDTO
   */
  StageDTO getStageById(int stageId);

  /**
   * create Stage in the database.
   *
   * @param stage the stage to create
   * @return StageDTO if the stage is created
   */
  StageDTO createStage(StageDTO stage);

  /**
   * Retrieve all stage data from the database.
   *
   * @param studentId the id of the student
   * @return a StageDTO
   */
  StageDTO getAcceptedStage(int studentId);

  /**
   * Update the internship project of a student.
   *
   * @param stage with the new internship project and the student id
   * @return true if the internship project is updated or false if not
   */
  boolean updateInternshipProject(StageDTO stage);
}
