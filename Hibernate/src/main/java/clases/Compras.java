package clases;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Compras")
public class Compras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompra")
    private int id;

    // Relación ManyToOne con Player
    @ManyToOne
    @JoinColumn(name = "idPlayer", nullable = false)
    private Player player;

    // Relación ManyToOne con Games
    @ManyToOne
    @JoinColumn(name = "idGames", nullable = false)
    private Games game;

    @Column(name = "Cosa", length = 25)
    private String cosa;

    @Column(name = "Precio", precision = 6, scale = 2)
    private BigDecimal precio;

    @Column(name = "FechaCompra")
    private Date fechaCompra;

    public Compras() {}

    public Compras(Player player, Games game, String cosa, BigDecimal precio, Date fechaCompra) {
        this.player = player;
        this.game = game;
        this.cosa = cosa;
        this.precio = precio;
        this.fechaCompra = fechaCompra;
    }

    // ... Getters y Setters ...
    
    @Override
    public String toString() {
        return "Compra ID: " + id + " | Jugador: " + player.getId() + " | Juego: " + game.getId() + " | Item: " + cosa;
    }
}
