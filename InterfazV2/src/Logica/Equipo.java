package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipo implements Serializable{
	
	boolean confirmado;
	String nombre;
	List<Jugador> listaDeJugadores = new ArrayList<Jugador>();
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Jugador> getListaDeJugadores() {
		return listaDeJugadores;
	}
	
}