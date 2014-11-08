package Logica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import BD.BDAdministrador;
import BD.BDEquipo;
import BD.BDJugador;
import BD.BDInscripciones;
import BD.BDPartido;
import Patrones.ObservableJugadores;
import Patrones.ObservadorAdmin;
import Patrones.SingletonJugadoresParaAprobar;

public class Administrador implements Serializable {

	public Administrador() {
		BDPartido bdPartido;
		try {
			bdPartido = new BDPartido();
			bdPartido.getConnection();
			this.listaPartidos = bdPartido.obtenerPartidosAbiertos();
			this.listaPartidosCerrados = bdPartido.obtenerPartidosCerrados();
			this.listaPartidosConfirmados= bdPartido.obtenerPartidosConfirmados();
			this.listaPartidosEmpezados= bdPartido.obtenerPartidosEmpezados();
			this.listaPartidosTerminados= bdPartido.obtenerPartidosTerminado();
			BDJugador bdJugador = new BDJugador();
			bdJugador.getConnection();
			this.listaDeJugadores = bdJugador.obtenerJugadores();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	    }

	}

	int id;
	List<Partido> listaPartidos = new ArrayList<Partido>();

	List<Partido> listaPartidosCerrados = new ArrayList<Partido>();
	List<Partido> listaPartidosConfirmados = new ArrayList<Partido>();
	List<Partido> listaPartidosEmpezados = new ArrayList<Partido>();
	List<Partido> listaPartidosTerminados = new ArrayList<Partido>();

	List<Rechazado> ListaDeRechazados = new ArrayList<Rechazado>();

	List<Jugador> listaDeJugadores = new ArrayList<Jugador>();

	List<Jugador> listaDeJugadoresParaAprobar = new ArrayList<Jugador>();
	
	public List<Partido> getListaPartidosCerrados() {
		return listaPartidosCerrados;
	}

	public void setListaPartidosCerrados(List<Partido> listaPartidosCerrados) {
		this.listaPartidosCerrados = listaPartidosCerrados;
	}

	public List<Partido> getListaPartidosConfirmados() {
		return listaPartidosConfirmados;
	}

	public void setListaPartidosConfirmados(
			List<Partido> listaPartidosConfirmados) {
		this.listaPartidosConfirmados = listaPartidosConfirmados;
	}

	public List<Partido> getListaPartidosEmpezados() {
		return listaPartidosEmpezados;
	}

	public void setListaPartidosEmpezados(List<Partido> listaPartidosEmpezados) {
		this.listaPartidosEmpezados = listaPartidosEmpezados;
	}

	public List<Partido> getListaPartidosTerminados() {
		return listaPartidosTerminados;
	}

	public void setListaPartidosTerminados(List<Partido> listaPartidosTerminados) {
		this.listaPartidosTerminados = listaPartidosTerminados;
	}

	

	public List<Jugador> getListaDeJugadoresParaAprobar() {
	
		BDAdministrador bdAdministrador;
		try {
			bdAdministrador = new BDAdministrador();
			bdAdministrador.getConnection();
			
			listaDeJugadoresParaAprobar = bdAdministrador.getJugadoresParaAprobar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	    }
		return listaDeJugadoresParaAprobar;
	}

	public void setListaDeJugadoresParaAprobar(
			List<Jugador> listaDeJugadoresParaAprobar) {
		this.listaDeJugadoresParaAprobar = listaDeJugadoresParaAprobar;
	}

	SingletonJugadoresParaAprobar avisoNuevosJugadores = SingletonJugadoresParaAprobar
			.getInstance();

	public SingletonJugadoresParaAprobar getAvisoNuevosJugadores() {
		return avisoNuevosJugadores;
	}

	public void setAvisoNuevosJugadores(
			SingletonJugadoresParaAprobar avisoNuevosJugadores) {
		this.avisoNuevosJugadores = avisoNuevosJugadores;
	}

	public Partido organizarPardido(String nombre, Date fecha, String hora,
			String lugar) {
		Partido partido = null;
		partido = new Partido();
		partido.crearPartido(partido, nombre, fecha, hora, lugar);

		this.getListaPartidos().add(partido);
		Notification.show("Se creó el partido " + nombre);
		return partido;
	}

	public List<Partido> getListaPartidos() {
		return listaPartidos;
	}

	public void setListaPartidos(List<Partido> listaPartidos) {
		this.listaPartidos = listaPartidos;
	}

