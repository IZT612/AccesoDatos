package clases;

import java.sql.Time;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Games")
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGames")
    private int id;

    @Column(name = "Nombre", length = 45)
    private String nombre;

    @Column(name = "tiempoJugado")
    private Time tiempoJugado; // O LocalTime en versiones nuevas

    @OneToMany(mappedBy = "game")
    private List<Compras> compras;

    public Games() {}

    public Games(String nombre, Time tiempoJugado) {
        this.nombre = nombre;
        this.tiempoJugado = tiempoJugado;
    }

    // ... Getters y Setters ...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    // ...
    
    @Override
    public String toString() {
        return "ID: " + id + " | Juego: " + nombre + " | Tiempo: " + tiempoJugado;
    }
}