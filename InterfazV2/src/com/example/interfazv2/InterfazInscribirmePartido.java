package com.example.interfazv2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.google.gwt.dev.asm.Label;
import com.google.gwt.view.client.SetSelectionModel;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazInscribirmePartido extends VerticalLayout implements View, InterfazVistas, ClickListener {

	List<ViewListener> listeners =  new ArrayList<ViewListener>();
	ViewListener listener ;
	@SuppressWarnings("deprecation")
	public VerticalLayout InterfazInscribirmePartido(){
		VerticalLayout layout = new VerticalLayout();
		
		HorizontalLayout comboLayout = new HorizontalLayout();
		VerticalLayout botonera = new VerticalLayout();
		
		com.vaadin.ui.Label nombreCombo = new com.vaadin.ui.Label("Partidos disponibles");
		final ComboBox partidosDisponibles =  new ComboBox();
		Button condicional = new Button("Inscribirme en forma condicional");
		Button solidaria = new Button("Inscribirme en forma solidaria");
		Button estandar = new Button("Inscribirme en forma estandar");
		
		addComponent(layout);
		setSizeFull();
		setMargin(true);
		setSpacing(true);
		layout.addComponents(comboLayout,botonera);
		layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setExpandRatio(botonera, 10);
		layout.setExpandRatio(comboLayout, 1);
		
		comboLayout.setSizeFull();
		
		
		//botonera.setSizeFull();
		botonera.setSpacing(true);
		
		botonera.addComponents(condicional,solidaria,estandar);
		condicional.setWidth("20%");
		solidaria.setWidth("20%");
		estandar.setWidth("20%");
		
		 listener.bindiarListaPartidoACombo(listener.buscarPartidos(), partidosDisponibles, null);
		partidosDisponibles.setItemCaptionPropertyId("nombre");
		comboLayout.addComponents(nombreCombo,partidosDisponibles);
		comboLayout.setExpandRatio(nombreCombo, 1);
		comboLayout.setExpandRatio(partidosDisponibles,9);
		
		condicional.addListener(new  ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(partidosDisponibles.getValue()==null){
					Notification.show("No ha seleccionado ningún partido, por favor seleccione uno.",Type.ERROR_MESSAGE);
				}else{
					try {
						listener.generarInscripcionCondicional(listener.getJugador(), (Partido) partidosDisponibles.getValue());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}				
			}
		});
		
		solidaria.addListener(new  ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(partidosDisponibles.getValue()==null){
					Notification.show("No ha seleccionado ningún partido, por favor seleccione uno.",Type.ERROR_MESSAGE);
				}else{
					try {
						listener.generarInscripcionSolidario(listener.getJugador(), (Partido) partidosDisponibles.getValue());
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		estandar.addListener(new  ClickListener() {
		
			@Override
			public void buttonClick(ClickEvent event) {
				if(partidosDisponibles.getValue()==null){
					Notification.show("No ha seleccionado ningún partido, por favor seleccione uno.",Type.ERROR_MESSAGE);
				}else{
					try {
						listener.generarInscripcionEstandar(listener.getJugador(), (Partido) partidosDisponibles.getValue());
					} catch (SQLException e) {
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

}
