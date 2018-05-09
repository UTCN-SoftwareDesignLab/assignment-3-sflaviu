
import hospital.Application;
import hospital.dto.DoctorConsultationDTO;
import hospital.dto.PatientDTO;
import hospital.dto.SecretaryConsultationDTO;
import hospital.dto.UserDTO;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.entity.User;
import hospital.service.conusltation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableJpaRepositories(basePackages = "hospital.repository")
@ComponentScan({"hospital", "hospital.entity", "hospital.dto", "hospital.repository", "hospital.service", "hospital.notifications","hospital.config"})
@EntityScan(basePackages ={"hospital.entity"})
public class ConsultationServiceImplTest {

    @Autowired
    ConsultationService consultationService;
    @Autowired
    PatientService patientService;
    @Autowired
    UserService userService;

    Patient testPatient;
    User testUser;
    Consultation testConsultation;

    @Before
    public void setUp()
    {
        PatientDTO patientDTO=new PatientDTO();
        patientDTO.name="Bolnavul Testat";
        patientDTO.pnc="2244668800";
        patientDTO.address="Adresa Bolnava";
        patientDTO.dob=new Date();
        patientDTO.idInfo="Serie bolnava";
        testPatient=patientService.create(patientDTO);

        UserDTO userDTO=new UserDTO();
        userDTO.username="Doctorul testat";
        userDTO.password="Parola testata";
        userDTO.role="Doctor";
        testUser=userService.create(userDTO);
    }

    @Test
    public void checkConsultations()
    {
        SecretaryConsultationDTO secretaryConsultationDTO=new SecretaryConsultationDTO();
        secretaryConsultationDTO.doctorUsername=testUser.getUsername();
        secretaryConsultationDTO.patientId=testPatient.getId();
        secretaryConsultationDTO.date=new Date();

        int currentNrOfConsultations=consultationService.getAll().size();
        testConsultation=consultationService.create(secretaryConsultationDTO);
        Assert.assertTrue(currentNrOfConsultations==consultationService.getAll().size()-1);

        DoctorConsultationDTO doctorConsultationDTO=new DoctorConsultationDTO();
        doctorConsultationDTO.diagnostic="Diagnostic testat";
        doctorConsultationDTO.recommendation="Recomandare testata";
        doctorConsultationDTO.id=testConsultation.getId();

        testConsultation=consultationService.update(doctorConsultationDTO);

        Assert.assertFalse(testConsultation.getDiagnostic().equals("Not yet!"));
    }

    @After
    public void clean()
    {
        consultationService.delete(testConsultation.getId());
        userService.delete(testUser.getUsername());
        patientService.delete(testPatient.getId());
    }

}
