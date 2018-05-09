package hospital;

import hospital.dto.CheckinDTO;
import hospital.dto.PatientDTO;
import hospital.dto.SecretaryConsultationDTO;
import hospital.entity.Consultation;
import hospital.entity.Patient;
import hospital.notifications.Message;
import hospital.service.conusltation.ConsultationService;
import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/secretary")
public class SecretaryController {

    @Autowired
    ConsultationService consultationService;

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;


    @RequestMapping(value = "/checkin", method = RequestMethod.POST)
    public void checkingIn(@RequestBody CheckinDTO checkinDTO) {
        Patient patient=patientService.findById(checkinDTO.patientId);

        Message message = new Message();
        message.setText("Doctor, "+ patient.getName() +" has arrived to see you!") ;
        messagingTemplate.convertAndSendToUser(checkinDTO.doctorUsername, "/queue/reply", message);

    }


    @RequestMapping(value = "/futureConsultations", method = RequestMethod.GET)
    public List<Consultation> getAllFutureConsultations() {
        return consultationService.getAllFutureConsultations();
    }

    @RequestMapping(value = "/createConsultation", method = RequestMethod.POST)
    public List<String> createConsultation(@RequestBody @Valid SecretaryConsultationDTO secretaryConsultationDTO, BindingResult bindingResult)
    {

        List<String> result =new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error->result.add(error.getDefaultMessage()));
        }
        else {

            if(consultationService.create(secretaryConsultationDTO)!=null) {
                result.add("Creating consultation successful!");

                Patient patient=patientService.findById(secretaryConsultationDTO.patientId);

                Message message = new Message();
                message.setText("New consultation! Patient " + patient.getName() +" at "+secretaryConsultationDTO.date) ;
                messagingTemplate.convertAndSendToUser(secretaryConsultationDTO.doctorUsername, "/queue/reply", message);
            }
            else
                result.add("Creating consultation not successful!");
        }
        return result;

    }
    @RequestMapping(value = "/deleteConsultation", method = RequestMethod.POST)
    public void deleteConsultation(@RequestBody Integer delID) {
        consultationService.delete(delID);
    }

    @RequestMapping(value = "/updateConsultation", method = RequestMethod.POST)
    public List<String> updateConsultation(@RequestBody @Valid SecretaryConsultationDTO secretaryConsultationDTO, BindingResult bindingResult) {
        List<String> result =new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error->result.add(error.getDefaultMessage()));
        }
        else {

            if (consultationService.update(secretaryConsultationDTO) != null) {
                result.add("Updating consultation successful!");
            }
            else
                result.add("Updating consultation not successful!");
        }
        return result;
    }



    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public List<Patient> getAllPatients() {
        return patientService.getAll();
    }

    @RequestMapping(value = "/createPatient", method = RequestMethod.POST)
    public List<String> createPatient(@RequestBody @Valid PatientDTO patientDTO, BindingResult bindingResult)
    {
        List<String> result =new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error->result.add(error.getDefaultMessage()));
        }
        else {

            if(patientService.create(patientDTO)!=null)
                result.add("Creating patient successful!");
            else
                result.add("Creating patient not successful!");
        }
        return result;

    }

    @RequestMapping(value = "/updatePatient", method = RequestMethod.POST)
    public List<String> update(@RequestBody @Valid PatientDTO patientDTO,BindingResult bindingResult) {
        List<String> result =new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error->result.add(error.getDefaultMessage()));
        }
        else {

            if(patientService.update(patientDTO)!=null)
                result.add("Updating patient successful!");
            else
                result.add("Updating patient not successful!");
        }
        return result;
    }


}
