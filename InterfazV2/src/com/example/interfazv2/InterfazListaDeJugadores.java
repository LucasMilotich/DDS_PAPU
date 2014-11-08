package com.example.interfazv2;

import java.sql.SQLException;

import Logica.Jugador;
import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InterfazListaDeJugadores extends VerticalLayout implements View,
		InterfazVistas {
	ViewListener listener;

	public VerticalLayout InterfazListaDeJugadores() {
		VerticalLayout layout = new VerticalLayout();
		final Table tablaDeJugadores = new Table("Jugadores en el sistema");
		final ComboBox partidosDisponibles = new ComboBox(
				"Partidos disponibles para inscribir jugadores");
		HorizontalLayout inscripcionLayout = new HorizontalLayout();
		Button condicional = new Button("Inscribirme en forma condicional");
		Button solidaria = new Button("Inscribirme en forma solidaria");
		Button estandar = new Button("Inscribirme en forma estandar");
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		layout.setSpacing(true);
		inscripcionLayout.setSpacing(true);
		addComponent(layout);
		layout.setMargin(true);
		tablaDeJugadores.setSizeFull();
		tablaDeJugadores.setSelectable(true);

		listener.bindiarTablaConJugadores(tablaDeJugadores);
		tablaDeJugadores.setVisibleColumns(new String[] { "nombre", "edad" });
		layout.addComponent(tablaDeJugadores);
		inscripcionLayout.addComponents(condicional, solidaria, estandar);
		layout.addComponents(partidosDisponibles, inscripcionLayout);
		partidosDisponibles.setItemCaptionPropertyId("nombre");

		listener.bindiarListaPartidoACombo(listener.getAdmin()
				.getListaPartidos(), partidosDisponibles, null);
		condicional.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					listener.generarInscripcionCondicional(
							(Jugador) tablaDeJugadores.getValue(),
							(Partido) partidosDisponibles.getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		solidaria.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					listener.generarInscripcionSolidario(
							(Jugador) tablaDeJugadores.getValue(),
							(Partido) partidosDisponibles.getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		estandar.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					listener.generarInscripcionEstandar(
							(Jugador) tablaDeJugadores.getValue(),
							(Partido) partidosDisponibles.getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
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
