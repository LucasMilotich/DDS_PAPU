package com.example.interfazv2;

import java.awt.Button;

import javax.sound.sampled.AudioFileFormat.Type;

import Logica.Jugador;
import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InterfazCalificarJugadores extends VerticalLayout implements View, InterfazVistas{

	ViewListener listener ;
	@SuppressWarnings("deprecation")
	public VerticalLayout InterfazCalificarJugadores(){
	VerticalLayout layout = new VerticalLayout();
	final ComboBox partidosJugados = new ComboBox("Partidos jugados");
	final Table jugadoresParacalificar = new Table("Jugadores para calificar");
	final ComboBox calificacionesDisponibles = new ComboBox("Calificaciones disponibles");
	com.vaadin.ui.Button calificar = new com.vaadin.ui.Button("Calificar");
	jugadoresParacalificar.setSelectable(true);
	setSizeFull();
	setMargin(true);
	addComponent(layout);
	layout.setMargin(true);
	layout.setSpacing(true);
	layout.addComponent(partidosJugados);
	layout.addComponent(jugadoresParacalificar);
	layout.addComponent(calificacionesDisponibles);
	layout.addComponent(calificar);
		
	
	

	listener.bindiarListaPartidoACombo(listener.obtenerPartidosJugados(listener.getJugador()), partidosJugados, null);
	partidosJugados.setItemCaptionPropertyId("nombre");
	partidosJugados.addListener(new Property.ValueChangeListener() {
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			
			listener.bindiarTablaConJugadoresEnLista(jugadoresParacalificar, listener.obtenerJugadoresDeUnPartidoParaCalificar((Partido) partidosJugados.getValue()));
			jugadoresParacalificar.setVisibleColumns(new String[] {
					"nombre", "apellido", "edad", "nivelDeJuego" });		
			
		}
	});
		
		
			
			
	
	
	listener.obtenerCalificacionesDisponiblesYBindearACombo(calificacionesDisponibles);
	
	
	calificar.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			if(jugadoresParacalificar.getValue() == null){
				Notification.show("No has seleccionado ningún jugador, por favor selecciona uno.", com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}else{
				listener.generarCalificacion((Jugador)jugadoresParacalificar.getValue(), (Integer)calificacionesDisponibles.getValue(), (Partido)partidosJugados.getValue());
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
		this.listener=listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
