package Logica;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Rechazado extends Jugador implements Serializable {

int id;	

String motivo;



//Jugador jugadorPropuesto;


Jugador jugadorProponedor;

Date fecha;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getMotivo() {
	return motivo;
}



public Jugador getJugadorProponedor() {
	return jugadorProponedor;
}

public Date getFecha() {
	return fecha;
}

public void setFecha(Date fecha){
	this.fecha = fecha;
}


public void setJugadorProponedor(Jugador jugadorProponedor){
	this.jugadorProponedor = jugadorProponedor;
}

public void setMotivo(String motivo){
	this.motivo = motivo;
}

}
