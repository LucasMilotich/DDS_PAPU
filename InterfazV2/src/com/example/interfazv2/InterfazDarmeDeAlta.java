package com.example.interfazv2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Logica.Jugador;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class InterfazDarmeDeAlta extends VerticalLayout implements View, InterfazVistas, ClickListener{
	 List<ViewListener> listeners =  new ArrayList<ViewListener>();
		ViewListener listener ;
	@SuppressWarnings("serial")
	public VerticalLayout InterfazDarmeDeAlta(){
		
		final Panel panel = new Panel();
		
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		final TextField campoDNI = new TextField("D.N.I.");
		final TextField nombre = new TextField("Nombre");
		final TextField apellido = new TextField("Apellido");		
		final TextField campoEdad = new TextField("Edad");
		Button confirmar = new Button("Confirmar",this)	;
		final com.vaadin.data.util.ObjectProperty<Integer> edad = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> DNI = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		campoEdad.setPropertyDataSource(edad);
		campoDNI.setPropertyDataSource(DNI);
		layout.addComponents(campoDNI,nombre,apellido,campoEdad,confirmar);
		
		
		confirmar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
	            try {
					listener.darDeAltaJugador(event.getButton()
					                     .getCaption(),DNI.getValue(),
					                     nombre.getValue(),apellido.getValue(),edad.getValue());
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
					
				
				
			}
		});
		/*confirmar.addClickListener(new ClickListener() {
		
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//llamar a crearJugador
				
			}
		});*/
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
