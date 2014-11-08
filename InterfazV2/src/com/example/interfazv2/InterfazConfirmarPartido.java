package com.example.interfazv2;

import java.awt.Button;

import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazConfirmarPartido extends VerticalLayout implements View, InterfazVistas,ClickListener {
	ViewListener listener ;
	
	
	 public VerticalLayout InterfazConfirmarPartido() {
		// TODO Auto-generated constructor stub
		VerticalLayout layout = new VerticalLayout();
		final ComboBox partidosAbiertos = new ComboBox("Partidos abiertos: ");
		com.vaadin.ui.Button confirmar = new com.vaadin.ui.Button("Confirmar");
		
		listener.bindiarListaPartidoACombo(listener.getAdmin().getListaPartidos(), partidosAbiertos, null);
		partidosAbiertos.setItemCaptionPropertyId("nombre");
		layout.addComponent(partidosAbiertos);
		layout.addComponent(confirmar);
		layout.setMargin(true);
		layout.setSpacing(true);
		
		addComponent(layout);
		
		confirmar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(partidosAbiertos.getValue()==null){
					Notification.show("No ha seleccionado ningún Partido, por favor seleccione uno.",Type.ERROR_MESSAGE);
				}else{
					listener.confirmarUnPartido((Partido)partidosAbiertos.getValue());
				}
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
		this.listener=listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
