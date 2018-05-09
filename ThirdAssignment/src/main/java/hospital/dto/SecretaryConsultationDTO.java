package hospital.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SecretaryConsultationDTO {
    @NotNull(message="Must enter an id!")
    public int id;

    @Future(message ="Can only make appointments in the future!")
    @NotNull(message = "Must enter a date!")
    public Date date;

    @NotNull
    public String doctorUsername;

    @NotNull
    public int patientId;
}
