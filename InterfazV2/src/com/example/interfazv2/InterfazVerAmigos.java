package com.example.interfazv2;

import Logica.Jugador;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class InterfazVerAmigos extends VerticalLayout implements View, InterfazVistas {
	ViewListener listener ;
	public VerticalLayout InterfazVerAmigos(){
		VerticalLayout layout = new VerticalLayout();
		final Table listaDeAmigos = new Table("Lista de amigos");
		Button borrarAmigo = new Button("Borrar como amigo");
		final Table amigosParaAgregar = new Table("Amigos para agregar");
		Button agregarAmigo = new Button("Agregar amigo");
		
		addComponent(layout);
		setSizeFull();setSpacing(true);setMargin(true);
		//layout.setSizeFull();
		layout.setSpacing(true);layout.setMargin(true);
		
		listener.bindiarListaAmigosATabla(listener.obtenerListaAmigos(listener.getJugador()), listaDeAmigos, null);
		listaDeAmigos.setVisibleColumns(new String[] {
				"nombre", "apellido", "edad", "nivelDeJuego" });

		
	//	listener.bindiarTablaConJugadoresEnLista(amigosParaAgregar, (listener.obtenerListaDeJugadores()));
		listener.bindiarTablaConJugadoresEnLista(amigosParaAgregar, listener.obtenerJugadoresNOAmigos(listener.getJugador()));
		amigosParaAgregar.setVisibleColumns(new String[] {
				"nombre", "apellido", "edad", "nivelDeJuego" });
		
		listaDeAmigos.setSelectable(true);
		amigosParaAgregar.setSelectable(true);
		
		layout.addComponents(listaDeAmigos,borrarAmigo,amigosParaAgregar,agregarAmigo);
			
		borrarAmigo.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				if(listaDeAmigos.getValue() == null){
					Notification.show("No has seleccionado ningún amigo para borrar",Type.ERROR_MESSAGE);
				}else{
					listener.borrarAmigo((Jugador) listaDeAmigos.getValue());
					listener.bindiarListaAmigosATabla(listener.obtenerListaAmigos(listener.getJugador()), listaDeAmigos, null);
					listaDeAmigos.setVisibleColumns(new String[] {
							"nombre", "apellido", "edad", "nivelDeJuego" });
				}
			}
		});	
		
		agregarAmigo.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if(amigosParaAgregar.getValue() == null){
					Notification.show("No has seleccionado ningún amigo para agregar",Type.ERROR_MESSAGE);
				}else{
					
					listener.agregarAmigo((Jugador) amigosParaAgregar.getValue());
					listener.bindiarListaAmigosATabla(listener.obtenerListaAmigos(listener.getJugador()), listaDeAmigos, null);
					listaDeAmigos.setVisibleColumns(new String[] {
							"nombre", "apellido", "edad", "nivelDeJuego" });
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
