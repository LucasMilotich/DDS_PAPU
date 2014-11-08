package Patrones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Logica.Jugador;
import Logica.Partido;

public class Mix extends GeneradorEquiposTentativos {
	public Mix()
	{
		this.setNombreMetodo("Mix");
	}
	
	//List<GeneradorEquiposTentativos> listaCriterios = new ArrayList<GeneradorEquiposTentativos>();
	List<GeneradorEquiposTentativos> listaCriterios = new ArrayList<GeneradorEquiposTentativos>();
	
	@Override
	public Map<Jugador, Double> generarListaTentativa(int n) {
		
		GeneradorEquiposTentativos Handi = new Handicap();
		GeneradorEquiposTentativos PUP = new promedioUtilmoPartido();
		GeneradorEquiposTentativos PNP = new PromedioNPartidos();
		Handi.setPartido(partido);
		PUP.setPartido(partido);
		PNP.setPartido(partido);
		
		listaCriterios.add(Handi);
		listaCriterios.add(PUP);
		listaCriterios.add(PNP);
		
		//relleno map con los jugadoresSeleccionados, seteo en 0 su "nota"
		Map<Jugador, Double> map = new LinkedHashMap<Jugador, Double>();
		Iterator<Jugador> iterator = partido.getJugadoresSeleccionados().iterator();
		Jugador jug = null;
		while(iterator.hasNext()){
			jug = iterator.next();
			map.put(jug, 0.0);
		}
		
		//recorro los maps que hay en listaCriterios y voy modificando la "nota" en el map posta
		Map<Jugador, Double> mapAux = new LinkedHashMap<Jugador, Double>();
		
		for (int i=0; i < listaCriterios.size(); i++){
			mapAux = listaCriterios.get(i).generarListaTentativa(n);
			
			Jugador jugMap = null;
			Jugador jugMapAux = null;
			Iterator<Jugador> it = map.keySet().iterator();
			while(it.hasNext()){
				jugMap = it.next();
				
				Iterator<Jugador> it2 = mapAux.keySet().iterator();
				while(it2.hasNext()){
					jugMapAux = it2.next();
					if(jugMap.equals(jugMapAux)){
						map.put(jugMap, map.get(jugMap) + mapAux.get(jugMapAux));
						break;
					}
				}
			}
			
			mapAux.clear();
		}
		
		//genero el "promedio" de la "nota"
		Iterator<Jugador> iter = map.keySet().iterator();
		while(iter.hasNext()){
		  Jugador key = iter.next();
		  map.put(key, map.get(key) / listaCriterios.size());
		}
		
		//ver si se puede mejorar esto: creo una lista y meto valores del map, luego ordeno lista y vuelvo a generar el map 
		List<PromedioPorJugador> listaAOrdenar = new ArrayList<PromedioPorJugador>();
		
		Iterator<Jugador> itera = map.keySet().iterator();
		while(itera.hasNext()){
		  Jugador key = itera.next();
		  PromedioPorJugador obje = new PromedioPorJugador();
		  obje.setJugador(key);
		  obje.setPromedio(map.get(key));
		  listaAOrdenar.add(obje);
		}
		
		Collections.sort(listaAOrdenar, ordComparator);
		
		partido.getListaJugadoresSeleccionados().clear();
		
		map.clear();
		Iterator<PromedioPorJugador> iterato = listaAOrdenar.iterator();
		while(iterato.hasNext()){
			PromedioPorJugador objec = iterato.next();
			map.put(objec.getJugador(), objec.getPromedio());
			partido.getJugadoresSeleccionados().add(objec.getJugador());
		}
		
		return map;
	}
	
	public static Comparator<PromedioPorJugador> ordComparator = new Comparator<PromedioPorJugador>() { 
		public int compare(PromedioPorJugador p1, PromedioPorJugador p2) {
			return Double.compare(p1.getPromedio(), p2.getPromedio()); 
		}
	};

	@Override
	public void agregar(GeneradorEquiposTentativos c) {
		listaCriterios.add(c);
	}
	
	@Override
	public void eliminar(GeneradorEquiposTentativos c) {
		listaCriterios.remove(c);
	}

}