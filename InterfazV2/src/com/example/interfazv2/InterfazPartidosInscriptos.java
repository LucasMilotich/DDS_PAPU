package com.example.interfazv2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class InterfazPartidosInscriptos extends VerticalLayout implements View, InterfazVistas {
	ViewListener listener ;
	public VerticalLayout InterfazPartidosInscriptos(){
		VerticalLayout layout = new VerticalLayout();
		Table listaDePartidos = new Table("Partidos inscriptos");
		HorizontalLayout botonera = new HorizontalLayout();		
		
		setSizeFull();
		setMargin(true);
		addComponent(layout);
		//layout.setSizeFull();
		layout.setSpacing(true);
		layout.setMargin(true);layout.setSpacing(true);
		layout.addComponent(listaDePartidos);
		layout.addComponent(botonera);
		botonera.setSizeFull();
		botonera.setMargin(true);
		
		listener.bindiarListaPartidoATabla(listener.obtenerPartidosInscriptos(), listaDePartidos, null);
		
		listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });
		
		listaDePartidos.setConverter("fecha", new StringToDateConverter(){

			public DateFormat getFormat(Locale locale){

			return new SimpleDateFormat("dd-MM-YYYY");

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
