package com.example.interfazv2;

import Logica.Jugador;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import org.tepi.filtertable.FilterTable;
public class InterfazBusquedaYVisualizacion extends
		com.vaadin.ui.VerticalLayout implements View, InterfazVistas {
	ViewListener listener;

	@SuppressWarnings({ "serial", "deprecation", "unused" })
	public VerticalLayout InterfazBusquedaYVisualizacion() {
		// TODO Auto-generated constructor stub
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		VerticalLayout filtros = new VerticalLayout();
		HorizontalLayout busqueda = new HorizontalLayout();
		HorizontalLayout filtros1 = new HorizontalLayout();
		HorizontalLayout filtros2 = new HorizontalLayout();
		filtros1.setSpacing(true);
		filtros1.setMargin(true);
		filtros2.setSpacing(true);
		filtros2.setMargin(true);
		final Table jugadores = new Table();
		final Label DNI = new Label("D.N.I.: ");
		final Label nombre = new Label("Nombre: ");
		final Label apellido = new Label("Apellido: ");
		final Label nivelDeJuego = new Label("Handicap: ");
		final TextField filtroNombre = new TextField("Nombre");
		final TextField filtroApellido = new TextField("Apellido");
		final TextField filtroEdadDesde = new TextField("Edad desde");
		final TextField filtroEdadHasta = new TextField("Edad hasta");
		final TextField filtroHandicapDesde = new TextField("Handicap desde");
		final TextField filtroHandicapHasta = new TextField("Handicap hasta ");
	
		@SuppressWarnings("unused")
		Button verPartidosInscriptos = new Button("Ver partidos inscriptos");
		Button verAmigos = new Button("Ver amigos");

		final Window ventanaDatosJugador = new Window("Datos de jugador");
		final VerticalLayout layoutDatosSubWindow = new VerticalLayout();
		final HorizontalLayout layoutParaSubWindow = new HorizontalLayout();
		final Table infracciones = new Table("Infracciones ");
		ventanaDatosJugador.setWidth("50%");
		final Label cantidadPartidos = new Label(
				"Cantidad de partidos que jugó: ");
		final Label promedioPartidos = new Label(
				"Promedio de todos los partidos que jugó: ");
		final Label promedioUltimoPartido = new Label(
				"Promedio del último partido que jugó: ");
		ventanaDatosJugador.center();
		ventanaDatosJugador.setContent(layoutParaSubWindow);
		layoutParaSubWindow.setMargin(true);
		layoutParaSubWindow.setSpacing(true);
		layoutDatosSubWindow.setMargin(true);
		layoutDatosSubWindow.setSpacing(true);
		
		
		listener.bindiarTablaConJugadoresEnLista(jugadores, listener.getAdmin()
				.getListaDeJugadores());
		

		jugadores.setSelectable(true);

		VerticalLayout layoutDatos = new VerticalLayout();

		filtros1.addComponents(filtroNombre, filtroApellido, filtroEdadDesde,
				filtroEdadHasta);
		filtros2.addComponents(filtroHandicapDesde, filtroHandicapHasta);
		filtros.addComponents(filtros1, filtros2);

		DNI.setCaption("DNI: ");
		nombre.setCaption("Nombre: ");
		apellido.setCaption("Apellido: ");
		nivelDeJuego.setCaption("Nivel de juego: ");
		cantidadPartidos.setCaption("Cantidad de partidos que jugó: ");
		promedioPartidos
				.setCaption("Promedio de todos los partidos que jugó: ");
		promedioUltimoPartido
				.setCaption("Promedio del último partido que jugó: ");
		layoutDatos.setMargin(true);
		layoutDatos.setSpacing(true);
		final com.vaadin.data.util.ObjectProperty<Integer> edadDesde = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> edadHasta= new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> nivelDeJuegoDesde = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> nivelDeJuegoHasta = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		filtroEdadDesde.setPropertyDataSource(edadDesde);
		filtroEdadHasta.setPropertyDataSource(edadHasta);
		filtroHandicapDesde.setPropertyDataSource(nivelDeJuegoDesde);
		filtroHandicapHasta.setPropertyDataSource(nivelDeJuegoHasta);
		
		
		DNI.setConverter(new StringToIntegerConverter());
		promedioPartidos.setConverter(new StringToIntegerConverter());
		promedioUltimoPartido.setConverter(new StringToDoubleConverter());
		nivelDeJuego.setConverter(new StringToDoubleConverter());
		cantidadPartidos.setConverter(new StringToIntegerConverter());
		jugadores.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event != null) {
					// TODO Auto-generated method stub
					listener.setJugador((Jugador) jugadores.getValue());
					DNI.setValue(listener.getJugador().getDNI().toString());
					nombre.setValue(listener.getJugador().getNombre());
					apellido.setValue(listener.getJugador().getApellido());
					nivelDeJuego.setValue(listener.getJugador()
							.getNivelDeJuego().toString());
					UI.getCurrent().addWindow(ventanaDatosJugador);
					listener.bindiarPenalizacionesATabla(listener
							.obtenerPenalizacionesDeUn(listener.getJugador()),
							infracciones);
					Double promUltPar = listener
							.obtenerPromedioUltimoPartido(listener.getJugador());
					Double promPart = listener
							.obtenerPromedioDeTodosLosPartidos(listener
									.getJugador());
					promedioUltimoPartido.setValue(promUltPar.toString());
					promedioPartidos.setValue(promPart.toString());
					Integer cantPart = listener
							.obtenerCantidadDePartidosJugados(listener
									.getJugador());
					cantidadPartidos.setValue(cantPart.toString());

				}
			}
		});
		
		
		
		
		jugadores.addGeneratedColumn("PromedioUltimoPartido",
				new ColumnGenerator() {

					@SuppressWarnings("unchecked")
					public Object generateCell(Table source, Object itemId,
							Object columnId) {
						// source.setValue(listener.obtenerPromedioUltimoPartido((Jugador)
						// source.getValue()));

						// Item item = source.getItem(itemId);

						BeanItem<Jugador> jugador = (BeanItem<Jugador>) source
								.getContainerDataSource().getItem(itemId);
						double i = listener
								.obtenerPromedioUltimoPartido(jugador.getBean());
						return i;
					}
				});
		jugadores.addGeneratedColumn("TuvoInfraciones", new ColumnGenerator() {

			@SuppressWarnings("unchecked")
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				// source.setValue(listener.obtenerPromedioUltimoPartido((Jugador)
				// source.getValue()));

				// Item item = source.getItem(itemId);

				BeanItem<Jugador> jugador = (BeanItem<Jugador>) source
						.getContainerDataSource().getItem(itemId);

				return listener.tuvoInfracciones(jugador.getBean());
			}
		});
		jugadores.setVisibleColumns(new String[] { "nombre", "apellido",
				"edad", "nivelDeJuego","TuvoInfraciones","PromedioUltimoPartido" });
		jugadores.setColumnHeaders(new String[] { " Nombre", "Apellido",
				"Edad", "Handicap","Tuvo infracciones", "P.U.P" });

		filtroNombre.addListener(new TextChangeListener() {
			SimpleStringFilter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);

				// Set new filter for the "Name" column
				filter = new SimpleStringFilter("nombre", event.getText(),
						true, false);
				f.addContainerFilter(filter);
			}
		});
		filtroApellido.addListener(new TextChangeListener() {
			SimpleStringFilter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);

				// Set new filter for the "Name" column
				filter = new SimpleStringFilter("apellido", event.getText(),
						true, false);
				f.addContainerFilter(filter);
			}
		});
		
		
		filtroEdadDesde.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int age = Integer.parseInt(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.GreaterOrEqual("edad", age);
						
						
						/*new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);*/
				f.addContainerFilter(filter);
			}
		});
		filtroEdadHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int age = Integer.parseInt(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.Less("edad", age);
						
						
						/*new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);*/
				f.addContainerFilter(filter);
			}
		});
		filtroHandicapDesde.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int handicap = Integer.parseInt(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.GreaterOrEqual("nivelDeJuego", handicap);
						
						
						/*new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);*/
				f.addContainerFilter(filter);
			}
		});
		
		filtroHandicapHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int handicap = Integer.parseInt(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.Less("nivelDeJuego", handicap);
						
						
						/*new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);*/
				f.addContainerFilter(filter);
			}
		});
		/*filtroUltimoDesde.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				double pup = Double.parseDouble(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.GreaterOrEqual("PromedioUltimoPartido", pup);
						
						
						new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);
				f.addContainerFilter(filter);
			}
		});
		filtroUltimoHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				double pup = Double.parseDouble(event.getText());
				// Set new filter for the "Name" column
				filter = 
				        new Compare.Less("PromedioUltimoPartido", pup);
						
						
						new SimpleStringFilter("TuvoInfraciones", event
						.getText(), true, false);
				f.addContainerFilter(filter);
			}
		});*/
		jugadores.setSortEnabled(true);
		

		layoutDatosSubWindow.addComponents(DNI, nombre, apellido, nivelDeJuego,
				promedioPartidos, promedioUltimoPartido, cantidadPartidos);
		layoutParaSubWindow.addComponents(layoutDatosSubWindow, infracciones);
		layoutParaSubWindow.setComponentAlignment(infracciones,
				Alignment.MIDDLE_RIGHT);
		// layoutDatos.addComponents(DNI, nombre, apellido, nivelDeJuego);
		busqueda.addComponents(jugadores, layoutDatos);
		layout.addComponents(filtros, busqueda);
		addComponent(layout);
		return layout;
	}

	
	
	
	
	
	
	@Override
	public void setTextFieldInt(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTextFieldString(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ViewListener listener) {
		// TODO Auto-generated method stub
		this.listener = listener;

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
