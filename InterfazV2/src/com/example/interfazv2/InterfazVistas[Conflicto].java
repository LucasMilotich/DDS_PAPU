package com.example.interfazv2;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import Logica.Administrador;
import Logica.Amigo;
import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;
import Logica.Penalizacion;
import Patrones.GeneradorEquiposTentativos;

public interface InterfazVistas1 {
	public void setTextFieldInt(int value);
	public void setTextFieldString(String value);
	interface ViewListener {
		public void setJugador(Jugador jugador);
        void darDeAltaJugador(String operacion,Integer DNI,String apellido ,String nombre, int edad) throws SQLException;
        List<Partido> buscarPartidos();
        void inscribirAPartido(Partido partido, Inscripcion inscripcion) throws SQLException;
        void generarInscripcionEstandar(Jugador jugador, Partido partido) throws SQLException;
        void generarInscripcionCondicional(Jugador jugador, Partido partido) throws SQLException;
        void generarInscripcionSolidario(Jugador jugador, Partido partido) throws SQLException;
        ComboBox bindiarListaPartidoACombo(List<Partido> lista, ComboBox combo, String nombreCombo);
        ComboBox bindiarListaJugadoresParaAprobarACombo(List<Jugador> lista, ComboBox combo, String nombreCombo);
        Jugador getJugador();
        Administrador getAdmin();
        void setAdmin(Administrador admin);
        void crearPartido(Date datoFecha,String datoHora,String datoNombre,String datoLugar);
        List<Partido> obtenerPartidosInscriptos();
        void darDeBaja(Partido partido, Jugador reeemplazo) throws SQLException;
        void proponerNuevoJugador(String nombre, String apellido,int dni, int edad, Partido partido, Administrador admin,Jugador jugador);
        List<Jugador> obtenerJugadoresParaAprobar();
        Table bindiarTablaConJugadores(Table tabla);
		Table bindiarListaPartidoATabla(List<Partido> lista, Table table,
				String nombreCombo);
		void aceptarONoJugadorNuevo(Jugador jugador, boolean aprobado, String motivo);
		public void bindiarListaAmigosATabla(List<Amigo> lista, Table table,
				String nombreCombo);
		public List<Amigo> obtenerListaAmigos(Jugador jugador);
		public List<Jugador> obtenerListaDeJugadores();
		public void bindiarListaDeJugadoresACombo(List<Jugador> lista, ComboBox combo,
				String nombreCombo);
		public List<Partido> obtenerPartidosJugados(Jugador jugador);
		public 	Table bindiarTablaConJugadoresEnLista(Table tabla,List<Jugador> jugadores);
		public List<Jugador> obtenerJugadoresSeleccionadosDeUnPartido(Partido partido );
		public List<Integer> obtenerCalificacionesDisponiblesYBindearACombo(ComboBox combo);
		
		public void generarCalificacion(Jugador jugadorCalificado, Integer calificacion,Partido partido);
		public List<GeneradorEquiposTentativos>  obtenerListaDeMetodosDePonderacion();
		public ComboBox bindiarAComboListaGenerarEquiposTentativos(List<GeneradorEquiposTentativos> lista, ComboBox combo);
		public Jugador loguearseJugador(Integer DNI);
		public ComboBox bindiarAmigosACombo(List<Amigo> amigos, ComboBox combo);
		public List<Amigo> obtenerAmigos(Jugador jugador);
		public void cambiarNivelDeJuego(Jugador jugador,Integer nota);
		public void llamarMetodoPonderamiento(GeneradorEquiposTentativos strategia,Partido partido, Integer cantidadPartidos);
		public void confirmarUnPartido(Partido partido);
		public List<Jugador> obtenerJugadoresDeUnPartido(Partido partido);
		public void empezarPartido(Partido partido);
		public void terminarPartido(Partido partido);
		public List<Jugador> obtenerJugadoresDeUnPartidoParaCalificar(Partido partido);
		public void agregarAmigo(Jugador jugador);
		public List<Jugador> obtenerJugadoresNOAmigos(Jugador jugador);
		public void borrarAmigo(Jugador amigo);
		public void crearPenalizacion(String motivo, Jugador jugador,Partido partido);
		public double obtenerPromedioUltimoPartido(Jugador jugador);
		public String tuvoInfracciones(Jugador jugador);
		public void bindiarPenalizacionesATabla(List<Penalizacion> lista, Table tabla);
		public List<Penalizacion> obtenerPenalizacionesDeUn(Jugador jugador);
	}
	public void addListener(ViewListener listener);
}
