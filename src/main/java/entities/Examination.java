package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "examination")
public class Examination {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "pacient_id", nullable = false)
        private Pacient pacient;

        @OneToOne
        @JoinColumn(name = "psychiatrist_id", nullable = false)
        private Psychiatrist psychiatrist;

        @Column(name = "date", nullable = false)
        private String date;

        @Column(name = "time", nullable = false)
        private String time;

        @Column(name = "department", nullable = false)
        private String department;

        @Column(name = "room", nullable = false)
        private String room;

        public Examination(Pacient pacient, Psychiatrist psychiatrist, String date, String time, String department, String room) {
            this.pacient = pacient;
            this.psychiatrist = psychiatrist;
            this.date = date;
            this.time = time;
            this.department = department;
            this.room = room;
        }
    }


