package hospital;

import hospital.dto.DoctorConsultationDTO;
import hospital.dto.SecretaryConsultationDTO;
import hospital.entity.Consultation;
import hospital.service.conusltation.ConsultationService;
import hospital.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/doctor")
public class DoctorController {
    @Autowired
    ConsultationService consultationService;

    @RequestMapping(value = "/pastConsultations", method = RequestMethod.GET)
    public List<Consultation> getAllPastConsultations() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return consultationService.getAllDoctorsPastConsultations(username);
    }

    @RequestMapping(value = "/futureConsultations", method = RequestMethod.GET)
    public List<Consultation> getAllFutureConsultations() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return consultationService.getAllDoctorsFutureConsultations(username);
    }

    @RequestMapping(value = "/updateConsultation", method = RequestMethod.POST)
    public List<String> updateConsultation(@RequestBody @Valid DoctorConsultationDTO doctorConsultationDTO, BindingResult bindingResult) {
        List<String> result =new ArrayList<>();
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error->result.add(error.getDefaultMessage()));
        }
        else {

            if(consultationService.update(doctorConsultationDTO)!=null)
                result.add("Updating diagnostics successful!");
            else
                result.add("Updating diagnostics not successful!");
        }
        return result;
    }
}
