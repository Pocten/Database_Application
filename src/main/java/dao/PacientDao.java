package dao;

import entities.Pacient;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class PacientDao {

    private static final String UNIT_NAME = "pu";
    private final EntityManager em;

    public PacientDao(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        em = factory.createEntityManager();
    }


    public Optional<Pacient> findById(Long id){
        em.clear();
        return Optional.ofNullable(em.find(Pacient.class, id));
    }

    public List<Pacient> findAll() {
        em.clear();
        Query query = em.createQuery("select s from Pacient s");
        return query.getResultList();
    }

    public Pacient create(String pacientFirstName, String pacientLastName, String rodneCislo, String age, String city) {
        em.getTransaction().begin();
        Pacient entity = new Pacient(pacientFirstName, pacientLastName, rodneCislo, age, city);
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public Pacient update(Pacient pacient, String pacientFirstName, String pacientLastName, String rodneCislo, String age, String city) {
        em.getTransaction().begin();
        pacient.setPacientFirstName(pacientFirstName);
        pacient.setPacientLastName(pacientLastName);
        pacient.setRodneCislo(rodneCislo);
        pacient.setAge(age);
        pacient.setCity(city);
        em.persist(pacient);
        em.getTransaction().commit();
        return pacient;
    }

    public void delete(Pacient pacient) {
        em.getTransaction().begin();
        em.remove(pacient);
        em.getTransaction().commit();
    }
}
