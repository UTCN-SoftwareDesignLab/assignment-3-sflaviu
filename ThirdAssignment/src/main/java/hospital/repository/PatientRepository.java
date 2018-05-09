package hospital.repository;

import hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    public Patient findByPnc(String pnc);
}
