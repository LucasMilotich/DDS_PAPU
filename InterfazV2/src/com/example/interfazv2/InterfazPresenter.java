package com.example.interfazv2;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.ComboBoxEditor;

import org.tepi.filtertable.FilterTable;

import com.vaadin.addon.jpacontainer.EntityContainer;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.navigator.View;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import BD.BDAdministrador;
import BD.BDAmigo;
import BD.BDConnection;
import BD.BDInscripciones;
import BD.BDJugador;
import BD.BDPenalizaciones;
import Logica.Administrador;
import Logica.Amigo;
import Logica.Calificacion;
import Logica.Equipo;
import Logica.Inscripcion;
import Logica.InscripcionCondicional;
import Logica.InscripcionEstandar;
import Logica.InscripcionSolidaria;
import Logica.Jugador;
import Logica.Partido;
import Logica.Penalizacion;
import Logica.Rechazado;
import Patrones.GeneradorEquiposTentativos;
import Patrones.Handicap;
import Patrones.Mix;
import Patrones.PromedioNPartidos;
import Patrones.SingletonJugadoresParaAprobar;
import Patrones.promedioUtilmoPartido;

public class InterfazPresenter implements InterfazVistas.ViewListener {

	InterfazDarmeDeAlta vistaDarmeDeAlta;
	InterfazInscribirmePartido vistaInscribirmePartido;
	View vista;
	public View getVista() {
		return vista;
	}

	public void setVista(View vista) {
		this.vista = vista;
	}

	Jugador jugador;
	Inscripcion inscripcion;
	SingletonJugadoresParaAprobar singleton;
	Administrador admin;

	public InterfazPresenter(Jugador model, View view) {
		this.jugador = model;
		this.vista = view;
		// ((InterfazVistas) view).addListener(this);

	}

