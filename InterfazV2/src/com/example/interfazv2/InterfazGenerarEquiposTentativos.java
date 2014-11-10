package com.example.interfazv2;

import java.util.Map;

import java_cup.internal_error;
import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;
import Patrones.GeneradorEquiposTentativos;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Property;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class InterfazGenerarEquiposTentativos extends VerticalLayout implements
		View, InterfazVistas, ClickListener {
	ViewListener listener;

	@SuppressWarnings({ "serial", "deprecation" })
	public VerticalLayout InterfazGenerarEquiposTentativos() {
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout panel = new HorizontalLayout();
		VerticalLayout panel1 = new VerticalLayout();
		VerticalLayout panel2 = new VerticalLayout();
		HorizontalLayout panel3 = new HorizontalLayout();
		HorizontalLayout panel4 = new HorizontalLayout();
		HorizontalLayout panel5 = new HorizontalLayout();
		Label IngreseNombreA = new Label("Nombre del primer equipo: ");
		Label IngreseNombreB = new Label("Nombre del segundo equipo: ");
		final TextField nombreA = new TextField();
		final TextField nombreB = new TextField();
		final ComboBox partidos = new ComboBox("Elegir partido");
		final Table listaDeJugadoresPorPartido = new Table(
				"Lista de jugadores de ese partido");
		final Table equipoA = new Table("Equipo A: ");
		final Table equipoB = new Table("Equipo B: ");
		final ComboBox metodoDePonderacion = new ComboBox(
				"Metodo de ponderación de jugadores");
		final ComboBox metodoDeOrdenamiento = new ComboBox(
				"Metodo de ordenamiento de jugadores");
		final Window vistaJugador = new Window("Datos de jugador");
		Button cambiarNivelDeJuego = new Button("Cambiar nivel de juego");
		final Window pantallaNivelJuego = new Window("Nivel de juego");
		VerticalLayout layoutPantallaNivelJuego = new VerticalLayout();
		final ComboBox nivelesDeJuego = new ComboBox("Niveles de juego");
		Button confirmarNivelDeJuego = new Button("Confirmar");
		final TextField numeroDePartidos = new TextField(
				"Número de partidos a considerar: ");
		
		
		Button confirmarEquipos = new Button("Confirmar equipos");
		
		layoutPantallaNivelJuego.addComponents(nivelesDeJuego,
				confirmarNivelDeJuego);
		layoutPantallaNivelJuego.setMargin(true);
		layoutPantallaNivelJuego.setSpacing(true);
		listener.obtenerCalificacionesDisponiblesYBindearACombo(nivelesDeJuego);
		pantallaNivelJuego.center();
		pantallaNivelJuego.setContent(layoutPantallaNivelJuego);

		panel5.addComponents(cambiarNivelDeJuego);
		setSizeFull();
		addComponent(layout);
		panel.setSizeFull();
		panel1.setSizeFull();
		panel2.setSizeFull();
		layout.addComponents(panel);
		panel.addComponents(panel1, panel2);
		panel.setExpandRatio(panel1, 5);
		panel.setExpandRatio(panel2, 5);
		panel.setComponentAlignment(panel2, Alignment.MIDDLE_RIGHT);
		panel2.setSpacing(true);
		panel2.setMargin(true);
		layout.setSpacing(true);
		layout.setMargin(true);
		panel1.setSpacing(true);
		panel1.setMargin(true);
		panel1.addComponents(partidos, listaDeJugadoresPorPartido, panel5,
				numeroDePartidos, metodoDePonderacion, IngreseNombreA, nombreA,
				IngreseNombreB, nombreB, metodoDeOrdenamiento);
		/*
		 * panel3.addComponents(); panel4.addComponents();
		 */
		panel2.addComponents(panel3, equipoA, panel4, equipoB, confirmarEquipos);
		vistaJugador.center();
		vistaJugador.setHeight("80%");
		vistaJugador.setWidth("70%");
		partidos.setItemCaptionPropertyId("nombre");
		listaDeJugadoresPorPartido.setSelectable(true);

		listener.bindiarListaPartidoACombo(listener.getAdmin().getListaPartidosParaGenerarEquipos(), partidos, null);

		// partidos.addValueChangeListener(new ValueChangeL)
		// listener.obtenerJugadoresDeUnPartido((Partido)partidos.getValue());

		partidos.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				listener.bindiarTablaConJugadoresEnLista(
						listaDeJugadoresPorPartido, listener.obtenerJugadoresPreSeleccionados((Partido) partidos
								.getValue()));

				listaDeJugadoresPorPartido.setVisibleColumns(new String[] {
						"nombre", "apellido", "edad", "nivelDeJuego" });

				listaDeJugadoresPorPartido.setColumnHeaders(new String[] {
						" Nombre", "Apellido", "Edad", "Nivel de juego" });
			}
		});
		listaDeJugadoresPorPartido
				.addItemClickListener(new ItemClickListener() {

					@Override
					public void itemClick(ItemClickEvent event) {
						// TODO Auto-generated method stub
						listener.setJugador(((BeanItem<Jugador>) event
								.getItem()).getBean());
						// listener.setJugador((Jugador)
						// listaDeJugadoresPorPartido.getValue());
						final InterfazDatosJugador InterfazDatosJugador = new InterfazDatosJugador();
						InterfazDatosJugador.addListener(listener);
						listener.setVista(InterfazDatosJugador);
						vistaJugador.setContent(InterfazDatosJugador
								.InterfazDatosJugador());
						UI.getCurrent().addWindow(vistaJugador);
					}
				});
		/*
		 * listaDeJugadoresPorPartido.addValueChangeListener(new
		 * ValueChangeListener() {
		 * 
		 * @Override public void valueChange(ValueChangeEvent event) { // TODO
		 * Auto-generated method stub
		 * 
		 * } });
		 */
		listener.bindiarAComboListaGenerarEquiposTentativos(
				listener.obtenerListaDeMetodosDePonderacion(),
				metodoDePonderacion);
		metodoDePonderacion.setItemCaptionPropertyId("nombreMetodo");

		cambiarNivelDeJuego.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI();
				// TODO Auto-generated method stub
				UI.getCurrent().addWindow(pantallaNivelJuego);

			}
		});

		confirmarNivelDeJuego.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				listener.cambiarNivelDeJuego(
						(Jugador) listaDeJugadoresPorPartido.getValue(),
						(Integer) nivelesDeJuego.getValue());
				listener.bindiarTablaConJugadoresEnLista(
						listaDeJugadoresPorPartido,
						listener.obtenerJugadoresSeleccionadosDeUnPartido((Partido) partidos
								.getValue()));
				listaDeJugadoresPorPartido.setVisibleColumns(new String[] {
						"nombre", "apellido", "edad", "nivelDeJuego" });

			}
		});
		nivelesDeJuego.setValidationVisible(true);

		metodoDePonderacion.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub

				numeroDePartidos.setConverter(new StringToIntegerConverter());
				if (numeroDePartidos.getValue().toString() != null
						&& (GeneradorEquiposTentativos) metodoDePonderacion
								.getValue() != null) {
					listener.llamarMetodoPonderamiento(
							(GeneradorEquiposTentativos) metodoDePonderacion
									.getValue(), (Partido) partidos.getValue(),
							(Integer) numeroDePartidos.getConvertedValue());
					listener.bindiarTablaConJugadoresEnLista(
							listaDeJugadoresPorPartido,
							listener.obtenerJugadoresSeleccionadosDeUnPartido((Partido) partidos
									.getValue()));
					listaDeJugadoresPorPartido.setVisibleColumns(new String[] {
							"nombre", "apellido", "edad", "nivelDeJuego" });

					listaDeJugadoresPorPartido.setColumnHeaders(new String[] {
							" Nombre", "Apellido", "Edad", "Nivel de juego" });
				} else
					Notification
							.show("Por favor complete la cantidad de partidos a evaluar. Si el método es handicap, ingrese 0",
									Type.ERROR_MESSAGE);

			}
		});
		metodoDeOrdenamiento.addContainerProperty("nombre", String.class, null);
		metodoDeOrdenamiento
				.addContainerProperty("codigo", Integer.class, null);
		metodoDeOrdenamiento.addItem("División par-impar");
		metodoDeOrdenamiento.addItem("División por posición");

		// metodoDeOrdenamiento.setItemCaptionPropertyId("codigo");

		metodoDeOrdenamiento.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (nombreA.getValue().toString() != null
						&& nombreB.getValue().toString() != null) {
					if (metodoDeOrdenamiento.getValue() == "División par-impar")
						listener.bindiarEquiposATabla(
								listener.getAdmin().dividirEquiposParImpar(
										(Partido) partidos.getValue(),
										nombreA.getValue(), nombreB.getValue()),
								equipoA, equipoB);
					if (metodoDeOrdenamiento.getValue() == "División por posición")
						listener.bindiarEquiposATabla(
								listener.getAdmin()
										.dividirEquiposSegundoCriterio(
												(Partido) partidos.getValue(),
												nombreA.getValue(),
												nombreB.getValue()), equipoA,
								equipoB);
					equipoA.setVisibleColumns(new String[] { "nombre",
							"apellido", "edad", "nivelDeJuego" });
					equipoB.setVisibleColumns(new String[] { "nombre",
							"apellido", "edad", "nivelDeJuego" });
				} else
					Notification.show("Ingrese los nombres de los equipos",
							Type.ERROR_MESSAGE);
			}
		});
		
		
		confirmarEquipos.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				listener.confirmarUnPartido((Partido) partidos.getValue());

			}
		});
		
		listener.agregarEfectosColores(listaDeJugadoresPorPartido);
		return layout;
	}
	

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

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
