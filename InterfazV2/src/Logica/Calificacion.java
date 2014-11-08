package Logica;

import java.io.Serializable;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import BD.BDCalificacion;


public class Calificacion implements Serializable{
	
	int id;
	Jugador jugadorCalificado;
	Jugador jugadorCalificador;
	int puntaje;
	Partido partido;
	public Jugador getJugadorCalificado() {
		return jugadorCalificado;
	}
	public void setJugadorCalificado(Jugador jugadorCalificado) {
		this.jugadorCalificado = jugadorCalificado;
	}
	public Jugador getJugadorCalificador() {
		return jugadorCalificador;
	}
	public void setJugadorCalificador(Jugador jugadorCalificador) {
		this.jugadorCalificador = jugadorCalificador;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public void calificarA(Jugador calificador, Jugador calificado, Integer puntaje,Partido partido){
		this.setJugadorCalificado(calificado);
		this.setJugadorCalificador(calificador);
		this.setPuntaje(puntaje);
		this.setPartido(partido);
		//Aregar db
		
		BDCalificacion bdCalificacion = new BDCalificacion();
		try {
			bdCalificacion.getConnection();
			bdCalificacion.crearCalificacion(partido, calificado, calificador, puntaje);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public Partido getPartido() {
		return partido;
	}
	public void setPartido(Partido partido) {
		this.partido = partido;
	}
}
