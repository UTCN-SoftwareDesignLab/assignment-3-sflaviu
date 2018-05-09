package hospital;

import hospital.service.patient.PatientService;
import hospital.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HospitalController {

    @Autowired
    PatientService patientService;

    @Autowired
    UserService userService;

    @RequestMapping("/admin")
    String admin()
    {
        return "crudUsers";
    }

    @RequestMapping("/secretary")
    String secretary() {
        return "secretary";
    }

    @RequestMapping("/doctor")
    String doctor()
    {
        return "updateDiagnostics";
    }

    @RequestMapping("/crudConsultations")
    String crudConsultations(Model model)
    {
        model.addAttribute("patients", patientService.getAll());
        model.addAttribute("doctors",userService.findDoctors());
        return "crudConsultations";
    }

    @RequestMapping("/updatePatients")
    String updatePatients()
    {
        return "updatePatients";
    }
}
