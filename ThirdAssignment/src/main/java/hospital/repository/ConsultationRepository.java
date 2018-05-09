package hospital.repository;

import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Integer> {
    List<Consultation> findByDoctor(User doctor);
    List<Consultation> findByPatient(Patient patient);

    List<Consultation> findByDateAfter(Date date);
    List<Consultation> findByDateBeforeAndDoctor(Date date,User doctor);
    List<Consultation> findByDateAfterAndDoctor(Date date,User doctor);
}
