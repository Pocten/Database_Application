package dao;


import entities.Psychiatrist;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class PsychiatristDao {
    private final EntityManager em;
    private static final String UNIT_NAME = "pu";

    public PsychiatristDao(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        em = factory.createEntityManager();
    }

    public List<Psychiatrist> findAll() {
        em.clear();
        Query query = em.createQuery("select a from Psychiatrist a");
        return query.getResultList();
    }

    public Optional<Psychiatrist> findById(Long id){
        em.clear();
        return Optional.ofNullable(em.find(Psychiatrist.class, id));
    }
}
