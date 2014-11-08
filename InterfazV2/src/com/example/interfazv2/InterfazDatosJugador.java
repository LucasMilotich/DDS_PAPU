package com.example.interfazv2;

import java.awt.Dimension;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazDatosJugador extends VerticalLayout implements View,
		InterfazVistas, ClickListener {
	ViewListener listener;

	public VerticalLayout InterfazDatosJugador() {
		// TODO Auto-generated constructor stub
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout layoutMain = new HorizontalLayout();

		VerticalLayout layoutDatos = new VerticalLayout();

		Label DNI = new Label("D.N.I.: ");
		Label nombre = new Label("Nombre: ");
		Label apellido = new Label("Apellido: ");
		Label nivelDeJuego = new Label("Nivel de juego: ");
		Label promedioUltimoPartido = new Label("P.U.P.");
		Label fechaNacimiento = new Label("Fecha nacimiento:");
		Label cantidadDePartidosJugadores = new Label(
				"Cantidad de partidos jugados: ");
		Label promedioPartidosTodos = new Label(
				"Promedio de los partidos jugados: ");
		Table penalizaciones = new Table("Penalizaciones");
		
		final ObjectProperty<Double> promUltPart = new ObjectProperty<Double>(
				0.0);
		final ObjectProperty<Double> promTotPart = new ObjectProperty<Double>(
				0.0);
		// final ObjectProperty<Date> fechaNac = new ObjectProperty<Date>(null);
		final ObjectProperty<Integer> cantPart = new ObjectProperty<Integer>(0);
		// Seteo

		DNI.setCaption("DNI: ");
		nombre.setCaption("Nombre: ");
		apellido.setCaption("Apellido: ");
		nivelDeJuego.setCaption("Nivel de juego: ");
		promedioUltimoPartido.setCaption("P.U.P.: ");
		fechaNacimiento.setCaption("Fecha de nacimiento: ");
		cantidadDePartidosJugadores
				.setCaption("Cantidad de partidos jugados: ");
		promedioPartidosTodos.setCaption("Promedio de los partidos jugados: ");

		DNI.setConverter(new StringToIntegerConverter());
		nivelDeJuego.setConverter(new StringToIntegerConverter());
		promedioUltimoPartido.setPropertyDataSource(promUltPart);
		// fechaNacimiento.setPropertyDataSource(fechaNac);
		cantidadDePartidosJugadores.setPropertyDataSource(cantPart);
		cantidadDePartidosJugadores
				.setConverter(new StringToIntegerConverter());
		promedioPartidosTodos.setPropertyDataSource(promTotPart);

		

		// Ordenamiento
		layoutMain.setSizeFull();
		layoutMain.setMargin(true);
		layoutMain.setSpacing(true);
		layoutDatos.setMargin(true);
		layoutDatos.setSpacing(true);
		layout.setMargin(true);
		layout.setSpacing(true);
		layoutDatos.addComponents(DNI, nombre, apellido, nivelDeJuego,
				promedioUltimoPartido, fechaNacimiento,
				cantidadDePartidosJugadores,promedioPartidosTodos);
		layoutMain.addComponents(layoutDatos, penalizaciones);
		layoutMain.setComponentAlignment(penalizaciones,
				ALIGNMENT_DEFAULT.MIDDLE_CENTER);
		layout.addComponents(layoutMain);

		addComponent(layout);

		// Logica

		DNI.setValue(listener.getJugador().getDNI().toString());
		nombre.setValue(listener.getJugador().getNombre());
		apellido.setValue(listener.getJugador().getApellido());
		promUltPart.setValue(listener.getJugador().getPromedioUltimoPartido());
		if (listener.getJugador().getNivelDeJuego() != null)
			nivelDeJuego.setValue(listener.getJugador().getNivelDeJuego()
					.toString());
		if (listener.getJugador().getFechaNacimiento() != null)
			fechaNacimiento.setValue(new SimpleDateFormat("dd-MM-yyyy").format(listener.getJugador().getFechaNacimiento()));
		cantPart.setValue(listener.obtenerCantidadDePartidosJugados(listener
				.getJugador()));
		promTotPart.setValue(listener
				.obtenerPromedioDeTodosLosPartidos(listener.getJugador()));
		listener.bindiarPenalizacionesATabla(
				listener.obtenerPenalizacionesDeUn(listener.getJugador()),
				penalizaciones);
		fechaNacimiento.setConverter(new StringToDateConverter() {

			public DateFormat getFormat(Locale locale) {

				return new SimpleDateFormat("dd-MM-yyyy");

			}

		});
		
		
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
