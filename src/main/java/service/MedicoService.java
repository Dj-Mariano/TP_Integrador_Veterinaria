package service;

import DAO.MedicoDAO;
import entity.Medico;

import java.util.List;

public interface MedicoService {
    void crearMedico(Medico medico);
    Medico obtenerMedicoPorId(int id);
    Medico obtenerMedicoPorNombre(String nombre);
    List<Medico> obtenerTodosLosMedicos();
    void actualizarMedico(Medico medico);
    void eliminarMedico(int id);
}
