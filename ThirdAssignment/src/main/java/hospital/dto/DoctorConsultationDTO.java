package hospital.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class DoctorConsultationDTO {

    @NotNull(message="Must enter an id!")
    public int id;

    @Size(min = 1, message ="A diagnostic must be given!")
    public String diagnostic;

    @Size(min = 1, message ="A recommendation must be given!")
    public String recommendation;

}
