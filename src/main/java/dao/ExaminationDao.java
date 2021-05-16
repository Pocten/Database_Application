package dao;

import entities.Examination;
import entities.Pacient;
import entities.Psychiatrist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class ExaminationDao {
    private static final String UNIT_NAME = "pu";
    private final EntityManager em;

    public ExaminationDao(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        em = factory.createEntityManager();
    }


    public Optional<Examination> findById(Long id){
        em.clear();
        return Optional.ofNullable(em.find(Examination.class, id));
    }

    public List<Examination> findAll() {
        em.clear();
        Query query = em.createQuery("select s from Examination s");
        return query.getResultList();
    }

    public List<Examination> findByPacient(Pacient pacient) {
        Query q = em.createQuery("select t from Examination t where t.pacient = :pacient");
        q.setParameter("pacient", pacient);
        return q.getResultList();
    }

    public List<Examination> findByPsychiatrist(Psychiatrist psychiatrist) {
        Query q = em.createQuery("select t from Examination t where t.psychiatrist = :psychiatrist");
        q.setParameter("psychiatrist", psychiatrist);
        return q.getResultList();
    }

    public Examination create(Pacient pacient, Psychiatrist psychiatrist, String date, String time, String department, String room) {
        em.getTransaction().begin();
        Examination entity = new Examination(pacient, psychiatrist, date, time, department, room);
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    public void delete(Examination examination) {
        em.getTransaction().begin();
        em.remove(examination);
        em.getTransaction().commit();
    }
}
