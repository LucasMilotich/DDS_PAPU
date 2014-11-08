package Logica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


public class Amigo extends Jugador implements Serializable {
	
	Jugador jugador;
	Jugador amigo;
	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Jugador getAmigo() {
		return amigo;
	}

	public void setAmigo(Jugador amigo) {
		this.amigo = amigo;
	}

	
	
}
