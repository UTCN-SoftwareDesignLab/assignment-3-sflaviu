package hospital.dto;

import javax.validation.constraints.NotNull;

public class CheckinDTO {
    @NotNull
    public String doctorUsername;

    @NotNull
    public int patientId;
}
