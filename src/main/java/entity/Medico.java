package entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String contraseña;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL)
    private List<TurnoMedico> turnos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TurnoMedico> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<TurnoMedico> turnos) {
        this.turnos = turnos;
    }
    public void addTurno(TurnoMedico turno){
        turnos.add(turno);
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
