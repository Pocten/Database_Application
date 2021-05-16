package dto;

import entities.Pacient;
import lombok.Data;

@Data
public class PacientDto {
    private long id;
    private String pacientFirstName;
    private String pacientLastName;
    private String rodneCislo;
    private String age;
    private String city;

    public PacientDto(Pacient pacient) {
        this.id = pacient.getId();
        this.pacientFirstName = pacient.getPacientFirstName();
        this.pacientLastName = pacient.getPacientLastName();
        this.rodneCislo = pacient.getRodneCislo();
        this.age = pacient.getAge();
        this.city = pacient.getCity();
    }
}
