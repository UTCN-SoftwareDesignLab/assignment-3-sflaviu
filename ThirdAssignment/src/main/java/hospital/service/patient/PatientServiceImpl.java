package hospital.service.patient;

import hospital.dto.PatientDTO;
import hospital.entity.Patient;
import hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(int patientId) {
        return patientRepository.findOne(patientId);
    }

    @Override
    public Patient create(PatientDTO patientDTO) {
        Patient patient=new Patient(patientDTO.name,patientDTO.pnc,patientDTO.dob,patientDTO.address,patientDTO.idInfo);
        return patientRepository.save(patient);
    }

    @Override
    public void delete(int patientId) {
        patientRepository.delete(patientId);
    }

    @Override
    public Patient update(PatientDTO patientDTO) {
        Patient patient=patientRepository.findByPnc(patientDTO.pnc);
        patient.setAddress(patientDTO.address);
        patient.setDob(patientDTO.dob);
        patient.setName(patientDTO.name);
        patient.setIdInfo(patientDTO.idInfo);
        return patientRepository.save(patient);
    }
}
