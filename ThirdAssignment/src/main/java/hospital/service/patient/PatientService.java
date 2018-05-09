package hospital.service.patient;

import hospital.dto.PatientDTO;
import hospital.entity.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAll();

    Patient findById(int patientId);

    Patient create(PatientDTO patientDTO);
    void delete(int patientId);
    Patient update(PatientDTO patientDTO);
}
