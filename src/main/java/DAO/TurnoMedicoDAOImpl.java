package DAO;
import entity.TurnoMedico;
import jakarta.persistence.*;

import java.util.List;

public class TurnoMedicoDAOImpl implements TurnoMedicoDAO{

    public TurnoMedicoDAOImpl() {
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    @Override
    public void crearTurnoMedico(TurnoMedico turnoMedico) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(turnoMedico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public TurnoMedico obtenerTurnoMedicoPorId(int id) {
        return entityManager.find(TurnoMedico.class, id);
    }

    @Override
    public List<TurnoMedico> obtenerTodosLosTurnoMedicos() {
        Query query = entityManager.createQuery("SELECT m FROM TurnoMedico m");
        return query.getResultList();
    }

    @Override
    public List<TurnoMedico> obtenerTurnosLibres(){
        TypedQuery<TurnoMedico> query = entityManager.createQuery("SELECT t FROM TurnoMedico t WHERE t.medico IS NULL", TurnoMedico.class);
        return query.getResultList();
    }

    @Override
    public void actualizarTurnoMedico(TurnoMedico turnoMedico) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(turnoMedico);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarTurnoMedico(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TurnoMedico turnoMedico = entityManager.find(TurnoMedico.class, id);
            if (turnoMedico != null) {
                entityManager.remove(turnoMedico);
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
