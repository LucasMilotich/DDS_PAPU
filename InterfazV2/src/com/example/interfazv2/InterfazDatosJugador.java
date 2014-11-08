package com.example.interfazv2;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazDatosJugador extends VerticalLayout implements View, InterfazVistas,ClickListener {
	ViewListener listener ;
	
	
	public VerticalLayout  InterfazDatosJugador() {
		// TODO Auto-generated constructor stub
		VerticalLayout layout = new VerticalLayout();
		Label DNI = new Label("D.N.I.: ");
		Label nombre = new Label("Nombre: ");
		Label apellido = new Label("Apellido: ");
		Label nivelDeJuego = new Label("Nivel de juego: ");
		
		Button verPartidosInscriptos = new Button("Ver partidos inscriptos");
		Button verAmigos = new Button("Ver amigos");
		DNI.setCaption("DNI: ");
		nombre.setCaption("Nombre: ");
		apellido.setCaption("Apellido: ");
		nivelDeJuego.setCaption("Nivel de juego: ");
		
		
		DNI.setConverter(new StringToIntegerConverter());
		nivelDeJuego.setConverter(new StringToIntegerConverter());
		DNI.setValue(listener.getJugador().getDNI().toString());
		nombre.setValue(listener.getJugador().getNombre());
		apellido.setValue(listener.getJugador().getApellido());
		if(listener.getJugador().getNivelDeJuego()!=null)
		nivelDeJuego.setValue(listener.getJugador().getNivelDeJuego().toString());
		
		
		
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponents(DNI,nombre,apellido,nivelDeJuego);
		addComponent(layout);
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
