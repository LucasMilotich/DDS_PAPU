package Patrones;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Logica.Jugador;
import Logica.Partido;

public class Handicap extends GeneradorEquiposTentativos{
	public Handicap(){
		this.setNombreMetodo("Handicap");
	}
	
	
	public static Comparator<Jugador> handicapComparator = new Comparator<Jugador>() { 
		public int compare(Jugador jugador1, Jugador jugador2) {
			return Integer.compare(jugador1.getNivelDeJuego(), jugador2.getNivelDeJuego()) ; 
		}
	};
	
	@Override
	public Map<Jugador, Double> generarListaTentativa(int n) {
		
		//ordena lista seleccionados segun nivel de juego
		Collections.sort(partido.getJugadoresSeleccionados(), handicapComparator);
		
		//genero el map para enviarlo en caso que se elija un mixCriterios
		Map<Jugador, Double> map = new LinkedHashMap<Jugador, Double>();
		Iterator<Jugador> iterator = partido.getJugadoresSeleccionados().iterator();
		Jugador jug = null;
		while(iterator.hasNext()){
			jug = iterator.next();
			map.put(jug, (double) jug.getNivelDeJuego());
		}
		
		return map;
	}

	@Override
	public void agregar(GeneradorEquiposTentativos c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(GeneradorEquiposTentativos c) {
		// TODO Auto-generated method stub
		
	}
	
	
}