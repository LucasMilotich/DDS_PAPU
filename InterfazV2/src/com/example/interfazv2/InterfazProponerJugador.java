package com.example.interfazv2;

import java.util.Date;

import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazProponerJugador extends VerticalLayout implements View, InterfazVistas, ClickListener {
	ViewListener listener ;
	public VerticalLayout InterfazProponerJugador(){

		Date datoFecha= null;
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		final ComboBox partidosAnotados = new ComboBox("Partidos en que me anoté: ");
		listener.bindiarListaPartidoACombo(listener.getJugador().obtenerPartidosInscripto(), partidosAnotados, null);
		partidosAnotados.setItemCaptionPropertyId("nombre");
		
		TextField campoDNI = new TextField("D.N.I.");
		final TextField nombre = new TextField("Nombre");
		final TextField apellido = new TextField("Apellido");		
		TextField campoEdad = new TextField("Edad");
	
		final com.vaadin.data.util.ObjectProperty<Integer> edad = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		final com.vaadin.data.util.ObjectProperty<Integer> DNI = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		campoEdad.setPropertyDataSource(edad);
		campoDNI.setPropertyDataSource(DNI);
		final DateField campoFecha = new DateField ("Fecha De Nacimiento");
		Button confirmar = new Button("Confirmar nuevo jugador",this)	;
		layout.addComponents(partidosAnotados,campoDNI,nombre,apellido,campoEdad,campoFecha,confirmar);
		
		
		confirmar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub	@Override
				
				
			            listener.proponerNuevoJugador(nombre.getValue(),apellido.getValue(),
			            		DNI.getValue(),edad.getValue(),(Partido) partidosAnotados.getValue(),listener.getAdmin(),listener.getJugador());
					
				
				
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
