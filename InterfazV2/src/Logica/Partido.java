package Logica;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import BD.BDConnection;
import BD.BDPartido;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@Entity
@Table(name = "PARTIDOS")
public class Partido implements Serializable {

	int id;

	String nombre;

	Date fecha;
boolean empezado = false;
	
	int jugadoresEstandar = 0;
	String hora;

	String lugar;

	boolean cerrado = false;
	boolean confirmado = false;

	List<Jugador> listaDeJugadores = new ArrayList<Jugador>();

	List<Jugador> listaJugadoresSeleccionados = new ArrayList<Jugador>();

	List<Calificacion> listaCalificaciones = new ArrayList<Calificacion>();

	List<Inscripcion> listaDeInscripciones = new ArrayList<Inscripcion>();


	public List<Jugador> getListaJugadoresSeleccionados() {
		return listaJugadoresSeleccionados;
	}

	public void setListaJugadoresSeleccionados(
			List<Jugador> listaJugadoresSeleccionados) {
		this.listaJugadoresSeleccionados = listaJugadoresSeleccionados;
	}
	public void empezarUnPartido(){
		this.setEmpezado(true);
		
		BDPartido bd = new BDPartido();
		try {
			bd.getConnection();
			bd.cambiarEstEmpezado(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void terminarUnPartido(){
		this.setEmpezado(true);
		
		BDPartido bd = new BDPartido();
		try {
			bd.getConnection();
			bd.cambiarEstTerminado(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public boolean isConfirmado() {
		return confirmado;
	}
	public void actualizarConfirmado(boolean estado){
		this.confirmado=estado;
	}
	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;

		try {
			BDPartido bd = new BDPartido();
			bd.getConnection();
			bd.cambiarEstConfirmado(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//UNA VEZ CONFIRMADO, DEBE SACAR EL JUGADOR DE LA LISTA GLOBAL DE PARTIDOS ABIERTOS, AGREGARLA A PARTIDOS CERRADOS, 
		//Y PASAR LOS JUGADORES A LA LISTA DE JUGADORES SELECCIONADOS
	}

	boolean terminado = false;

	public boolean isEmpezado() {
		return empezado;
	}

	public void setEmpezado(boolean empezado) {
		this.empezado = empezado;
	}

	
	

	public Partido() {

	};

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean jugado) {
		this.terminado = jugado;
	}

	public List<Calificacion> getListaCalificaciones() {
		return listaCalificaciones;
	}

	public void setListaCalificaciones(List<Calificacion> listaCalificaciones) {
		this.listaCalificaciones = listaCalificaciones;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public List<Inscripcion> getListaDeInscripciones() {
		return listaDeInscripciones;
	}

	public void setListaDeInscripciones(List<Inscripcion> listaDeInscripciones) {
		this.listaDeInscripciones = listaDeInscripciones;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public boolean isCerrado() {
		return cerrado;
	}
	public void actualizarCerrado(boolean estado){
		this.cerrado = estado;
	}
	public void setCerrado(boolean cerrado) {
		this.cerrado = cerrado;
		try {
			BDPartido bd = new BDPartido();
			bd.getConnection();
			bd.cambiarEstCerrado(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Jugador> getListaDeJugadores() {
		return listaDeJugadores;
	}

	public void setListaDeJugadores(List<Jugador> listaDeJugadores) {
		this.listaDeJugadores = listaDeJugadores;
	}

	public List<Jugador> getJugadoresSeleccionados() {
		return listaJugadoresSeleccionados;
	}

	public void setJugadoresSeleccionados(List<Jugador> jugadoresSeleccionados) {
		this.listaJugadoresSeleccionados = jugadoresSeleccionados;
	}

	public boolean inscribirA(Jugador jugador, Inscripcion inscripcion)
			throws SQLException {

		if (this.cerrado == false) {
			this.getListaDeInscripciones().add(inscripcion);
			if (jugadoresEstandar < 10) {
				this.listaDeJugadores.add(jugador);
				/*
				 * BDConnection generadorQuery = new BDConnection();
				 * generadorQuery.getConnection();
				 * generadorQuery.insertarAJugadoresPartido(jugador, this);
				 */
				// Notification.show("Jugador en lista de jugadores para el partido "
				// + this.getNombre() );
				if (inscripcion instanceof InscripcionEstandar) {
					jugadoresEstandar++;
				}
				if ((jugadoresEstandar == 10)) {
					// Se cierra el partido(10 jugadores estandar)
					this.cerrado = true;
					BDPartido bd = new BDPartido();
					bd.getConnection();
					bd.cambiarEstCerrado(this);
					this.setCerrado(true);
					// envioMensaje.enviarMensajeAAdmin();
				}
				if (this.getListaDeJugadores().size() >= 10
						&& this.cerrado == false) {
					// se le envia mensaje a admin, ya q se llego a los 10
					// inscriptos, pero no se cerro la inscripcion
					// envioMensaje.partidoConfirmado(this);
				}
				Notification.show("Se ha inscripto al partido el jugador "
						+ jugador.getNombre());

			}
			return true;
		} else {
			// informo qe el partido esta cerrado
			Notification.show("Error, el partido está cerrado", Type.ERROR_MESSAGE);
			return false;
		}

	}

	public void darDebajaA(Jugador jugador) {
		this.getListaDeJugadores().remove(jugador);
		this.getJugadoresSeleccionados().remove(jugador);
		BDPartido bd;
		try {
			bd = new BDPartido();
			bd.getConnection();
			bd.darDeBajaJugador(this, jugador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void confirmarEquipos() {
		// Se deberia llamar al metodo q creo maxi
		/*
		 * if(equipoA.listaDeJugadores.size()==5 &&
		 * equipoB.listaDeJugadores.size()==5){ cerrado = true;
		 * equipoA.confirmado = true; equipoB.confirmado = true; }
		 */
	}

	public void empezarPartido() {
		this.empezado = true;
	}

	public void terminarPartido() {
		this.terminado = false;
	}

	public void crearPartido(Partido partido, String nombre, Date fecha,
			String hora, String lugar) {
		partido.setFecha(fecha);
		partido.setLugar(lugar);
		partido.setNombre(nombre);
		partido.setHora(hora);

		// Base de datos

		try {
			BDPartido bd = new BDPartido();
			bd.getConnection();
			bd.crearPartido(nombre, fecha, hora, lugar);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
