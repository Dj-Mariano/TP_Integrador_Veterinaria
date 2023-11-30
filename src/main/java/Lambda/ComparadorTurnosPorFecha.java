package Lambda;

import entity.TurnoMedico;

public class ComparadorTurnosPorFecha implements ComparadorTurnos{
    @Override
    public int compararTurnos(TurnoMedico turno1, TurnoMedico turno2) {
        return turno1.getFechaTurno().compareTo(turno2.getFechaTurno());
    }
}
