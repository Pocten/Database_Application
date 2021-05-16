package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pacient")
public class Pacient {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false)
        private String pacientFirstName;

        @Column(name = "last_name", nullable = false)
        private String pacientLastName;

        @Column(name = "rodne_cislo", nullable = false, unique = true)
        private String rodneCislo;

        @Column(name = "age", nullable = false)
        private String age;

        @Column(name = "city", nullable = false)
        private String city;

        public Pacient(String pacientFirstName, String pacientLastName, String rodneCislo, String age, String city) {
            this.pacientFirstName = pacientFirstName;
            this.pacientLastName = pacientLastName;
            this.rodneCislo = rodneCislo;
            this.age = age;
            this.city = city;
        }
    }


