package hospital.service.conusltation;

import hospital.dto.DoctorConsultationDTO;
import hospital.dto.SecretaryConsultationDTO;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import hospital.repository.ConsultationRepository;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    ConsultationRepository consultationRepository;
    PatientService patientService;
    UserService userService;

    @Autowired
    public ConsultationServiceImpl(ConsultationRepository consultationRepository, PatientService patientService,UserService userService) {
        this.consultationRepository = consultationRepository;
        this.patientService=patientService;
        this.userService=userService;
    }

    @Override
    public List<Consultation> getAll() {
        return consultationRepository.findAll();
    }

    @Override
    public Consultation create(SecretaryConsultationDTO secretaryConsultationDTO) {
        Patient patient=patientService.findById(secretaryConsultationDTO.patientId);
        User doctor=userService.findByUsername(secretaryConsultationDTO.doctorUsername);

        Consultation consultation=new Consultation(secretaryConsultationDTO.date,doctor,patient);
        consultation.setDiagnostic("None yet!");
        consultation.setRecommendation("None yet!");

        return consultationRepository.save(consultation);
    }

    @Override
    public void delete(int consultationID) {
        consultationRepository.delete(consultationID);
    }

    @Override
    public Consultation update(SecretaryConsultationDTO secretaryConsultationDTO) {
        Consultation consultation=consultationRepository.findOne(secretaryConsultationDTO.id);

        Patient patient=patientService.findById(secretaryConsultationDTO.patientId);
        User doctor=userService.findByUsername(secretaryConsultationDTO.doctorUsername);

        consultation.setDate(secretaryConsultationDTO.date);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);


        return consultationRepository.save(consultation);

    }

    @Override
    public Consultation update(DoctorConsultationDTO doctorConsultationDTO) {
        Consultation consultation=consultationRepository.findOne(doctorConsultationDTO.id);

        consultation.setRecommendation(doctorConsultationDTO.recommendation);
        consultation.setDiagnostic(doctorConsultationDTO.diagnostic);

        return consultationRepository.save(consultation);
    }

    @Override
    public List<Consultation> getAllDoctorsPastConsultations(String username) {
        User doctor=userService.findByUsername(username);
        return consultationRepository.findByDateBeforeAndDoctor(new Date(),doctor);
    }

    @Override
    public List<Consultation> getAllDoctorsFutureConsultations(String username) {
        User doctor=userService.findByUsername(username);
        return consultationRepository.findByDateAfterAndDoctor(new Date(),doctor);
    }

    @Override
    public List<Consultation> getAllFutureConsultations() {
        return consultationRepository.findByDateAfter(new Date());
    }
}
