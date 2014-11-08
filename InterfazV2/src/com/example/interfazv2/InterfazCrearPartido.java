package com.example.interfazv2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;









import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InterfazCrearPartido extends VerticalLayout implements View, InterfazVistas,ClickListener {
	ViewListener listener ;
	public ViewListener getListener() {
		return listener;
	}

	public void setListener(ViewListener listener) {
		this.listener = listener;
	}

	String datoNombre= null;
	Date datoFecha= null;
	String datoHora= null;
	String datoLugar = null;
	
	public VerticalLayout crearPartidoInterfaz(){
	VerticalLayout layout = new VerticalLayout();
	final TextField campoNombre = new TextField("Nombre Partido:");
	final DateField   campoFecha = new DateField ("Fecha");
	final TextField campoHora = new TextField("Hora");
	final TextField campoLugar = new TextField("Lugar");
	final Button confirmar = new Button("Confirmar partido");
	
	layout.addComponents(campoNombre,campoFecha,campoHora,campoLugar,confirmar);
	addComponent(layout);
	setMargin(true);
	layout.setMargin(true);
	
	campoNombre.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				 datoNombre = (String) event.getProperty().getValue();
				
			}
		});
	campoFecha.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				Date date = new Date();
				if(campoFecha.getValue().before(date)){
					Notification.show("La fecha ingresada es anterior al dia actual",Type.ERROR_MESSAGE);
				}else{
					datoFecha = (Date) event.getProperty().getValue();
				}
			}
		});
	campoHora.addValueChangeListener(new Property.ValueChangeListener() {
		
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			 datoHora = (String) event.getProperty().getValue();
			
		}
	});
	campoLugar.addValueChangeListener(new Property.ValueChangeListener() {
		
		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			 datoLugar = (String) event.getProperty().getValue();
			
		}
	});
	
	confirmar.addClickListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			
			if(datoFecha != null && datoHora != null && datoNombre != null && datoLugar != null){
				listener.crearPartido(datoFecha, datoHora, datoNombre,datoLugar);
			}else{
				Notification.show("Faltan rellenar campos o los valores ingresados son inválidos, por favor verifique", Type.ERROR_MESSAGE);
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
		this.listener=listener;
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		listener.crearPartido(datoFecha, datoHora, datoNombre,datoLugar);
		
	}

}
