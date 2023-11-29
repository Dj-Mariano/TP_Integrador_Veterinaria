package service;
import entity.TurnoMedico;
import java.util.List;

public interface TurnoMedicoService {
    void crearTurnoMedico(TurnoMedico turnoMedico);
    TurnoMedico obtenerTurnoMedicoPorId(int id);
    List<TurnoMedico> obtenerTodosLosTurnoMedicos();
    List<TurnoMedico> obtenerTurnosLibres();
    void actualizarTurnoMedico(TurnoMedico turnoMedico);
    void eliminarTurnoMedico(int id);
}
