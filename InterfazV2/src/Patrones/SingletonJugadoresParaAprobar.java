package Patrones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.interfazv2.InterfazPresenter;

import Logica.Administrador;
import Logica.Jugador;
import Logica.Rechazado;

public class SingletonJugadoresParaAprobar implements Serializable {
	Administrador admin;
	
	List<Rechazado> listaDeRechazados = new ArrayList<Rechazado>();
	InterfazPresenter presenter ;
	boolean aceptado;
	private static SingletonJugadoresParaAprobar instance = null;
	public InterfazPresenter getPresenter() {
		return presenter;
	}
	public void setPresenter(InterfazPresenter presenter) {
		this.presenter = presenter;
	}
	protected SingletonJugadoresParaAprobar() {
	      // Exists only to defeat instantiation.
	   }
	public static SingletonJugadoresParaAprobar getInstance() {
	      if(instance == null) {
	         instance = new SingletonJugadoresParaAprobar();
	      }
	      return instance;
	   }

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public List<Rechazado> getListaDeRechazados() {
		return listaDeRechazados;
	}

	public void setListaDeRechazados(List<Rechazado> listaDeRechazados) {
		this.listaDeRechazados = listaDeRechazados;
	}

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	

/*public void agregarJugador(Jugador jugadorPropuesto, Jugador jugadorProponedor, String mensaje) {
		
		if (aceptado = admin.aprobarORechazarNuevoJugador(jugadorPropuesto)){
			mensaje = null;
			jugadorProponedor.recibirRespuestaNuevoJugadorAceptado(mensaje,jugadorPropuesto, admin);
		}
		else{
			jugadorProponedor.recibirRespuestaNuevoJugadorRechazado(mensaje,jugadorPropuesto,jugadorProponedor, admin);
		}
		
	}*/

	public void recibirMensajeDerechazo(String mensaje) {

	}
	
	public void agregarNuevoJugadorAAprobar(Jugador jugador){
		admin.getListaDeJugadoresParaAprobar().add(jugador);
	}
	
}
