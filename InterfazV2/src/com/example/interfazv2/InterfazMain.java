package com.example.interfazv2;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class InterfazMain extends VerticalLayout implements View {

	public InterfazMain(){
		/*Declaracion de elementos de pantalla*/
		
		setSizeFull();
		final VerticalLayout layout = new VerticalLayout();
		
		Panel panel = new Panel("  Selección de tipo de usuario");
		FormLayout content = new FormLayout();

		NativeButton botonJugador = new NativeButton("Jugador");
		NativeButton botonAdmin = new NativeButton("Admin");
	
		/*Fin de declaracion de elementos*/
	
		/*Diseño*/
		layout.setSizeFull();
		layout.addComponent(panel);
		layout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		panel.setSizeUndefined();
		
		panel.setContent(content);
		content.addComponent(botonJugador);
		content.addComponent(botonAdmin);
		content.setComponentAlignment(botonAdmin, Alignment.MIDDLE_CENTER);
		content.setComponentAlignment(botonJugador, Alignment.MIDDLE_CENTER);
		content.setSizeUndefined();
		content.setMargin(true);
		panel.setWidth("300px");
		botonAdmin.setWidth("235px");
		botonJugador.setWidth("235px");
		botonJugador.setHeight("70px");
		botonAdmin.setHeight("70px");
		addComponent(layout);
		
		/*Fin Diseño*/
	
	
		/*Comportamiento*/
		
		botonJugador.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo("jugador");
				
			}
		});
botonAdmin.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo("admin");
				
			}
		});

	}
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
