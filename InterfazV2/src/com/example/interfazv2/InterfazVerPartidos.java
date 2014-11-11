package com.example.interfazv2;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Logica.Inscripcion;
import Logica.Jugador;
import Logica.Partido;

import com.example.interfazv2.InterfazDatosJugador;
import com.example.interfazv2.InterfazVistas;
import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Container.PropertySetChangeEvent;
import com.vaadin.data.Container.PropertySetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.sass.internal.parser.function.ListAppendFunctionGenerator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class InterfazVerPartidos extends VerticalLayout implements View, InterfazVistas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ViewListener listener ;
	public VerticalLayout InterfazVerPartidos(){
		VerticalLayout layout =new  VerticalLayout();
		HorizontalLayout layoutMain = new HorizontalLayout();
		final ComboBox tipoPartido = new ComboBox("Partidos por estado");
		
		final Table listaDePartidos = new Table("Lista de partidos");
		final Table listaDeInscripciones = new Table("Lista de jugadores");
		final Window vistaJugador = new Window("Datos de jugador");
		//Seteo
		tipoPartido.addContainerProperty("nombre", String.class, null);
		listaDeInscripciones.setSelectable(true);
		listaDePartidos.setSelectable(true);
		vistaJugador.center();
		vistaJugador.setHeight("80%");
		vistaJugador.setWidth("70%");
		layout.setSpacing(true);
		layout.setMargin(true);
		
		layoutMain.setMargin(true);
		layoutMain.setSpacing(true);
		layoutMain.setSizeFull();
		
		//Ordenamiento
		layoutMain.addComponents(listaDePartidos,listaDeInscripciones);
		layout.addComponents(tipoPartido,layoutMain);
		
		//Bindeos
		
		tipoPartido.addItem("Partidos abiertos");
		tipoPartido.addItem("Partidos cerrados");
		tipoPartido.addItem("Partidos confirmados");
		tipoPartido.addItem("Partidos empezados");
		tipoPartido.addItem("Partidos terminados");
		
		//Logica
			//Agrego los partidos segun el tipo
		tipoPartido.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				
					// TODO Auto-generated method stub
					if(tipoPartido.getValue().toString() == "Partidos abiertos"){
						listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidos(), listaDePartidos, null);
						listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });		
						listaDePartidos.setConverter("fecha", new StringToDateConverter(){

							public DateFormat getFormat(Locale locale){

							return new SimpleDateFormat("dd-MM-YYYY");

							}

							});
					}
					if(tipoPartido.getValue().toString() == "Partidos cerrados"){
						listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosCerrados(), listaDePartidos, null);
						listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });	
						listaDePartidos.setConverter("fecha", new StringToDateConverter(){

							public DateFormat getFormat(Locale locale){

							return new SimpleDateFormat("dd-MM-YYYY");

							}

							});
					}
					if(tipoPartido.getValue().toString() == "Partidos confirmados"){
						listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosConfirmados(), listaDePartidos, null);
						listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });	
						listaDePartidos.setConverter("fecha", new StringToDateConverter(){

							public DateFormat getFormat(Locale locale){

							return new SimpleDateFormat("dd-MM-YYYY");

							}

							});
					}
					if(tipoPartido.getValue().toString() == "Partidos empezados"){
						listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosEmpezados(), listaDePartidos, null);
						listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });
						listaDePartidos.setConverter("fecha", new StringToDateConverter(){

							public DateFormat getFormat(Locale locale){

							return new SimpleDateFormat("dd-MM-YYYY");

							}

							});
					}
					if(tipoPartido.getValue().toString() == "Partidos terminados"){
						listener.bindiarListaPartidoATabla(listener.getAdmin().getListaPartidosTerminados(), listaDePartidos, null);
						listaDePartidos.setVisibleColumns(new String[] { "nombre", "lugar","hora", "fecha" });	
						listaDePartidos.setConverter("fecha", new StringToDateConverter(){

							public DateFormat getFormat(Locale locale){

							return new SimpleDateFormat("dd-MM-YYYY");

							}

							});
					}
			}
		});
			
			
			//Busco inscripciones
		listaDePartidos.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				// TODO Auto-generated method stub
				listener.bindiarListaInscripcionesATabla(((BeanItem<Partido>) event.getItem()).getBean().getListaDeInscripciones(), listaDeInscripciones);
				listaDeInscripciones.addGeneratedColumn("inscripcionAceptada", new ColumnGenerator() {
					
					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {
						// TODO Auto-generated method stub
						if(((BeanItem<Inscripcion>)source.getContainerDataSource().getItem(itemId)).getBean().isInscripto())
						return "SI";
						else
							return "NO";
						
					}
				});
				listaDeInscripciones.setVisibleColumns(new String[]{"jugador.nombre","jugador.apellido","tipoInscripcion","inscripcionAceptada"});
				listaDeInscripciones.setColumnHeaders(new String[] {
						" Nombre", "Apellido", "Tipo de inscripción", "Inscripción aceptada" });
			}
		});
			//Busco dato del jugador de la inscripcion
		listaDeInscripciones.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				listener.setJugador(((BeanItem<Inscripcion>) event
						.getItem()).getBean().getJugador());
					
				final InterfazDatosJugador InterfazDatosJugador = new InterfazDatosJugador();
				InterfazDatosJugador.addListener(listener);
				listener.setVista(InterfazDatosJugador);
				vistaJugador.setContent(InterfazDatosJugador
						.InterfazDatosJugador());
				UI.getCurrent().addWindow(vistaJugador);
				
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
		this.listener= listener;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
