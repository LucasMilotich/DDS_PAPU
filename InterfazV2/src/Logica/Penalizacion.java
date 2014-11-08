package Logica;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Penalizacion implements Serializable {

	int id;

	String motivo;
	
	public String getMotivo() {
		return motivo;
	}

	public Jugador getJugador() {
		return jugador;
	}

	Jugador jugador;
	Partido partido;

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public void setMotivo(String motivo) {
		// TODO Auto-generated method stub
		this.motivo = motivo;
	}

	public void setJugador(Jugador jugador) {
		// TODO Auto-generated method stub
		this.jugador = jugador;
	}

}
