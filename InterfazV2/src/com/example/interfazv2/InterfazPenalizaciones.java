package com.example.interfazv2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Logica.Jugador;
import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class InterfazPenalizaciones extends VerticalLayout implements View,InterfazVistas  {
	ViewListener listener ;
	
	@SuppressWarnings("serial")
	public VerticalLayout InterfazPenalizaciones(){
		VerticalLayout layout = new VerticalLayout();
		final Table partidos = new Table("Partidos disponibles para penalizar: ");
		final Table jugadoresDeUnPartido = new Table("Jugadores del partido seleccionado: ");
		Button penalizar = new Button("Penalizar");
		final Window panelPenalizacion = new Window("Nivel de juego");
		VerticalLayout subwindow = new VerticalLayout();
		final TextField motivo = new TextField("Motivo: ");
		Button confirmar = new Button("Confirmar");
		subwindow.addComponents(motivo,confirmar);
		subwindow.setMargin(true);
		subwindow.setSpacing(true);
		layout.setMargin(true);
		layout.setSpacing(true);
		panelPenalizacion.center();
		partidos.setSelectable(true);
		layout.setHeight("100%");
		partidos.setHeight("30%");
		jugadoresDeUnPartido.setHeight("40%");
		confirmar.setHeight("20%");
		jugadoresDeUnPartido.setSelectable(true);
		panelPenalizacion.setContent(subwindow);
		partidos.setImmediate(true);
		listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosEmpezados(), partidos, null);
		partidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });
		partidos.setConverter("fecha", new StringToDateConverter(){
			
			public DateFormat getFormat(Locale locale){

				return new SimpleDateFormat("dd-MM-YYYY");

			}

			});
		penalizar.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI();
				// TODO Auto-generated method stub
				UI.getCurrent().addWindow(panelPenalizacion);

			}
		});
		
		partidos.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				Partido partido =  (Partido) partidos.getValue();
				listener.bindiarTablaConJugadoresEnLista(jugadoresDeUnPartido, partido.getJugadoresSeleccionados() );
				jugadoresDeUnPartido.setVisibleColumns(new String[] {
						"nombre", "apellido", "edad", "nivelDeJuego" });
		
				
			}
		});
				
		
				
			
		
		
		confirmar.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				listener.crearPenalizacion(motivo.getValue(), (Jugador) jugadoresDeUnPartido.getValue(), (Partido) partidos.getValue());
				panelPenalizacion.close();
			}
		});
		
		layout.addComponents(partidos,jugadoresDeUnPartido,penalizar);
		layout.setExpandRatio(partidos, 4);
		layout.setExpandRatio(jugadoresDeUnPartido, 5);
		layout.setExpandRatio(penalizar, 1);	
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
		this.listener = listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