	public List<Rechazado> getListaDeRechazados(Date fechaPartido,String nombrePartido,String lugarPartido) {
		
		try {
			BDAdministrador bd = new BDAdministrador();
			bd.getConnection();
			ListaDeRechazados = bd.getJugadoresRechazados();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ListaDeRechazados;
	}

	public void setListaDeRechazados(List<Rechazado> listaDeRechazados) {
		ListaDeRechazados = listaDeRechazados;
	}

	public List<Jugador> getListaDeJugadores() {

		return listaDeJugadores;
	}

	public void setListaDeJugadores(List<Jugador> listaDeJugadores) {
		this.listaDeJugadores = listaDeJugadores;
	}

	public void generarEquiposTentativos(Partido partido) {

	}

	public void confirmarEquipos(Partido partido) {
		partido.confirmarEquipos();

	}

	public void registrarYJustificar(String motivo, Jugador jugadorPropuesto,
			Jugador jugadorProponedor,Date fechaPartido,String nombrePartido,String lugarPartido) {
		Rechazado rechazado = new Rechazado();
		rechazado.jugadorProponedor = jugadorProponedor;
		rechazado.jugadorPropuesto = jugadorPropuesto;
		rechazado.motivo = motivo;
		java.util.Date fecha = new Date();
		rechazado.fecha = fecha;
		this.getListaDeRechazados(fechaPartido,nombrePartido,lugarPartido).add(rechazado);
		
		try {
			BDAdministrador bd = new BDAdministrador();
			bd.getConnection();
			//bd.insertarRechazado(jugadorPropuesto.DNI,jugadorProponedor.DNI,motivo,fecha,fechaPartido,nombrePartido,lugarPartido);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void aceptarONo(Jugador jugador, boolean aprobado, String motivo){
		if (aprobado == true) {
			
			BDAdministrador bdAdministrador;
			try {
				Partido partido = null;
				Integer DniProponedor = null;
				Integer IdInscripcion = null;
				Inscripcion inscripcion = null;
				bdAdministrador = new BDAdministrador();
				bdAdministrador.getConnection();
				DniProponedor = bdAdministrador.obtenerDNIProponedor(jugador.getDNI());
				partido = bdAdministrador.obtenerPartidoParaAprobar(jugador.getDNI());
				bdAdministrador.actualizarAprobado(jugador);
				BDInscripciones bdInscripciones;
				bdInscripciones = new BDInscripciones();
				bdInscripciones.getConnection();
				IdInscripcion = bdInscripciones.obtenerInscripcionIdPorPartidoYDni(DniProponedor);
				inscripcion = bdInscripciones.obtenerInscripcionPorID(IdInscripcion);
				
				jugador.inscribirseAUn(partido, inscripcion);
				
				//Notification.show("El jugador " + jugador.getNombre()
					//	+ " ha sido agregado" +inscripcion.getID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		    }
			
			
		} else if(aprobado == false) {
			
			BDAdministrador bdAdministrador;
			try {
				
			
				Partido partido = null;
				Integer DniProponedor = null;
				
				bdAdministrador = new BDAdministrador();
				bdAdministrador.getConnection();
				DniProponedor = bdAdministrador.obtenerDNIProponedor(jugador.getDNI());
				partido = bdAdministrador.obtenerPartidoParaAprobar(jugador.getDNI());
				
				Rechazado rechazado = new Rechazado();
				
				bdAdministrador.insertarRechazado(jugador.getDNI(), DniProponedor,motivo);
				
				rechazado = bdAdministrador.getJugadorRechazado(jugador.getDNI(), DniProponedor);
				this.getListaDeRechazados(partido.getFecha(), partido.getNombre(), partido.getLugar()).add(rechazado);
				
				this.getListaDeJugadoresParaAprobar().remove(jugador);
				bdAdministrador.borrarJugadorParaArpobar(jugador.DNI);
				
				Notification.show("El jugador " + jugador.getNombre()
						+ "NO ha sido agregado");
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
		    }
			
	
		
		}
	}

	public void cerrarInscripcion(Partido partido) { // ordena listaJug (del
														// parti) y la pasa a
														// listaJugSeleccionados
														// (del parti)

		int i = 0;
		Iterator<Inscripcion> iterator = partido.getListaDeInscripciones()
				.iterator();
		Inscripcion inscrip = null;
		if(partido.getListaDeJugadores().size() >= 10 ){
		while (i < 10) {

			while (iterator.hasNext() && i < 10) {
				inscrip = iterator.next();
				if (inscrip instanceof InscripcionEstandar) {
					inscrip.setInscripto(true);

					partido.listaJugadoresSeleccionados.add(inscrip
							.getJugador());
					i++;

					// Inserto a la base de datos
					try {
						BDAdministrador bd = new BDAdministrador();
						bd.getConnection();
						bd.actualizarInscripcionAceptada(inscrip.getID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			iterator = partido.getListaDeInscripciones().iterator();
			while (iterator.hasNext() && i < 10) {
				inscrip = iterator.next();
				if (inscrip instanceof InscripcionSolidaria) {
					inscrip.setInscripto(true);
					partido.listaJugadoresSeleccionados.add(inscrip
							.getJugador());
					i++;

					// Inserto a la base de datos
					try {
						BDAdministrador bd = new BDAdministrador();
						bd.getConnection();
						bd.actualizarInscripcionAceptada(inscrip.getID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			iterator = partido.getListaDeInscripciones().iterator();
			while (iterator.hasNext() && i < 10) {
				inscrip = iterator.next();
				if (inscrip instanceof InscripcionCondicional) {
					inscrip.setInscripto(true);
					
					partido.listaJugadoresSeleccionados.add(inscrip
							.getJugador());
					i++;

					// Inserto a la base de datos
					try {
						BDAdministrador bd = new BDAdministrador();
						bd.getConnection();
						bd.actualizarInscripcionAceptada(inscrip.getID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (!this.getListaPartidos().remove(partido))
			this.getListaPartidosCerrados().remove(partido);
		this.getListaPartidosConfirmados().add(partido);
		partido.setConfirmado(true);
		partido.setCerrado(true);
		Notification.show("Partido confirmado");
		}
		else Notification.show("No hay suficientes jugadores", Type.ERROR_MESSAGE);
	}

	public void borrarJugador(Jugador jugador) {
		this.listaDeJugadores.remove(jugador);

		// Borro desde la base de datos
		try {
			BDJugador bd = new BDJugador();
			bd.getConnection();
			bd.borrarJugador(jugador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Equipo> dividirEquiposParImpar (Partido partido, String nombreEquipoA, String nombreEquipoB){
		if (nombreEquipoA!= null && nombreEquipoB!= null && nombreEquipoA!= "" && nombreEquipoB != ""){
		Equipo equipoPar = new Equipo();
		Equipo equipoImpar = new Equipo();
		List<Equipo> equipos = new ArrayList<Equipo>();
		equipos.add(equipoPar);
		equipos.add(equipoImpar);
		equipoPar.setNombre(nombreEquipoA);
		equipoImpar.setNombre(nombreEquipoB);
		
		//Base de datos
		try {
			BDEquipo bdequipos = new BDEquipo();
			bdequipos.getConnection();
			bdequipos.crearEquipo(nombreEquipoA);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BDEquipo bdequipos = new BDEquipo();
			bdequipos.getConnection();
			bdequipos.crearEquipo(nombreEquipoB);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BDEquipo bdEquipo = new BDEquipo();
		try {
			bdEquipo.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 1;
		Jugador jugador = null;
		Iterator<Jugador> iterator = partido.getJugadoresSeleccionados().iterator();
		while(iterator.hasNext()) {
			jugador = iterator.next();
			
			if(i % 2 == 0){
				equipoPar.getListaDeJugadores().add(jugador);
				bdEquipo.cargarJugadorEnJugadoresEquipo(jugador, partido, equipoPar);
			} else 
				{
					equipoImpar.getListaDeJugadores().add(jugador);
					bdEquipo.cargarJugadorEnJugadoresEquipo(jugador, partido, equipoImpar);
				}	
			
			i++;
		}
		return equipos;
	}else {
		Notification.show("Ingrese los nombres de los equipos por favor",Type.ERROR_MESSAGE);
		return null;
	}
	}
	
	
	public List<Equipo> dividirEquiposSegundoCriterio (Partido partido, String nombreEquipoA, String nombreEquipoB){
		
		if (nombreEquipoA!= null && nombreEquipoB!= null && nombreEquipoA!= "" && nombreEquipoB != ""){
		
		Equipo equipoA = new Equipo();
		Equipo equipoB = new Equipo();	
		List<Equipo> equipos = new ArrayList<Equipo>();
		equipos.add(equipoA);
		equipos.add(equipoB);
		equipoA.setNombre(nombreEquipoA);
		equipoB.setNombre(nombreEquipoB);
		
		//Base de datos
		try {
			BDEquipo bdequipos = new BDEquipo();
			bdequipos.getConnection();
			bdequipos.crearEquipo(nombreEquipoA);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BDEquipo bdequipos = new BDEquipo();
			bdequipos.getConnection();
			bdequipos.crearEquipo(nombreEquipoB);;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BDEquipo bdEquipo = new BDEquipo();
		try {
			bdEquipo.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 1;
		Jugador jugador = null;
		Iterator<Jugador> iterator = partido.getJugadoresSeleccionados().iterator();
		while(iterator.hasNext()) {
			jugador = iterator.next();
			
			if (i==1 || i==4 || i==5 || i==8 || i==9){
				equipoA.getListaDeJugadores().add(jugador);
				bdEquipo.cargarJugadorEnJugadoresEquipo(jugador, partido, equipoA);
			} else {
				equipoB.getListaDeJugadores().add(jugador);
				bdEquipo.cargarJugadorEnJugadoresEquipo(jugador, partido, equipoB);
			}
			
			i++;
		}
		return equipos;
	}else {
		Notification.show("Ingrese los nombres de los equipos por favor",Type.ERROR_MESSAGE);
		return null;
	}
	}
	
	
	
	public void cambiarDeEstadoUnPartidoAEmpezado(Partido partido){
		if (this.getListaPartidosConfirmados().remove(partido) && !partido.isEmpezado()){
			this.getListaPartidosEmpezados().add(partido);
			partido.empezarUnPartido();
			 Notification.show("Partido empezado");
		}
		
		else Notification.show("El partido no está empezado, o ya empezó", Type.ERROR_MESSAGE);
	}
	public void cambiarDeEstadoUnPartidoATerminado(Partido partido){
		if (this.getListaPartidosEmpezados().remove(partido) && !partido.isTerminado()){
			this.getListaPartidosTerminados().add(partido);
			partido.terminarUnPartido();
			 Notification.show("Partido terminado");
		}
		
		else Notification.show("El partido no está empezado, o ya terminó", Type.ERROR_MESSAGE);
	}
	
}
