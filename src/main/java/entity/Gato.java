package entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
@Entity
@DiscriminatorValue("Gato")
public class Gato extends Mascota {
    @Override
    public String toString() {
        return "Gato";
    }
}
