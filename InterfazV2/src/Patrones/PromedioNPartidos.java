package Patrones;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import BD.BDPartido;
import Logica.Calificacion;
import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;

public class PromedioNPartidos extends GeneradorEquiposTentativos {
	
	public PromedioNPartidos(){
		this.setNombreMetodo("Promedio N partidos");
	}
	
	List<PromedioPorJugador> listaDePromedios = new ArrayList<PromedioPorJugador>();
	
	public void generarPromediosUltimosPartidos(int n) {
		
		Jugador jugador = null;
		Calificacion calificacion = null;
		int puntajeTotal;
		int cantidadDeVecesCalificado;
		double promedio;
		List<Partido> ultimosPartidos;
		Partido unPartido = null;
		
		BDPartido bd =new BDPartido();
		//traigo todas las calificaciones de la base
		try {
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
		}
				
		List<Calificacion> listaCalificaciones = new ArrayList<Calificacion>();
		listaCalificaciones = bd.obtenerTodasLasCalificaciones();
		
		
		Iterator<Jugador> iteradorJugadores = partido.getJugadoresSeleccionados().iterator();
		while(iteradorJugadores.hasNext()){
			jugador= iteradorJugadores.next();
			puntajeTotal=0;
			cantidadDeVecesCalificado=0;
			
			
			ultimosPartidos = this.buscarUltimosPartidos(jugador, n);
			
			
			Iterator<Partido> iteradorDePartidos = ultimosPartidos.iterator();
			while(iteradorDePartidos.hasNext()){
				unPartido = iteradorDePartidos.next();
				
				String nombreUltPartido = unPartido.getNombre();
				
				if(unPartido == null){
					//nunca jugo un partido, asi que prom 0
					PromedioPorJugador promObj = new PromedioPorJugador();
					promObj.setJugador(jugador);
					promObj.setPromedio(0);
					listaDePromedios.add(promObj);
				}
				else {
					//iterar partido y buscar calificaciones
					if(listaCalificaciones.size() > 0){
						Iterator<Calificacion> iteradorDeCalificaciones = listaCalificaciones.iterator();
						while(iteradorDeCalificaciones.hasNext()){
							calificacion = iteradorDeCalificaciones.next();
							if(calificacion.getJugadorCalificado().getDNI() == jugador.getDNI() 
									&& calificacion.getPartido().getNombre().compareTo(nombreUltPartido) == 0){
								++cantidadDeVecesCalificado;
								puntajeTotal = puntajeTotal + calificacion.getPuntaje();
							}
						}
						if(cantidadDeVecesCalificado == 0){
							PromedioPorJugador promedioObj = new PromedioPorJugador();
							promedioObj.setPromedio(0);
							promedioObj.setJugador(jugador);
							listaDePromedios.add(promedioObj);
						} else{
							promedio = puntajeTotal/cantidadDeVecesCalificado;
							PromedioPorJugador promedioObj = new PromedioPorJugador();
							promedioObj.setPromedio(promedio);
							promedioObj.setJugador(jugador);
							listaDePromedios.add(promedioObj);
						}
						
					} else {
						PromedioPorJugador promedioObj = new PromedioPorJugador();
						promedioObj.setPromedio(0);
						promedioObj.setJugador(jugador);
						listaDePromedios.add(promedioObj);
					}

				}
			}
		}
	}
	
	private List<Partido> buscarUltimosPartidos(Jugador jugador, int n) {
		
		List<Partido> nPartidos = new ArrayList<Partido>();
		
		List<Partido> partidos = new ArrayList<Partido>();
		
		Iterator<Inscripcion> ins = jugador.getListaDeInscripciones()
				.iterator();
		
		while (ins.hasNext()) {
			partidos.add(ins.next().getPartido());
		}
		Collections.sort(partidos, ultimoPartido);
		
		if(partidos.size() > 0 && partidos.size() >= n){
			for(int i=0; i<n; i++){
				nPartidos.add(partidos.get(i));
			}
		}
		if (partidos.size() > 0){
			for(int i=0; i<partidos.size(); i++){
				nPartidos.add(partidos.get(i));
			}
		}
		else nPartidos = null;
		
		return nPartidos;
		
		/*List<Partido> listaUltimosPartidos = new ArrayList<Partido>();
		
		//ordeno lista de inscripciones del jugador por fecha del partido perteneciente a esa inscripcion
		Collections.sort(jugador.getListaDeInscripciones(), inscripComparator);
		
		Inscripcion inscAux = null;
		Iterator<Inscripcion> iterator = jugador.getListaDeInscripciones().iterator();
		for(int i=0; i<n; i++){
			iterator.hasNext();
			inscAux = iterator.next();
			listaUltimosPartidos.add(inscAux.getPartido());		
		}
		
		return listaUltimosPartidos;
		*/	
	}
	
	//para buscar ultimo partido
	public static Comparator<Partido> ultimoPartido = new Comparator<Partido>() {
		public int compare(Partido p1, Partido p2) {

			if (p1.getFecha().before(p2.getFecha())) {
				return -1;
			} else if (p1.getFecha().after(p2.getFecha())) {
				return 1;
			} else {
				return 0;
			}
		}

	};

	/*
	public static Comparator<Inscripcion> inscripComparator = new Comparator<Inscripcion>() { 
		@SuppressWarnings("deprecation")
		public int compare(Inscripcion ins1, Inscripcion ins2) {
			//return Integer.compare(ins1.getPartido().getFecha().getDate(), ins2.getPartido().getFecha().getDate()) ; 
			return ins1.getPartido().getFecha().compareTo(ins2.getPartido().getFecha()); 
		}
	};*/

	@Override
	
	
	public Map<Jugador, Double> generarListaTentativa(int n) {
		
		this.generarPromediosUltimosPartidos(n);
		Collections.sort(listaDePromedios, promedioUltimoPartidoComparator);
		
		partido.getJugadoresSeleccionados().clear();
		
		Map<Jugador, Double> map = new LinkedHashMap<Jugador, Double>();
		PromedioPorJugador promObj = null;
		Iterator<PromedioPorJugador> iteradorPromedios = listaDePromedios.iterator();
		while(iteradorPromedios.hasNext()){
			promObj = iteradorPromedios.next();
			partido.getJugadoresSeleccionados().add(promObj.getJugador());
			map.put(promObj.getJugador(), promObj.getPromedio());
		}
		
		return map;
	}
	
	
	//para ordenar lista de promedios
	public static Comparator<PromedioPorJugador> promedioUltimoPartidoComparator = new Comparator<PromedioPorJugador>() { 
		public int compare(PromedioPorJugador prom1, PromedioPorJugador prom2) { //ver si no va double
			return Double.compare(prom1.getPromedio(), prom2.getPromedio()); 
		}
	};

	@Override
	public void agregar(GeneradorEquiposTentativos c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(GeneradorEquiposTentativos c) {
		// TODO Auto-generated method stub
		
	}
	
}
