package service;
import DAO.MascotaDAO;
import entity.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MascotaServiceImpl implements MascotaService {

    private final MascotaDAO mascotaDAO;

    public MascotaServiceImpl(MascotaDAO mascotaDAO) {
        this.mascotaDAO = mascotaDAO;
    }

    @Override
    public void crearMascota(Mascota mascota) {
        mascotaDAO.crearMascota(mascota);
    }

    @Override
    public Mascota obtenerMascotaPorId(int id) {
        return mascotaDAO.obtenerMascotaPorId(id);
    }

    @Override
    public List<Mascota> buscarPorNombre(String nombre){
        return mascotaDAO.buscarPorNombre(nombre);
    }

    @Override
    public List<Mascota> obtenerTodasLasMascotas() {
        return mascotaDAO.obtenerTodasLasMascotas();
    }

    @Override
    public void actualizarMascota(Mascota mascota) {
        mascotaDAO.actualizarMascota(mascota);
    }

    @Override
    public void eliminarMascota(int id) {
        mascotaDAO.eliminarMascota(id);
    }
}

