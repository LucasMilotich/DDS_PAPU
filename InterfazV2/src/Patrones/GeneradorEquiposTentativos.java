package Patrones;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import Logica.*;

public abstract class GeneradorEquiposTentativos {
	
	Partido partido;
	String nombreMetodo;
	public String getNombreMetodo() {
		return nombreMetodo;
	}


	public void setNombreMetodo(String nombreMetodo) {
		this.nombreMetodo = nombreMetodo;
	}


	public void setPartido(Partido partido){
		this.partido = partido;
	}
	
	
	abstract public Map<Jugador, Double> generarListaTentativa(int n);
	
	abstract public void agregar(GeneradorEquiposTentativos c);
	
	abstract public void eliminar(GeneradorEquiposTentativos c);
}