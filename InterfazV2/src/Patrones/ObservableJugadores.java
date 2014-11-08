package Patrones;

import java.util.ArrayList;

import Logica.Jugador;

public abstract class ObservableJugadores {
	private ArrayList<ObservadorAdmin> _jugadoresObservables;
public void jugadoresObservables(){
	_jugadoresObservables= new ArrayList<ObservadorAdmin>();
}

public void agregarAdmin(ObservadorAdmin o){
	_jugadoresObservables.add(o);
}

public void eliminarAdmin(ObservadorAdmin o){
	_jugadoresObservables.remove(o);
}
public void notificarAdmin(Jugador jugador){

	for(ObservadorAdmin o:_jugadoresObservables){
		o.avisarNuevaPropuesta(jugador);
	}
}

}
