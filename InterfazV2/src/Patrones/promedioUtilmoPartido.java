package Patrones;
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

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class promedioUtilmoPartido extends GeneradorEquiposTentativos {
	
	public promedioUtilmoPartido(){
		this.setNombreMetodo("Promedio último partidos");
	}
	
	List<PromedioPorJugador> listaDePromedios = new ArrayList<PromedioPorJugador>();
	
	@Override
	public Map<Jugador, Double> generarListaTentativa(int n) {
		
		BDPartido bd =new BDPartido();
		
		Jugador jug = null;
		Partido ultPartido = null;
		
		//traigo todas las calificaciones de la base
		try {
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Calificacion> listaCalificaciones = new ArrayList<Calificacion>();
		listaCalificaciones = bd.obtenerTodasLasCalificaciones();
		
		
		
		Iterator<Jugador> iterator = partido.getJugadoresSeleccionados().iterator();
		while(iterator.hasNext()){
			jug = iterator.next();
			ultPartido = this.buscarUltPartido(jug);
						
			if(ultPartido == null){
				//nunca jugo un partido, asi que prom 0
				PromedioPorJugador promObj = new PromedioPorJugador();
				promObj.setJugador(jug);
				promObj.setPromedio(0);
				listaDePromedios.add(promObj);
			}
			else {
				
				String nombreUltPartido = ultPartido.getNombre();
				
				//iterar partido y buscar calificaciones
				if(listaCalificaciones.size() > 0){
					int cantidadDeVecesCalificado = 0;
					int puntajeTotal = 0;
					int promedio;
					Calificacion calificacion = null;
					Iterator<Calificacion> iteradorDeCalificaciones = listaCalificaciones.iterator();
					while(iteradorDeCalificaciones.hasNext()){
						calificacion = iteradorDeCalificaciones.next();
						if(calificacion.getJugadorCalificado().getDNI() == jug.getDNI() 
								&& calificacion.getPartido().getNombre().compareTo(nombreUltPartido) == 0){
							++cantidadDeVecesCalificado;
							puntajeTotal = puntajeTotal + calificacion.getPuntaje();
						}
					}
					if(cantidadDeVecesCalificado == 0){
						PromedioPorJugador promedioObj = new PromedioPorJugador();
						promedioObj.setPromedio(0);
						promedioObj.setJugador(jug);
						listaDePromedios.add(promedioObj);
					} else{
						promedio = puntajeTotal/cantidadDeVecesCalificado;
						PromedioPorJugador promedioObj = new PromedioPorJugador();
						promedioObj.setPromedio(promedio);
						promedioObj.setJugador(jug);
						listaDePromedios.add(promedioObj);
					}
					
				} else {
					PromedioPorJugador promedioObj = new PromedioPorJugador();
					promedioObj.setPromedio(0);
					promedioObj.setJugador(jug);
					listaDePromedios.add(promedioObj);
				}
			
			}

		}
		
		//ordeno listaDePromedios para despues pasarla ordenada al map
		Collections.sort(listaDePromedios, promedioUltimoPartidoComparator);
		
		partido.getListaJugadoresSeleccionados().clear();
		
		//itero listaDePromedios para generar el map
		Map<Jugador, Double> map = new LinkedHashMap<Jugador, Double>();
		Iterator<PromedioPorJugador> iter = listaDePromedios.iterator();
		while(iter.hasNext()){
			PromedioPorJugador p = iter.next();
			map.put(p.getJugador(), p.getPromedio());
			partido.getListaJugadoresSeleccionados().add(p.getJugador());
		}

		
		return map;
	}

	
	public Partido buscarUltPartido(Jugador jugador) {

		Partido parti = null;
		List<Partido> partidos = new ArrayList<Partido>();

		Iterator<Inscripcion> ins = jugador.getListaDeInscripciones()
				.iterator();

		while (ins.hasNext()) {
			
			Inscripcion inscAux = ins.next();
			
			if(inscAux.getPartido().getNombre() != partido.getNombre()){
				partidos.add(inscAux.getPartido());
			}
			
		}
		Collections.sort(partidos, ultimoPartido);
		
		if(partidos.size() > 0) {
			parti = partidos.get(0);
		}
		
		return parti;
	}
	
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


	public static Comparator<PromedioPorJugador> promedioUltimoPartidoComparator = new Comparator<PromedioPorJugador>() { 
		public int compare(PromedioPorJugador p1, PromedioPorJugador p2) {
			return Double.compare(p1.getPromedio(), p2.getPromedio()); 
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