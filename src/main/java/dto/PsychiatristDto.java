package dto;

import entities.Psychiatrist;
import lombok.Data;

@Data
public class PsychiatristDto {
        private long id;
        private String psychiatristFirstName;
        private String psychiatristLastName;

        public PsychiatristDto (Psychiatrist psychiatrist){
            this.id = psychiatrist.getId();
            this.psychiatristFirstName = psychiatrist.getPsychiatristFirstName();
            this.psychiatristLastName = psychiatrist.getPsychiatristLastName();
        }
}
