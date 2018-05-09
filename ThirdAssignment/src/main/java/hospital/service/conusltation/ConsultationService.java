package hospital.service.conusltation;

import hospital.dto.DoctorConsultationDTO;
import hospital.dto.SecretaryConsultationDTO;
import hospital.entity.Consultation;

import java.util.List;

public interface ConsultationService {

    List<Consultation> getAll();
    Consultation create(SecretaryConsultationDTO secretaryConsultationDTO);

    void delete(int consultationID);
    Consultation update(SecretaryConsultationDTO secretaryConsultationDTO);
    Consultation update(DoctorConsultationDTO doctorConsultationDTO);

    List<Consultation> getAllDoctorsPastConsultations(String username);
    List<Consultation> getAllDoctorsFutureConsultations(String username);

    List<Consultation> getAllFutureConsultations();

}
