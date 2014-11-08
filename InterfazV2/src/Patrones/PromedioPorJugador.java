package Patrones;

import Logica.Jugador;

public class PromedioPorJugador {

	Jugador jugador;
	double promedio;
	
	public void setPromedio(double promedio) {
		this.promedio = promedio;
		
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
		
	}

	public double getPromedio() {
		return promedio;
	}

	public Jugador getJugador() {
		return jugador;
	}
	
}
