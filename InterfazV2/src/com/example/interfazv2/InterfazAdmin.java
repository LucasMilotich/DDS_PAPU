package com.example.interfazv2;


import Logica.Administrador;
import Patrones.SingletonJugadoresParaAprobar;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;

public class InterfazAdmin extends VerticalLayout implements View,InterfazVistas {
	
	public  InterfazAdmin(){
	
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout menubar = new HorizontalLayout();
		VerticalLayout mainLayout = new VerticalLayout();
		MenuBar menu = new MenuBar();
		MenuItem adminMenu = menu.addItem("Admin", null, null);
		MenuItem jugadorMenu = menu.addItem("Jugadores", null, null);
		MenuItem partidoMenu= menu.addItem("Partido", null, null);
		MenuItem crearPartido = partidoMenu.addItem("Crear partido", null, null);
		MenuItem generarEquiposTentativos= adminMenu.addItem("Generar equipos tentativos", null, null);
	//	MenuItem confirmarEquipos = adminMenu.addItem("Confirmar Equipos", null, null);
		MenuItem aceptarORechazarJugador = adminMenu.addItem("Aceptar o rechazar jugador", null, null);
		MenuItem rechazos = adminMenu.addItem("Rechazos", null, null);
		MenuItem listaDeJugadores = adminMenu.addItem("Lista de jugadores", null, null);
		MenuItem confirmarPartido = partidoMenu.addItem("Confirmar partido", null,null);
		MenuItem empezarPartido = partidoMenu.addItem("Empezar partido", null, null);
		MenuItem penalizar = partidoMenu.addItem("Penalizar",null,null);
		MenuItem verPenalizaciones = adminMenu.addItem("Penalizar",null,null);
		MenuItem terminarPartido = partidoMenu.addItem("Terminar partido", null, null);
		MenuItem buscarJugadores = jugadorMenu.addItem("Buscar jugadores", null,null);
		MenuItem verPartidos = partidoMenu.addItem("Ver todos los partidos",null,null);
		final Panel panel = new Panel();
	//	final InterfazCrearPartido creadorPartido = new InterfazCrearPartido();
		com.vaadin.ui.Button atras = new com.vaadin.ui.Button("Atras");
		
	
		
		
		/*Diseño*/
		setSizeFull();
		addComponent(layout);
		layout.setSizeFull();
		panel.setSizeFull();
		
		layout.addComponent(menubar); 
		menubar.addComponent(menu);
		
		layout.addComponent(mainLayout);
		mainLayout.addComponents(panel,atras);
		atras.setSizeFull();
		menubar.setSizeFull();
		menu.setSizeFull();
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(panel, 4);
		mainLayout.setExpandRatio(atras, 0.15f);
	
		layout.setExpandRatio(menubar,0.15f);
		layout.setExpandRatio(mainLayout,4);
		final InterfazPresenter interfazPresenter =SingletonJugadoresParaAprobar.getInstance().getPresenter();
		final InterfazCrearPartido interfazCrearPartido = new InterfazCrearPartido();
		final InterfazAprobarORechazarJugadorNuevo InterfazAprobarORechazarJugadorNuevo = new InterfazAprobarORechazarJugadorNuevo();
		final InterfazListaDeJugadores interfazListaDeJugadores = new InterfazListaDeJugadores();
		final InterfazGenerarEquiposTentativos interfazGenerarEquiposTentativos = new InterfazGenerarEquiposTentativos();
		final InterfazConfirmarPartido interfazConfirmarPArtido = new InterfazConfirmarPartido();
		final InterfazEmpezarPartido interfazEmpezarPartido = new InterfazEmpezarPartido();
		final InterfazTerminarPartido InterfazTerminarPartido= new InterfazTerminarPartido();
		final InterfazPenalizaciones InterfazPenalizaciones = new InterfazPenalizaciones();
		final InterfazBusquedaYVisualizacion InterfazBusquedaYVisualizacion = new InterfazBusquedaYVisualizacion();
		final InterfazRechazados InterfazRechazados = new InterfazRechazados();
		final InterfazVerPartidos InterfazVerPartidos = new InterfazVerPartidos();
		crearPartido.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=interfazCrearPartido;
				interfazCrearPartido.addListener(interfazPresenter);
				
				panel.setContent(interfazCrearPartido.crearPartidoInterfaz());
			
			}
		});
		aceptarORechazarJugador.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazAprobarORechazarJugadorNuevo;
				InterfazAprobarORechazarJugadorNuevo.addListener(interfazPresenter);
				
				panel.setContent(InterfazAprobarORechazarJugadorNuevo.InterfazAprobarORechazarJugadorNuevo());
			
			}
		});
		
		listaDeJugadores.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=interfazListaDeJugadores;
				interfazListaDeJugadores.addListener(interfazPresenter);
				
				panel.setContent(interfazListaDeJugadores.InterfazListaDeJugadores());
			
			}
		});
		generarEquiposTentativos.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=interfazGenerarEquiposTentativos;
				interfazGenerarEquiposTentativos.addListener(interfazPresenter);
				
				panel.setContent(interfazGenerarEquiposTentativos.InterfazGenerarEquiposTentativos());
			
			}
		});
		
		confirmarPartido.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=interfazConfirmarPArtido;
				interfazConfirmarPArtido.addListener(interfazPresenter);
				
				panel.setContent(interfazConfirmarPArtido.InterfazConfirmarPartido());
			
			}
		});
		
		
		empezarPartido.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=interfazEmpezarPartido;
				interfazEmpezarPartido.addListener(interfazPresenter);
				
				panel.setContent(interfazEmpezarPartido.InterfazEmpezarPartido());
			
			}
		});
		terminarPartido.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazTerminarPartido;
				InterfazTerminarPartido.addListener(interfazPresenter);
				
				panel.setContent(InterfazTerminarPartido.InterfazTerminarPartido());
			
			}
		});
		penalizar.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazPenalizaciones;
				InterfazPenalizaciones.addListener(interfazPresenter);
				
				panel.setContent(InterfazPenalizaciones.InterfazPenalizaciones());
			
			}
		});
		buscarJugadores.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazBusquedaYVisualizacion;
				InterfazBusquedaYVisualizacion.addListener(interfazPresenter);
				
				panel.setContent(InterfazBusquedaYVisualizacion.InterfazBusquedaYVisualizacion());
			
			}
		});
		rechazos.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazRechazados;
				InterfazRechazados.addListener(interfazPresenter);
				
				panel.setContent(InterfazRechazados.InterfazRechazados());
			
			}
		});
		
		verPartidos.setCommand(new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				interfazPresenter.vista=InterfazVerPartidos;
				InterfazVerPartidos.addListener(interfazPresenter);
				
				panel.setContent(InterfazVerPartidos.InterfazVerPartidos());
			
			}
		});
		
		atras.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("");
				
			}
		});
		
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
		
	}
	
	
}
