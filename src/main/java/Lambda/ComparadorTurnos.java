package Lambda;

import entity.TurnoMedico;

@FunctionalInterface
public interface ComparadorTurnos {
    int compararTurnos(TurnoMedico turno1, TurnoMedico turno2);
}
