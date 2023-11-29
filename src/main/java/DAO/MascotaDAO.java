package DAO;
import entity.Mascota;
import java.util.List;

public interface MascotaDAO {
    void crearMascota(Mascota mascota);
    Mascota obtenerMascotaPorId(int id);
    List<Mascota> buscarPorNombre(String nombre);
    List<Mascota> obtenerTodasLasMascotas();
    void actualizarMascota(Mascota mascota);
    void eliminarMascota(int id);
}