	public void darDeAltaJugador(String operacion, Integer DNI, String nombre,
			String Apellido, int edad, Date fechaNacimiento) throws SQLException {
		// TODO Auto-generated method stub
		// if(operacion == "Confirmar"){
		jugador = new Jugador();
		jugador.darDeAltaJugador(jugador, DNI, nombre, Apellido, edad, fechaNacimiento);
		this.setJugador(jugador);
		this.admin.getListaDeJugadores().add(jugador);
		// }
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	@Override
	public void inscribirAPartido(Partido partido, Inscripcion inscripcion)
			throws SQLException {
		// TODO Auto-generated method stub
		jugador.inscribirseAUn(partido, inscripcion, admin);
		if (inscripcion.isInscripto() == false) {
			Notification.show("Hubo un error y usted no fue inscripto");
		}
	}

	@Override
	public void generarInscripcionEstandar(Jugador jugador, Partido partido)
			throws SQLException {
		// TODO Auto-generated method stub
		inscripcion = new InscripcionEstandar();
		// inscripcion.crearInscripcion(inscripcion, jugador, partido, false);
		// partido.inscribirA(jugador, inscripcion);

		// INS.addEntity((Inscripcion)inscripcion);
		jugador.inscribirseAUn(partido, inscripcion, admin);

	}

	@Override
	public void generarInscripcionCondicional(Jugador jugador, Partido partido)
			throws SQLException {
		// TODO Auto-generated method stub

		inscripcion = new InscripcionCondicional();
		// inscripcion.crearInscripcion(inscripcion, jugador, partido, false);

		// partido.inscribirA(jugador, inscripcion);

		jugador.inscribirseAUn(partido, inscripcion, admin);
	}

	@Override
	public void generarInscripcionSolidario(Jugador jugador, Partido partido)
			throws SQLException {

		inscripcion = new InscripcionSolidaria();
		// inscripcion.crearInscripcion(inscripcion, jugador, partido, false);

		jugador.inscribirseAUn(partido, inscripcion, admin);
	}

	@Override
	public List buscarPartidos() {
		// TODO Auto-generated method stub

		return this.admin.getListaPartidos();
	}

	@Override
	public ComboBox bindiarListaPartidoACombo(List<Partido> lista,
			ComboBox combo, String nombreCombo) {
		final BeanItemContainer<Partido> container = new BeanItemContainer<Partido>(
				Partido.class);
		Iterator<Partido> iterador = lista.iterator();
		while (iterador.hasNext()) {
			container.addItem(iterador.next());
		}

		combo.setContainerDataSource(container);
		return combo;
		// TODO Auto-generated method stub

	}	

	@Override
	public Jugador getJugador() {
		// TODO Auto-generated method stub
		return this.jugador;
	}

	@Override
	public void crearPartido(Date datoFecha, String datoHora,
			String datoNombre, String datoLugar) {

		this.admin.organizarPardido(datoNombre, datoFecha, datoHora, datoLugar);

	}

	@Override
	public Administrador getAdmin() {
		// TODO Auto-generated method stub
		return this.admin;
	}

	@Override
	public void setAdmin(Administrador admin) {
		// TODO Auto-generated method stub
		this.admin = admin;
	}

	@Override
	public List<Partido> obtenerPartidosInscriptos() {
		// TODO Auto-generated method stub
		return this.jugador.obtenerPartidosInscripto();
	}
	
	@Override
	public List<Partido> obtenerPartidosInscriptosParaMostrarBaja() {
		// TODO Auto-generated method stub
		return this.jugador.obtenerPartidosInscriptoParaMostrarBaja();
	}
	
	@Override
	public void darDeBaja(Partido partido, Jugador Reemplazo)
			throws SQLException {
		// TODO Auto-generated method stub

		
		if (Reemplazo != null) {
			Inscripcion inscripcion = null;
			Iterator<Inscripcion> iterator = this.getJugador()
					.getListaDeInscripciones().iterator();
			Inscripcion insAux;
			while (iterator.hasNext()) {
				insAux = iterator.next();
				if (insAux.getJugador() == this.getJugador()
						&& insAux.getPartido() == partido) {
					if (insAux instanceof InscripcionCondicional) {
						inscripcion = new InscripcionCondicional();
					}
					if (insAux instanceof InscripcionSolidaria) {
						inscripcion = new InscripcionSolidaria();
					}
					if (insAux instanceof InscripcionEstandar) {
						inscripcion = new InscripcionEstandar();
					}
				}

			}
			this.jugador.bajaDeUn(partido);
			Reemplazo.inscribirseAUn(partido, inscripcion, admin);
			Notification.show("Has sido dado de baja sin penalización");
		} else {
			Penalizacion penalizacion = new Penalizacion();
			penalizacion
					.setMotivo("Penalizado por baja sin proponer reemplazo");
			penalizacion.setJugador(jugador);
			penalizacion.setPartido(partido);
			BDPenalizaciones bd = new BDPenalizaciones();
			BDJugador bdJugador = new BDJugador();
			try {
				bd.getConnection();
				bd.agregarPenalizacion(penalizacion.getMotivo(), jugador, partido);
				bdJugador.getConnection();
				bdJugador.actualizarTuvoInfracciones(jugador.getDNI());
				jugador.getPenalizaciones().add(penalizacion);
				jugador.setTuvoInfracciones("SI");
				Notification.show("La penalización ha sido creada");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Notification.show("Hubo un error", Type.ERROR_MESSAGE);
			}
			
			
			this.jugador.bajaDeUn(partido);
			Notification.show("Has sido dado de baja y serás multado");
		}
	}

	@Override
	public void proponerNuevoJugador(String nombre, String apellido, int dni,
			int edad, Date fechaNacimiento, Partido partido, Administrador admin,Jugador jugador) {
		// // TODO Auto-generated method stub
		// this.jugador.proponerAmigo((Amigo)jugadorPropuesto, null, null);

		this.jugador.proponerJugador(nombre, apellido, dni, edad, fechaNacimiento, partido,
				admin);
	}

	@Override
	public List<Jugador> obtenerJugadoresParaAprobar() {
		// TODO Auto-generated method stub
		return this.admin.getListaDeJugadoresParaAprobar();

	}

	@Override
	public ComboBox bindiarListaJugadoresParaAprobarACombo(List<Jugador> lista,
			ComboBox combo, String nombreCombo) {
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Jugador> iterador = lista.iterator();
		while (iterador.hasNext()) {
			container.addItem(iterador.next());
		}
		combo.setContainerDataSource(container);
		return combo;

	}

	@Override
	public void aceptarONoJugadorNuevo(Jugador amigo, boolean aprobado,String motivo)
			throws SQLException {
		this.admin.aceptarONo(amigo, aprobado,motivo);
	}

	public Table bindiarTablaConJugadores(Table tabla) {
		// TODO Auto-generated method stub
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Jugador> iterador = this.admin.getListaDeJugadores()
				.iterator();
		while (iterador.hasNext()) {
			container.addItem(iterador.next());
		}
		tabla.setContainerDataSource(container);
		return tabla;
	}

	
	
	@Override
	public Table bindiarListaPartidoATabla(List<Partido> lista, Table table,
			String nombreCombo) {
		final BeanItemContainer<Partido> container = new BeanItemContainer<Partido>(
				Partido.class);
		Iterator<Partido> iterator = lista.iterator();
		while (iterator.hasNext()) {
			container.addItem(iterator.next());
		}
		if (container != null) {
			table.setContainerDataSource(container);
			return null;
		} else
			// TODO Auto-generated method stub
			return null;
	}

	public void bindiarListaAmigosATabla(List<Amigo> lista, Table table,
			String nombreCombo) {
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Amigo> iterator = lista.iterator();
		while (iterator.hasNext()) {
			container.addItem((Jugador) iterator.next().getAmigo());
		}
		table.setContainerDataSource(container);
	}

	public List<Amigo> obtenerListaAmigos(Jugador jugador) {
		List<Amigo> lista = new ArrayList<Amigo>();
		lista = jugador.getAmigos();

		return lista;

	}

	public List<Jugador> obtenerListaDeJugadores() {
		List<Jugador> lista = new ArrayList<Jugador>();
		this.admin.getListaDeJugadores();
		return lista;
	}

	public void bindiarListaDeJugadoresACombo(List<Jugador> lista,
			ComboBox combo, String nombreCombo) {
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Jugador> iterator = lista.iterator();
		while (iterator.hasNext()) {
			container.addItem((Jugador) iterator.next());
		}
		combo.setContainerDataSource(container);

	}

	public List<Partido> obtenerPartidosJugados(Jugador jugador) {
		List<Partido> partidos = new ArrayList<Partido>();
		Iterator<Inscripcion> iterador = jugador.getListaDeInscripciones()
				.iterator();
		Inscripcion inscripcion = null;
		Partido partido = null;
		while (iterador.hasNext()) {
			inscripcion = iterador.next();
			if (inscripcion.isInscripto() == true) {
				partido = inscripcion.getPartido();
				if (partido.isTerminado() == true) {
					partidos.add(partido);
				}
			}
		}

		return partidos;
	}

	public List<Jugador> obtenerJugadoresSeleccionadosDeUnPartido(
			Partido partido) {

		return partido.getJugadoresSeleccionados();

	}

	public List<Jugador> obtenerJugadoresDeUnPartido(Partido partido) {

		return partido.getListaDeJugadores();

	}

	public Table bindiarTablaConJugadoresEnLista(Table tabla,
			List<Jugador> jugadores) {
		// TODO Auto-generated method stub
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Jugador> iterador = jugadores.iterator();
		while (iterador.hasNext()) {
			container.addItem(iterador.next());
		}
		tabla.setContainerDataSource(container);
		return tabla;
	}


	public FilterTable bindiarFilterTablaConJugadoresEnLista(FilterTable tabla,
			List<Jugador> jugadores) {
		// TODO Auto-generated method stub
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Jugador> iterador = jugadores.iterator();
		while (iterador.hasNext()) {
			container.addItem(iterador.next());
		}
		tabla.setContainerDataSource(container);
		return tabla;
	}

	public List<Integer> obtenerCalificacionesDisponiblesYBindearACombo(
			ComboBox combo) {
		List<Integer> lista = new ArrayList<Integer>();
		final BeanItemContainer<Integer> container = new BeanItemContainer<Integer>(
				Integer.class);
		for (Integer i = 1; i < 11; ++i) {
			lista.add(i);
			container.addItem(i);
		}
		combo.setContainerDataSource(container);
		return lista;
	}

	public void generarCalificacion(Jugador jugadorCalificado,
			Integer calificacion, Partido partido) {
		Calificacion cali = new Calificacion();
		cali.calificarA(this.getJugador(), jugadorCalificado, calificacion,
				partido);
		partido.getListaCalificaciones().add(cali);
		Notification.show("Jugador calificado!");

	}

	public List<GeneradorEquiposTentativos> obtenerListaDeMetodosDePonderacion() {
		List<GeneradorEquiposTentativos> lista = new ArrayList<GeneradorEquiposTentativos>();

		lista.add(new Handicap());
		lista.add(new Mix());
		lista.add(new promedioUtilmoPartido());
		lista.add(new PromedioNPartidos());
		return lista;
	}

	public ComboBox bindiarAComboListaGenerarEquiposTentativos(
			List<GeneradorEquiposTentativos> lista, ComboBox combo) {
		final BeanItemContainer<GeneradorEquiposTentativos> container = new BeanItemContainer<GeneradorEquiposTentativos>(
				GeneradorEquiposTentativos.class);
		Iterator<GeneradorEquiposTentativos> iterator = lista.iterator();
		while (iterator.hasNext()) {
			container.addItem(iterator.next());
		}
		combo.setContainerDataSource(container);

		return combo;

	}

	public Jugador loguearseJugador(Integer DNI) {
		Jugador jugador = null;
		BDJugador bdJugador;
		try {
			bdJugador = new BDJugador();
			bdJugador.getConnection();
			jugador = bdJugador.obtenerJugador(DNI);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (jugador != null) {
			this.jugador = jugador;

		} else
			Notification.show("Jugador no existente");
		return jugador;
	}

	public ComboBox bindiarAmigosACombo(List<Amigo> amigos, ComboBox combo) {
		final BeanItemContainer<Jugador> container = new BeanItemContainer<Jugador>(
				Jugador.class);
		Iterator<Amigo> iterator = amigos.iterator();
		while (iterator.hasNext()) {

			container.addItem(iterator.next().getAmigo());
		}
		combo.setContainerDataSource(container);

		return combo;
	}

	public List<Amigo> obtenerAmigos(Jugador jugador) {

		return jugador.getListaDeAmigos();

	}

	public void cambiarNivelDeJuego(Jugador jugador, Integer nota) {
		jugador.setNivelDeJuegoBD(nota);
		
		Notification.show("Puntaje " + nota + " asignado al jugador "
				+ jugador.getNombre());
	}

	public void confirmarUnPartido(Partido partido) {
		
		//No son el mismo obj, no se borra...
		
		//borro de partidos abiertos
		Iterator<Partido> iteratorPartido = this.getAdmin().getListaPartidos().iterator();
		while(iteratorPartido.hasNext()){
			Partido partidoLista  = iteratorPartido.next();
			
			if(partidoLista.getFecha() == partido.getFecha() && partidoLista.getNombre() == partido.getNombre() && partidoLista.getLugar() == partido.getLugar()){
				this.getAdmin().getListaPartidos().remove(partidoLista);
				
			}
		}
	
		//borro de partidos cerrados
		iteratorPartido = this.getAdmin().getListaPartidosCerrados().iterator();
		while(iteratorPartido.hasNext()){
			Partido partidoLista  = iteratorPartido.next();
			
			if(partidoLista.getFecha() == partido.getFecha() && partidoLista.getNombre() == partido.getNombre() && partidoLista.getLugar() == partido.getLugar()){
				this.getAdmin().getListaPartidosCerrados().remove(partidoLista);
				
			}
		}
		
		this.getAdmin().getListaPartidosConfirmados().add(partido);
		partido.setConfirmado(true);
		partido.setCerrado(true);
		Notification.show("Partido confirmado");	

	}

	public void llamarMetodoPonderamiento(GeneradorEquiposTentativos strategia,
			Partido partido, Integer cantidadPartidos) {
if(cantidadPartidos != null){
		strategia.setPartido(partido);
		strategia.generarListaTentativa(cantidadPartidos);
} else Notification.show("Por favor complete la cantidad de partidos a evaluar. Si el método es handicap, ingrese 0",Type.ERROR_MESSAGE);

	}

	public void empezarPartido(Partido partido) {
		this.getAdmin().cambiarDeEstadoUnPartidoAEmpezado(partido);

	}

	public void terminarPartido(Partido partido) {
		this.getAdmin().cambiarDeEstadoUnPartidoATerminado(partido);

	}

	public List<Jugador> obtenerJugadoresDeUnPartidoParaCalificar(
			Partido partido) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		BDJugador bd = new BDJugador();
		try {
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jugadores = bd
				.obtenerJugadoresSeleccionadosSinListasDeUnPartido(partido);
		if (jugadores.remove(this.getJugador()))
			;
		else {
			Iterator<Jugador> iterator = jugadores.iterator();
			int id = 0;
			for (int i = 0; iterator.hasNext(); ++i) {
				if (iterator.next().getDNI() == this.getJugador().getDNI())
					id = i;
			}
			jugadores.remove(id);
		}

		return jugadores;

	}

	public void agregarAmigo(Jugador jugador) {
		int amigoDni= jugador.getDNI();
		int jugadorDNI = this.getJugador().getDNI();
if(amigoDni != jugadorDNI){
		BDAmigo bdAmigo = new BDAmigo();
		try {
			bdAmigo.getConnection();
			Amigo amigo = new Amigo();

			amigo.setJugador(this.getJugador());
			amigo.setAmigo(jugador);
			this.getJugador().getAmigos().add(amigo);
			bdAmigo.agregarAmigo(this.getJugador(), jugador);
			Notification.show("Amigo agregado");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}else Notification.show("No puedes ser tu propio amigo",Type.ERROR_MESSAGE);

	}

	public List<Jugador> obtenerJugadoresNOAmigos(Jugador jugador) {
		List<Jugador> jugadores = new ArrayList<Jugador>();

		Iterator<Jugador> jugadoresGlobal = this.getAdmin()
				.getListaDeJugadores().iterator();

		while (jugadoresGlobal.hasNext()) {
			Jugador jugadorGlobal = jugadoresGlobal.next();
			if (!this.chequearSiEsta(jugador.getAmigos(),
					jugadorGlobal.getDNI())
					&& jugadorGlobal != jugador) {
				jugadores.add(jugadorGlobal);
			}
		}
		return jugadores;
	}

	public void borrarAmigo(Jugador amigo) {

		BDAmigo bd = new BDAmigo();
		try {
			bd.getConnection();
			bd.borrarAmigo(this.getJugador(), amigo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Notification.show("Amigo borrado");
	}

	public boolean chequearSiEsta(List<Amigo> amigos, int DNI) {
		Iterator<Amigo> iteratorAmigos = amigos.iterator();
		boolean resultado = false;
		while (iteratorAmigos.hasNext()) {
			Jugador amigo = iteratorAmigos.next().getAmigo();
			if(amigo != null){
			if (DNI == amigo.getDNI())
				resultado = true;
			break;
			}
		}
		return resultado;
	}

	public void crearPenalizacion(String motivo, Jugador jugador,
			Partido partido) {

		BDPenalizaciones bd = new BDPenalizaciones();
		BDJugador bdJugador = new BDJugador();
		try {
			bd.getConnection();
			bd.agregarPenalizacion(motivo, jugador, partido);
			bdJugador.getConnection();
			bdJugador.actualizarTuvoInfracciones(jugador.getDNI());
			jugador.setTuvoInfracciones("SI");
			Notification.show("La penalización ha sido creada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show("Hubo un error", Type.ERROR_MESSAGE);
		}

	}

	public double obtenerPromedioUltimoPartido(Jugador jugador) {

		return jugador.obtenerPromedioUltimoPartido();
	}

	public double obtenerPromedioDeTodosLosPartidos(Jugador jugador) {

		return jugador.obtenerPromedioDeTodosLosPartidos();
	}

	
	
	
	public String tuvoInfracciones(Jugador jugador) {
		String siONo = null;
		BDPenalizaciones bd = new BDPenalizaciones();
		try {
			bd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (bd.tuvoInfracciones(jugador))
			siONo = "SI";
		else siONo = "NO";
		return siONo;
	}

	public List<Penalizacion> obtenerPenalizacionesDeUn(Jugador jugador) {
		List<Penalizacion> penalizaciones = new ArrayList<Penalizacion>();
		BDPenalizaciones bdPenalizaciones = new BDPenalizaciones();
		try {
			bdPenalizaciones.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		penalizaciones = bdPenalizaciones.obtenerPenalizacionesDeUn(jugador);

		return penalizaciones;

	}
	public void bindiarPenalizacionesATabla(List<Penalizacion> lista, Table tabla){
		final BeanItemContainer<Penalizacion> container = new BeanItemContainer<Penalizacion>(
				Penalizacion.class);
		Iterator<Penalizacion> iterador = lista.iterator();
		
		Penalizacion penalizacion = null;
		while (iterador.hasNext()) {
			penalizacion = iterador.next();
			BeanItem<Penalizacion> bean = container.addItem(penalizacion);
		//	container.addNestedContainerProperty("nombrePartido");
			
			container.addNestedContainerProperty("partido.nombre");
			container.addNestedContainerProperty("partido.fecha");
			
			bean.getItemProperty("partido.nombre").setValue(penalizacion.getPartido().getNombre());
			bean.getItemProperty("partido.fecha").setValue(penalizacion.getPartido().getFecha());
			
			

		tabla.setContainerDataSource(container);
		tabla.setVisibleColumns(new String[] { "motivo", "partido.nombre","partido.fecha"
		 });
		tabla.setConverter("partido.fecha", new StringToDateConverter(){

			public DateFormat getFormat(Locale locale){

			return new SimpleDateFormat("dd-MM-YYYY");

			}

			});
	}
		if(lista.size()==0){
			
			tabla.removeAllItems();
		}
		
	}
	public int obtenerCantidadDePartidosJugados(Jugador jugador){
		Iterator<Inscripcion> iterator = jugador.getListaDeInscripciones().iterator();
		int i = 0;
		while(iterator.hasNext()){
			if(iterator.next().isInscripto())
				++i;
		}
		return i;
	}
	
	public void obtenerRechazosYBindiarDeUnPartido(Table tabla,Partido partido){
		final BeanItemContainer<Rechazado> container = new BeanItemContainer<Rechazado>(
				Rechazado.class);
		BDAdministrador bd = null;
		try {
			bd =  new BDAdministrador();
			bd.getConnection();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//container.addNestedContainerProperty("jugadorPropuesto.nombre");
		//container.addNestedContainerProperty("jugadorPropuesto.apellido");
		container.addNestedContainerProperty("jugadorProponedor.nombre");
		container.addNestedContainerProperty("jugadorProponedor.apellido");
		
		container.addAll(bd.getJugadoresRechazados());
		tabla.setContainerDataSource(container);
	}

		public void bindiarEquiposATabla(List<Equipo> equipos,Table tablaA,Table tableB){
			
		final BeanItemContainer<Jugador> containerA = new BeanItemContainer<Jugador>(
				Jugador.class);
		final BeanItemContainer<Jugador> containerB = new BeanItemContainer<Jugador>(
				Jugador.class);
		if(equipos!= null){
			Equipo equipoA = equipos.get(0);
			Iterator<Jugador> iteratorJugador = equipoA.getListaDeJugadores().iterator();
			while(iteratorJugador.hasNext()){
				containerA.addBean(iteratorJugador.next());
			}
			Equipo equipoB = equipos.get(1);
			iteratorJugador = equipoB.getListaDeJugadores().iterator();
			while(iteratorJugador.hasNext()){
				containerB.addBean(iteratorJugador.next());
			}
		tablaA.setContainerDataSource(containerA);
		tableB.setContainerDataSource(containerB);
		}
		else {tablaA.setContainerDataSource(containerA);
		tableB.setContainerDataSource(containerB);
		}
	}
		public void agregarEfectosColores(final Table tabla){
			tabla.setCellStyleGenerator(new Table.CellStyleGenerator() {
					public String getStyle(Object itemId, Object propertyId) {
			        if (propertyId == null) {
			          // Styling for row
			          Item item = tabla.getItem(itemId);
			          Integer handicap = (Integer) item.getItemProperty("nivelDeJuego").getValue();
			          if (handicap>=8) {
			            return "highlight-blue";
			          } 
			            else {
			          // styling for column propertyId
			          return null;
			        }
			      }
					return null;

				
			    }

					@Override
					public String getStyle(Table source, Object itemId,
							Object propertyId) {
						// TODO Auto-generated method stub
						 if (propertyId == null) {
					          // Styling for row
					          Item item = tabla.getItem(itemId);
					          Integer handicap = (Integer) item.getItemProperty("nivelDeJuego").getValue();
					          if (handicap>=8) {
					            return "highlight-blue";
					          } 
					            else {
					          // styling for column propertyId
					          return null;
					        }
					      }
							return null;
					}

			});
		}
		
		public void bindiarListaInscripcionesATabla(List<Inscripcion> lista, Table tabla){
			final BeanItemContainer<Inscripcion> container = new BeanItemContainer<Inscripcion>(
					Inscripcion.class);
			Iterator<Inscripcion> iterator = lista.iterator();
			while (iterator.hasNext()) {
				container.addItem(iterator.next());
			}
			container.addNestedContainerProperty("jugador.nombre");
			container.addNestedContainerProperty("jugador.apellido");
			tabla.setContainerDataSource(container);

			
		}
		
		
		public List<Jugador> obtenerJugadoresPreSeleccionados(Partido partido){
			 
			List<Inscripcion> listaInscripcionesAceptadas = new ArrayList<Inscripcion>();
			List<Jugador> listaJugadoresPreSeleccionados = new ArrayList<Jugador>();
			
			BDInscripciones bdInscripcion = new BDInscripciones();
			try {
				bdInscripcion.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			listaInscripcionesAceptadas = bdInscripcion.obtenerInscripcionesAceptadas(partido);
			
			Iterator<Inscripcion> iterator = listaInscripcionesAceptadas.iterator();
			Inscripcion inscrip = null;
			while(iterator.hasNext()){
				inscrip = iterator.next();
				listaJugadoresPreSeleccionados.add(inscrip.getJugador());
				partido.getListaJugadoresSeleccionados().add(inscrip.getJugador());
			}
			
			
			return listaJugadoresPreSeleccionados;
		}
		
		
		
}
