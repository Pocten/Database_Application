package dto;

import entities.Examination;
import lombok.Data;

@Data
public class ExaminationDto {
    private long id;
    private long pacient_id;
    private long psychiatrist_id;
    private String date;
    private String time;
    private String department;
    private String room;

    public ExaminationDto (Examination examination) {
        this.id = examination.getId();
        this.pacient_id = examination.getPacient().getId();
        this.psychiatrist_id = examination.getPsychiatrist().getId();
        this.date = examination.getDate();
        this.time = examination.getTime();
        this.department = examination.getDepartment();
        this.room = examination.getRoom();
    }
}
