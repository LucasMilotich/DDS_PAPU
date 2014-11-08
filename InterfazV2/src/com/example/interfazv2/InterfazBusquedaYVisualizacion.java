package com.example.interfazv2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Logica.Jugador;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.util.filter.Compare.Less;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.data.util.filter.Compare.LessOrEqual;

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

		VerticalLayout layoutDatos = new VerticalLayout();
		VerticalLayout filtros = new VerticalLayout();
		HorizontalLayout busqueda = new HorizontalLayout();
		HorizontalLayout filtros1 = new HorizontalLayout();
		HorizontalLayout filtros2 = new HorizontalLayout();
		final Window vistaJugador = new Window("Datos de jugador");

		final Table jugadores = new Table();

		final TextField filtroNombre = new TextField("Nombre");
		final TextField filtroApellido = new TextField("Apellido");
		final TextField filtroEdadDesde = new TextField("Edad desde");
		final TextField filtroEdadHasta = new TextField("Edad hasta");
		final DateField filtroFechaNacHasta = new DateField(
				"Fecha de nacimiento hsata");
		final TextField filtroHandicapDesde = new TextField("Handicap desde");
		final TextField filtroHandicapHasta = new TextField("Handicap hasta ");
		final TextField filtroPromedioUltimoPartidoDesde = new TextField(
				"P.U.P. Desde");
		final TextField filtroPromedioUltimoPartidoHasta = new TextField(
				"P.U.P. Hasta");
		final TextField filtroTuvoInfracciones = new TextField(
				"¿Tuvo infracciones?");

		final com.vaadin.data.util.ObjectProperty<Integer> edadDesde = new com.vaadin.data.util.ObjectProperty<Integer>(
				0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> edadHasta = new com.vaadin.data.util.ObjectProperty<Integer>(
				0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> nivelDeJuegoDesde = new com.vaadin.data.util.ObjectProperty<Integer>(
				0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> nivelDeJuegoHasta = new com.vaadin.data.util.ObjectProperty<Integer>(
				0, Integer.class);
		// final ObjectProperty<Date> fechaNac = new ObjectProperty<Date>(null);
		final com.vaadin.data.util.ObjectProperty<Double> PromedioUltimoPartidoDesde = new com.vaadin.data.util.ObjectProperty<Double>(
				0.0, Double.class);
		final com.vaadin.data.util.ObjectProperty<Double> PromedioUltimoPartidoHasta = new com.vaadin.data.util.ObjectProperty<Double>(
				0.0, Double.class);

		filtroEdadDesde.setPropertyDataSource(edadDesde);
		filtroEdadHasta.setPropertyDataSource(edadHasta);
		filtroHandicapDesde.setPropertyDataSource(nivelDeJuegoDesde);
		filtroHandicapHasta.setPropertyDataSource(nivelDeJuegoHasta);
		// filtroFechaNacHasta.setPropertyDataSource(fechaNac);
		filtroPromedioUltimoPartidoDesde
				.setPropertyDataSource(PromedioUltimoPartidoDesde);
		filtroPromedioUltimoPartidoHasta
				.setPropertyDataSource(PromedioUltimoPartidoHasta);

		listener.bindiarTablaConJugadoresEnLista(jugadores, listener.getAdmin()
				.getListaDeJugadores());

		jugadores.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event != null) {
					// TODO Auto-generated method stub
					listener.setJugador((Jugador) jugadores.getValue());
					final InterfazDatosJugador InterfazDatosJugador = new InterfazDatosJugador();
					InterfazDatosJugador.addListener(listener);
					listener.setVista(InterfazDatosJugador);
					vistaJugador.setContent(InterfazDatosJugador.InterfazDatosJugador());
					UI.getCurrent().addWindow(vistaJugador);

				}
			}
		});

		jugadores.setVisibleColumns(new String[] { "nombre", "apellido",
				"edad", "nivelDeJuego", "tuvoInfracciones",
				"promedioUltimoPartido" });
		jugadores.setColumnHeaders(new String[] { " Nombre", "Apellido",
				"Edad", "Handicap", "Tuvo infracciones", "P.U.P" });
		
	
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
				int age = 0;
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					age = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.GreaterOrEqual("edad", age);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadDesde.setValue("0");
			}
		});
		filtroEdadHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int age = 100;
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					age = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.Less("edad", age);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadHasta.setValue("0");
			}
		});
		filtroHandicapDesde.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					f.removeContainerFilter(filter);
					int handicap = 0;
					if (event.getText() != "")
						handicap = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.GreaterOrEqual("nivelDeJuego",
							handicap);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadHasta.setValue("0");
			}
		});
		filtroHandicapHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int handicap = 11;
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					handicap = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.Less("nivelDeJuego", handicap);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadHasta.setValue("0");
			}
		});
		filtroPromedioUltimoPartidoHasta.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int handicap = 11;
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					handicap = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.Less("promedioUltimoPartido", handicap);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadHasta.setValue("0");
			}
		});
		filtroPromedioUltimoPartidoDesde.addListener(new TextChangeListener() {
			Filter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);
				int handicap = 0;
				if (!(event.getText().equals("")
						|| event.getText().equals("null") || event.getText() == null)) {
					handicap = Integer.parseInt(event.getText());
					// Set new filter for the "Name" column
					filter = new Compare.GreaterOrEqual(
							"promedioUltimoPartido", handicap);

					/*
					 * new SimpleStringFilter("TuvoInfraciones", event
					 * .getText(), true, false);
					 */
					f.addContainerFilter(filter);
				} else
					filtroEdadHasta.setValue("0");
			}
		});
		filtroTuvoInfracciones.addListener(new TextChangeListener() {
			SimpleStringFilter filter = null;

			public void textChange(TextChangeEvent event) {
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);

				// Set new filter for the "Name" column
				filter = new SimpleStringFilter("tuvoInfracciones", event
						.getText(), true, false);
				f.addContainerFilter(filter);
			}
		});
		filtroFechaNacHasta.addValueChangeListener(new ValueChangeListener() {
			Filter filter = null;
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				Filterable f = (Filterable) jugadores.getContainerDataSource();

				// Remove old filter
				if (filter != null)
					f.removeContainerFilter(filter);

				// Set new filter for the "Name" column
				filtroFechaNacHasta.setDateFormat("dd-MM-yyyy");
				Date fecha =filtroFechaNacHasta.getValue();
				DateFormat format= new SimpleDateFormat("dd-MM-yyyy");
				String convertido = format.format(fecha);
				try {
					fecha = format.parse(convertido);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				filter = new LessOrEqual("fechaNacimiento", fecha);
				filtroFechaNacHasta.setDateFormat("dd-MM-yyyy");
				f.addContainerFilter(filter);
			
			}
		});;
		
		 jugadores.setCellStyleGenerator(new Table.CellStyleGenerator() {
		      public String getStyle(Object itemId, Object propertyId) {
		        if (propertyId == null) {
		          // Styling for row
		          Item item = jugadores.getItem(itemId);
		          Integer handicap = (Integer) item.getItemProperty("nivelDeJuego").getValue();
		          if (handicap>=8) {
		            return "highlight-green";
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
				return null;
			}});
		
		
		jugadores.setSortEnabled(true);
		vistaJugador.center();
		vistaJugador.setHeight("80%");
		vistaJugador.setWidth("70%");
		filtros1.setSpacing(true);
		filtros1.setMargin(true);
		filtros2.setSpacing(true);
		filtros2.setMargin(true);

		layoutDatos.setMargin(true);
		layoutDatos.setSpacing(true);

		// layoutDatos.addComponents(DNI, nombre, apellido, nivelDeJuego);
		jugadores.setSelectable(true);

		filtros1.addComponents(filtroNombre, filtroApellido, filtroEdadDesde,
				filtroEdadHasta,filtroTuvoInfracciones);
		filtros2.addComponents(filtroHandicapDesde, filtroHandicapHasta,
				filtroFechaNacHasta, filtroPromedioUltimoPartidoDesde,
				filtroPromedioUltimoPartidoHasta);
		filtros.addComponents(filtros1, filtros2);

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
