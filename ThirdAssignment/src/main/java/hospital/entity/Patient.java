package hospital.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="patients")

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private String pnc;
    private Date dob;
    private String address;
    private String idInfo;

    public Patient(){}

    public Patient(String name, String pnc, Date dob, String address, String idInfo) {
        this.name = name;
        this.pnc = pnc;
        this.dob = dob;
        this.address = address;
        this.idInfo=idInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnc() {
        return pnc;
    }

    public void setPnc(String pnc) {
        this.pnc = pnc;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getIdInfo() {
        return idInfo;
    }

    public void setIdInfo(String idInfo) {
        this.idInfo = idInfo;
    }
}
