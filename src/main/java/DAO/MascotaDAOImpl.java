package DAO;
import entity.Mascota;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

public class MascotaDAOImpl implements MascotaDAO {

    public MascotaDAOImpl() {
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public void crearMascota(Mascota mascota) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(mascota);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Mascota obtenerMascotaPorId(int id) {
        return entityManager.find(Mascota.class, id);

    }

    @Override
    public List<Mascota> buscarPorNombre(String nombre) {
        try {
            TypedQuery<Mascota> query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.nombre = :nombre", Mascota.class);
            query.setParameter("nombre", nombre);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public List<Mascota> obtenerTodasLasMascotas() {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m");
        return query.getResultList();
    }

    @Override
    public void actualizarMascota(Mascota mascota) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(mascota);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMascota(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Mascota mascota = entityManager.find(Mascota.class, id);
            if (mascota != null) {
                entityManager.remove(mascota);
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
