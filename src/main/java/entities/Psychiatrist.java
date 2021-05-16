package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "psychiatrist")
public class Psychiatrist {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false)
        private String psychiatristFirstName;

        @Column(name = "last_name", nullable = false)
        private String psychiatristLastName;

        public Psychiatrist(String psychiatristFirstName, String psychiatristLastName) {
            this.psychiatristFirstName = psychiatristFirstName;
            this.psychiatristLastName = psychiatristLastName;
        }
    }



