package DAO;
import entity.Mascota;
import entity.Medico;
import jakarta.persistence.*;

import java.util.List;

public class MedicoDAOImpl implements MedicoDAO{

    public MedicoDAOImpl() {
    }
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void crearMedico(Medico medico) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(medico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Medico obtenerMedicoPorId(int id) {
        return entityManager.find(Medico.class, id);
    }

    @Override
    public Medico obtenerMedicoPorNombre(String nombre){
        try {
            TypedQuery<Medico> query = entityManager.createQuery("SELECT m FROM Medico m WHERE m.nombre = :nombre", Medico.class);
            query.setParameter("nombre", nombre);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Medico> obtenerTodosLosMedicos() {
        Query query = entityManager.createQuery("SELECT m FROM Medico m");
        return query.getResultList();
    }

    @Override
    public void actualizarMedico(Medico medico) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(medico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMedico(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Medico medico = entityManager.find(Medico.class, id);
            if (medico != null) {
                entityManager.remove(medico);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
