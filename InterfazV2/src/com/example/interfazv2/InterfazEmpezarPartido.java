package com.example.interfazv2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazEmpezarPartido extends VerticalLayout implements View, InterfazVistas,ClickListener {
	ViewListener listener ;
	
	public VerticalLayout InterfazEmpezarPartido(){
		VerticalLayout layout = new VerticalLayout();
		final Table partidos = new Table("Partidos a cerrar: ");
		Button confirmar = new Button("Empezar");
		setSpacing(true);
		setMargin(true);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponents(partidos,confirmar);
		listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosConfirmados(), partidos, null);
		partidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });
		partidos.setSelectable(true);
		partidos.setConverter("fecha", new StringToDateConverter(){

			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd-MM-YYYY");

			}

			});
		confirmar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(partidos.getValue()==null){
					Notification.show("No ha seleccionado ningún partido, por favor seleccione alguno.",Type.ERROR_MESSAGE);
				}else{
					listener.empezarPartido((Partido) partidos.getValue());
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
		this.listener = listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
