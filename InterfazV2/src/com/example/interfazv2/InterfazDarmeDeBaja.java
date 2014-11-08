package com.example.interfazv2;

import java.awt.Button;
import java.sql.SQLException;

import Logica.Jugador;
import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class InterfazDarmeDeBaja extends VerticalLayout implements View,InterfazVistas, ClickListener {
	ViewListener listener ;
	public VerticalLayout InterfazDarmeDeBaja(){
		
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout comboLayout = new HorizontalLayout(); 
		com.vaadin.ui.Label nombreCombo = new com.vaadin.ui.Label("Partidos inscriptos: ");
		final ComboBox partidosInscriptos = new ComboBox();
		final ComboBox jugadoresDeReemplazo = new ComboBox("Jugador como reemplazo:");
		com.vaadin.ui.Button confirmar = new com.vaadin.ui.Button("Confirmar");
		setSizeFull();
		
		addComponent(layout);
		layout.setSizeFull();
		layout.setMargin(true);
		layout.addComponents(comboLayout,jugadoresDeReemplazo,confirmar);
		layout.setExpandRatio(comboLayout, 1);
		layout.setExpandRatio(jugadoresDeReemplazo, 1);
		layout.setExpandRatio(confirmar, 9);
		
		comboLayout.addComponents(nombreCombo,partidosInscriptos);
		comboLayout.setSpacing(true);
		listener.bindiarAmigosACombo(listener.obtenerAmigos(listener.getJugador()), jugadoresDeReemplazo);
		listener.bindiarListaPartidoACombo(listener.obtenerPartidosInscriptos(), partidosInscriptos, null);
		
		 partidosInscriptos.setItemCaptionPropertyId("nombre");
		 jugadoresDeReemplazo.setItemCaptionPropertyId("nombre");
		 
		 
		 confirmar.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(partidosInscriptos.getValue() == null){
						Notification.show("No ha seleccionado ningún partido, por favor seleccione uno.",Type.ERROR_MESSAGE);
					}else{
						try {
							listener.darDeBaja((Partido) partidosInscriptos.getValue(),(Jugador) jugadoresDeReemplazo.getValue());
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}					
				}
			});
		 
		return layout;
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
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
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	
	
}
