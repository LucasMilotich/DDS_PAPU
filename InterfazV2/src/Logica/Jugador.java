package Logica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Notification;

import BD.BDAdministrador;
import BD.BDJugador;
import BD.BDPartido;
import Patrones.ObservableJugadores;
import Patrones.ObservadorAdmin;
import Patrones.PromedioPorJugador;
import Patrones.SingletonJugadoresParaAprobar;

public class Jugador extends ObservableJugadores implements Serializable {

	Integer DNI;

	String nombre;

	String apellido;

	int edad;

	Integer nivelDeJuego;

	Jugador reemplazo;


	String tuvoInfracciones = "NO";
	double promedioUltimoPartido = 0;
	Date fechaNacimiento;
	
	List<Inscripcion> listaDeInscripciones = new ArrayList<Inscripcion>();

	List<Amigo> amigos = new ArrayList<Amigo>();
	List<Penalizacion> penalizaciones = new ArrayList<Penalizacion>();
	
	public String getTuvoInfracciones() {
		return tuvoInfracciones;
	}

	public void setTuvoInfracciones(String tuvoInfracciones) {
		this.tuvoInfracciones = tuvoInfracciones;
	}

	public double getPromedioUltimoPartido() {
		return promedioUltimoPartido;
	}

	public void setPromedioUltimoPartido(double promedioUltimoPartido) {
		this.promedioUltimoPartido = promedioUltimoPartido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setNivelDeJuego(Integer nivelDeJuego) {
		this.nivelDeJuego = nivelDeJuego;
	}

	public List<Penalizacion> getPenalizaciones() {
		return penalizaciones;
	}

	public void setPenalizaciones(List<Penalizacion> penalizaciones) {
		this.penalizaciones = penalizaciones;
	}

	public Jugador() {

	}

	public Integer getDNI() {
		return DNI;
	}

	public void setDNI(Integer dNI) {
		DNI = dNI;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Jugador getReemplazo() {
		return reemplazo;
	}

	public void setReemplazo(Jugador reemplazo) {
		this.reemplazo = reemplazo;
	}

	public List<Amigo> getListaDeAmigos() {
		return this.amigos;
	}

	public List<Inscripcion> getListaDeInscripciones() {
		return listaDeInscripciones;
	}

	public void setListaDeInscripciones(List<Inscripcion> listaDeInscripciones) {
		this.listaDeInscripciones = listaDeInscripciones;
	}

	public Integer getNivelDeJuego() {
		return nivelDeJuego;
	}

	public void setNivelDeJuego(int nivelDeJuego) {
		this.nivelDeJuego = nivelDeJuego;
		BDJugador bd;
		try {
			bd = new BDJugador();
			bd.getConnection();
			bd.cambiarNivelDeJuego(this, nivelDeJuego);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public List<Amigo> getAmigos() {
		return amigos;
	}

	public void setAmigos(List<Amigo> amigos) {
		this.amigos = amigos;
	}

	public void inscribirseAUn(Partido partido, Inscripcion inscripcion)
			throws SQLException {

		inscripcion.crearInscripcion(inscripcion, this, partido,
				partido.inscribirA(this, inscripcion));

		this.getListaDeInscripciones().add(inscripcion);
	}

	public void bajaDeUn(Partido partido) {
		Iterator<Inscripcion> iterador = this.getListaDeInscripciones()
				.iterator();

		boolean encontrado = false;
		Inscripcion inscrip = null;
		while (iterador.hasNext() && encontrado == false) {
			inscrip = iterador.next();
			if (inscrip.getPartido() == partido) {
				this.getListaDeInscripciones().remove(inscrip);
				inscrip.borrarInscripcion(inscrip);
				partido.darDebajaA(this);
				encontrado = true;

			}
		}
		if (encontrado == true) {

			Notification.show("Te diste de baja del partido "
					+ partido.getNombre());
		} else
			Notification.show("No estas inscripto a ese partido");
	}

	public void recibirRespuestaNuevoJugador(String respuesta, Jugador jugador) {

	}

	public void jugadoresDeUn(Partido partido) {

		Iterator<Jugador> listaDeJugadores = partido
				.getJugadoresSeleccionados().iterator();
		// List<Calificacion> listaDeCalificaciones = new
		// ArrayList<Calificacion>();
		while (listaDeJugadores.hasNext()) {
			listaDeJugadores.next();

		}

	}

	public void calificarA(Partido partido, Jugador jugador, int puntaje) {
		Calificacion calificacion = new Calificacion();
		calificacion.setJugadorCalificado(jugador);
		calificacion.setJugadorCalificador(this);
		calificacion.setPuntaje(puntaje);
		partido.getListaCalificaciones().add(calificacion);
	}

	public void crearJugador(String nombre, int edad, Administrador admin) {
		this.setEdad(edad);
		this.setNombre(nombre);
		admin.getListaDeJugadores().add(this);
	}

	public List<Partido> obtenerPartidosInscripto() {
		List<Inscripcion> listaDeInscripciones = this.getListaDeInscripciones();
		if (listaDeInscripciones != null) {
			Iterator<Inscripcion> iterator = listaDeInscripciones.iterator();
			List<Partido> lista = new ArrayList<Partido>();
			Inscripcion inscrip = null;
			while (iterator.hasNext()) {
				inscrip = iterator.next();
				if (inscrip.isInscripto() == true)
					lista.add(inscrip.getPartido());

			}

			return lista;
		} else
			Notification.show("ERROR, NO HAY PARTIDOS INSCRIPTOS");
		return null;

	}

	/*
	 * public void recibirRespuestaNuevoJugadorRechazado(String mensaje, Jugador
	 * jugadorPropuesto, Jugador jugadorProponedor, Administrador admin) {
	 * admin.registrarYJustificar(mensaje, jugadorPropuesto, jugadorProponedor);
	 * // TODO Auto-generated method stub
	 * 
	 * }
	 */

	public void rechazosJugador() {
		BDAdministrador bd;
		try {
			bd = new BDAdministrador();
			bd.getConnection();
			bd.getRechazoJugador(this.DNI);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recibirRespuestaNuevoJugadorAceptado(String respuesta,
			Jugador jugador, Administrador admin) {
		this.crearJugador(jugador.nombre, jugador.edad, admin);
	}

	public void borrarmeComoJugador(Administrador admin) {
		admin.borrarJugador(this);
	}

	public void proponerJugador(String nombre, String apellido, int dni,
			int edad, Date fechaNacimiento, Partido partido, Administrador admin) {
		Jugador jugador = null;
		jugador = new Jugador();
		jugador.setEdad(edad);
		jugador.setNombre(nombre);
		jugador.setApellido(apellido);
		jugador.setDNI(dni);
		jugador.setFechaNacimiento(fechaNacimiento);
		jugador.setPromedioUltimoPartido(0);
		jugador.setTuvoInfracciones("NO");
		admin.listaDeJugadoresParaAprobar.add(jugador);
		admin.listaDeJugadores.add(jugador);
		BDJugador bd;
		try {
			bd = new BDJugador();
			bd.getConnection();
			//bd.crearJugador(dni, nombre, apellido, edad);
			bd.crearJugadorParaAprobar(jugador, partido,this.getDNI());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void proponerAmigo(String nombre, int edad, Partido partido) {
		Amigo amigo = null;
		amigo = new Amigo();
		amigo.setEdad(edad);
		amigo.setNombre(nombre);

		Iterator<Inscripcion> iterator = this.listaDeInscripciones.iterator();
		while (iterator.hasNext()) {
			Inscripcion inscrip = iterator.next();
			if (inscrip.getPartido() == partido) {

				if (inscrip instanceof InscripcionEstandar) {
					InscripcionEstandar i = new InscripcionEstandar();
					i.setJugador(amigo);
					i.setInscripto(false);
					i.setPartido(partido);
					amigo.getListaDeInscripciones().add(i);
				}

				if (inscrip instanceof InscripcionSolidaria) {
					InscripcionSolidaria i = new InscripcionSolidaria();
					i.setJugador(amigo);
					i.setInscripto(false);
					i.setPartido(partido);
					amigo.getListaDeInscripciones().add(i);
				}

				if (inscrip instanceof InscripcionCondicional) {
					InscripcionCondicional i = new InscripcionCondicional();
					i.setJugador(amigo);
					i.setInscripto(false);
					i.setPartido(partido);
					amigo.getListaDeInscripciones().add(i);
				}
			}
		}
		this.amigos.add(amigo);
		SingletonJugadoresParaAprobar aviso = SingletonJugadoresParaAprobar
				.getInstance();
		aviso.agregarNuevoJugadorAAprobar(amigo);
	}

	public void borrarAmigo(Amigo amigo) {
		boolean encontrado = false;
		Iterator<Amigo> listaDeAmigos = this.getListaDeAmigos().iterator();
		// List<Calificacion> listaDeCalificaciones = new
		// ArrayList<Calificacion>();
		while (listaDeAmigos.hasNext() && encontrado == false) {
			if (listaDeAmigos.equals(amigo.getNombre())) {
				this.amigos.remove(amigo);
				encontrado = true;
			}
			listaDeAmigos.next();

		}

	}

	public void darDeAltaJugador(Jugador jugador, Integer DNI, String nombre,
			String Apellido, int edad) {
		jugador.setDNI(DNI);
		jugador.setApellido(Apellido);
		jugador.setEdad(edad);
		jugador.setNombre(nombre);

		// Inserto a la base de datos
		try {
			BDJugador bd = new BDJugador();
			bd.getConnection();
			bd.crearJugador(DNI, nombre, Apellido, edad);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double obtenerPromedioUltimoPartido() {
		Partido ultPartido = this.buscarUltPartido(this);
		BDPartido bd = new BDPartido();
		try {
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ultPartido != null)
			/*
			 * ultPartido = bd.obtenerPartidoPorId(bd.obtenerIDPartido(
			 * ultPartido.getNombre(), ultPartido.getLugar(),
			 * ultPartido.getFecha()));
			 */
			ultPartido.setListaCalificaciones(bd
					.obtenerCalificacionesPorPartidoID(ultPartido));
		if (ultPartido == null) {
			// nunca jugo un partido, asi que prom 0
			PromedioPorJugador promObj = new PromedioPorJugador();
			promObj.setJugador(this);
			promObj.setPromedio(0);

		} else {
			// iterar partido y buscar calificaciones
			if (ultPartido.getListaCalificaciones().size() >= 0) {
				int cantidadDeVecesCalificado = 0;
				int puntajeTotal = 0;
				int promedio;
				Calificacion calificacion = null;
				Iterator<Calificacion> iteradorDeCalificaciones = ultPartido
						.getListaCalificaciones().iterator();
				while (iteradorDeCalificaciones.hasNext()) {
					calificacion = iteradorDeCalificaciones.next();
					if (calificacion.getJugadorCalificado().getDNI() == this
							.getDNI()) {
						++cantidadDeVecesCalificado;
						puntajeTotal = puntajeTotal + calificacion.getPuntaje();
					}
				}
				if (cantidadDeVecesCalificado == 0) {
					PromedioPorJugador promedioObj = new PromedioPorJugador();
					promedioObj.setPromedio(0);
					promedioObj.setJugador(this);
					return promedioObj.getPromedio();
				} else {
					promedio = puntajeTotal / cantidadDeVecesCalificado;
					PromedioPorJugador promedioObj = new PromedioPorJugador();
					promedioObj.setPromedio(promedio);
					promedioObj.setJugador(this);
					return promedioObj.getPromedio();
				}

			} else {
				PromedioPorJugador promedioObj = new PromedioPorJugador();
				promedioObj.setPromedio(0);
				promedioObj.setJugador(this);
				return promedioObj.getPromedio();
			}

		}
		return 0;

	}

	public Partido buscarUltPartido(Jugador jugador) {

		Partido partido = null;
		List<Partido> partidos = new ArrayList<Partido>();

		Iterator<Inscripcion> ins = jugador.getListaDeInscripciones()
				.iterator();

		while (ins.hasNext()) {
			partidos.add(ins.next().getPartido());
		}
		Collections.sort(partidos, ultimoPartido);
		if (partidos.size() > 0) {
			partido = partidos.get(0);
		} else
			partido = null;

		return partido;
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

	public double obtenerPromedioDeTodosLosPartidos() {
		Partido ultPartido = null;
		BDJugador bdJugador = new BDJugador();
		BDPartido bd = new BDPartido();
		int puntajeTotal = 0;
		try {
			bdJugador.getConnection();
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Inscripcion> inscripciones = bdJugador.obtenerInscriciones(this);
		Iterator<Inscripcion> iterador = inscripciones.iterator();
		while (iterador.hasNext()) {
			ultPartido = iterador.next().getPartido();
			if (ultPartido != null)
				/*
				 * ultPartido = bd.obtenerPartidoPorId(bd.obtenerIDPartido(
				 * ultPartido.getNombre(), ultPartido.getLugar(),
				 * ultPartido.getFecha()));
				 */
				ultPartido.setListaCalificaciones(bd
						.obtenerCalificacionesPorPartidoID(ultPartido));
			if (ultPartido == null) {
				// nunca jugo un partido, asi que prom 0
				PromedioPorJugador promObj = new PromedioPorJugador();
				promObj.setJugador(this);
				promObj.setPromedio(0);

			} else {
				// iterar partido y buscar calificaciones
				if (ultPartido.getListaCalificaciones().size() >= 0) {
					int cantidadDeVecesCalificado = 0;
					puntajeTotal = 0;
					int promedio;
					Calificacion calificacion = null;
					Iterator<Calificacion> iteradorDeCalificaciones = ultPartido
							.getListaCalificaciones().iterator();
					while (iteradorDeCalificaciones.hasNext()) {
						calificacion = iteradorDeCalificaciones.next();
						if (calificacion.getJugadorCalificado().getDNI() == this
								.getDNI()) {
							++cantidadDeVecesCalificado;
							puntajeTotal = puntajeTotal
									+ calificacion.getPuntaje();
						}
					}
					if (cantidadDeVecesCalificado == 0) {
						PromedioPorJugador promedioObj = new PromedioPorJugador();
						promedioObj.setPromedio(0);
						promedioObj.setJugador(this);
						return promedioObj.getPromedio();
					} else {
						promedio = puntajeTotal / cantidadDeVecesCalificado;
						PromedioPorJugador promedioObj = new PromedioPorJugador();
						promedioObj.setPromedio(promedio);
						promedioObj.setJugador(this);
						return promedioObj.getPromedio();
					}

				} else {
					PromedioPorJugador promedioObj = new PromedioPorJugador();
					promedioObj.setPromedio(0);
					promedioObj.setJugador(this);
					return promedioObj.getPromedio();
				}

			}

		}
		return puntajeTotal;
	}

}
