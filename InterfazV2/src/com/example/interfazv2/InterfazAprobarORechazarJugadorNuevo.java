package com.example.interfazv2;

import java.sql.SQLException;

import Logica.Amigo;
import Logica.Jugador;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InterfazAprobarORechazarJugadorNuevo extends VerticalLayout implements View, InterfazVistas {
	ViewListener listener ;
	public VerticalLayout InterfazAprobarORechazarJugadorNuevo(){
VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout comboLayout = new HorizontalLayout();
		VerticalLayout botonera = new VerticalLayout();
		
		com.vaadin.ui.Label nombreCombo = new com.vaadin.ui.Label("Jugadores para aprobar");
		final ComboBox jugadorAAprobar =  new ComboBox();
		Button aprobar = new Button("Aprobar jugador");
		Button rechazar = new Button("Rechazar jugador");
		final TextField campoMotivo = new TextField("Motivo En Caso De Rechazo");
		
		
		addComponent(layout);
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		layout.addComponents(comboLayout,botonera,campoMotivo);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setExpandRatio(botonera, 10);
		layout.setExpandRatio(comboLayout, 1);
		layout.setExpandRatio(campoMotivo, 5);
		
		comboLayout.setSizeFull();
		
		
		//botonera.setSizeFull();
		botonera.setSpacing(true);
		
		botonera.addComponents(aprobar,rechazar,campoMotivo);
		aprobar.setWidth("20%");
		rechazar.setWidth("20%");
		campoMotivo.setWidth("20%");
		
		
		
		 listener.bindiarListaJugadoresParaAprobarACombo(listener.obtenerJugadoresParaAprobar(), jugadorAAprobar, null);
		 jugadorAAprobar.setItemCaptionPropertyId("nombre");
		comboLayout.addComponents(nombreCombo,jugadorAAprobar);
		comboLayout.setExpandRatio(nombreCombo, 1);
		comboLayout.setExpandRatio(jugadorAAprobar,9);
		
		
		
		aprobar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub	@Override
				if(jugadorAAprobar.getValue() == null){
					Notification.show("No has seleccionado a ningún jugador.",Type.ERROR_MESSAGE);
				}else{
					try {
						listener.aceptarONoJugadorNuevo((Jugador)jugadorAAprobar.getValue(), true, null);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		
		rechazar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub	@Override
				if(jugadorAAprobar.getValue() == null || campoMotivo.getValue() == null){
					Notification.show("No has seleccionado a ningún jugador o campo de motivo vacio.",Type.ERROR_MESSAGE);
				}else{
					try {
						listener.aceptarONoJugadorNuevo((Jugador)jugadorAAprobar.getValue(), false, campoMotivo.getValue());
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
