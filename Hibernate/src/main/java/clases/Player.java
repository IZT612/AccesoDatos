package clases;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    @Column(name = "idPlayer")
    private int id;

    @Column(name = "Nick", length = 45)
    private String nick;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "email", length = 100)
    private String email;

    // Relación inversa (opcional, pero útil): Un jugador tiene muchas compras
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Compras> compras;

    // Constructores, Getters y Setters vacíos y con campos obligatorios
    public Player() {}
    
    public Player(String nick, String password, String email) {
        this.nick = nick;
        this.password = password;
        this.email = email;
    }

    // ... Genera los Getters y Setters aquí ...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    // ... resto ...
    
    @Override
    public String toString() {
        return "ID: " + id + " | Nick: " + nick + " | Email: " + email;
    }
}
