package be.vinci.pae.business.ucc;

import be.vinci.pae.business.dto.InternshipSupervisorDTO;
import java.util.List;

/**
 * InternshipSupervisorUCC interface.
 */
public interface InternshipSupervisorUCC {

  /**
   * Create an internship supervisor.
   *
   * @param internshipSupervisor to create
   * @return the created internship supervisor
   */
  InternshipSupervisorDTO create(InternshipSupervisorDTO internshipSupervisor);

  /**
   * Get all internship supervisors by company id.
   *
   * @param companyId of the internship supervisor
   * @return the list of internship supervisors
   */
  List<InternshipSupervisorDTO> getAllInternshipSupervisors(int companyId);

  /**
   * Get an internship supervisor.
   *
   * @param internshipSupervisor to get
   * @return the internship supervisor
   */
  InternshipSupervisorDTO getInternshipSupervisor(
      InternshipSupervisorDTO internshipSupervisor);
}
