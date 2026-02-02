package clases;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Games")
public class Games implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idGames")
	private int idGames;
	
	@Column(name="Nombre")
	private String nombre;
	
	@Column(name="tiempoJugado")
	private String tiempoJugado;

}