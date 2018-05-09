package hospital.dto;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class PatientDTO {

    @Size(min=1,message="Name is too short!")
    public String name;

    @Pattern(regexp="^\\d+$",message="The PNC must contain only numbers")
    @Size(min=10,max=10,message="The PNC must be composed of 10 numbers!")
    public String pnc;

    @Past(message = "No time travellers allowed!")
    public Date dob;

    @Size(min=1,message="Address is too short!")
    public String address;

    @Size(min=5,message="Id info is too short!")
    public String idInfo;
}
