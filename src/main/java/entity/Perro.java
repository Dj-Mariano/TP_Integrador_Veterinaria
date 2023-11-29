package entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Perro")
public class Perro extends Mascota {
    @Override
    public String toString() {
        return "Perro";
    }
}
