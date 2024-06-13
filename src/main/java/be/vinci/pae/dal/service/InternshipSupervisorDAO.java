package be.vinci.pae.dal.service;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import java.util.List;

/**
 * InternshipSupervisorDAO interface.
 */
public interface InternshipSupervisorDAO {

  /**
   * Create an internship supervisor.
   *
   * @param internshipSupervisor to create
   * @return true if the internship supervisor is created
   */
  InternshipSupervisorDTO create(InternshipSupervisorDTO internshipSupervisor);

  /**
   * Get an internship supervisor by name and entreprise id.
   *
   * @param lastName of the internship supervisor
   * @param firstName of the internship supervisor
   * @param id of the company
   * @return the internship supervisor
   */
  InternshipSupervisorDTO getInternshipSupervisorByName(String lastName, String firstName, int id);

  /**
   * Get all internship supervisor by id.
   *
   * @param companyId of the internship supervisor
   * @return the list of internship supervisor
   */
  List<InternshipSupervisorDTO> getAllInternshipSupervisorByCompanyId(int companyId);

  /**
   * Get the internship supervisor.
   *
   * @param internshipSupervisor to get
   * @return the internshipSupervisorDTO
   */
  InternshipSupervisorDTO getInternshipSupervisor(
      InternshipSupervisorDTO internshipSupervisor);

  /**
   * Get the internship supervisor by email.
   *
   * @param email of the internship supervisor
   * @return the internshipSupervisorDTO
   */
  InternshipSupervisorDTO getInternshipSupByEmail(String email);

  /**
   * Get the internship supervisor by phone.
   *
   * @param phone of the internship supervisor
   * @return the internshipSupervisorDTO
   */
  InternshipSupervisorDTO getInternshipPhone(String phone);
}
