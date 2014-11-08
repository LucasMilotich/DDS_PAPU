package com.example.interfazv2;


import Logica.Jugador;
import Patrones.SingletonJugadoresParaAprobar;

import com.vaadin.data.util.filter.IsNull;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;

public class InterfazJugador extends VerticalLayout implements View, InterfazVistas {
	
	
	public InterfazJugador(){
	VerticalLayout layout = new VerticalLayout();
	VerticalLayout menuLayout = new VerticalLayout();
	VerticalLayout mainLayout = new VerticalLayout();
	
	MenuBar menu = new MenuBar();
	
	MenuItem jugadorMenu = menu.addItem("Jugador", null, null);
	MenuItem partidoMenu= menu.addItem("Partido", null, null);
	MenuItem amigos = menu.addItem("Amigos", null,null);
	MenuItem verAmigos = amigos.addItem("Ver amigos", null, null);
	
	MenuItem darseDeAltaJugador = jugadorMenu.addItem("Darme de alta", null, null);
	MenuItem datosJugador = jugadorMenu.addItem("Mis datos",null,null);
	MenuItem inscribirmeAPartidoJugador = partidoMenu.addItem("Inscribirme a partido", null, null);
	MenuItem proponerNuevoJugador = jugadorMenu.addItem("Proponer jugador", null, null);
	MenuItem darseDebajaJugador = partidoMenu.addItem("Darme de baja", null, null);
	
//	MenuItem elimiarseJugador = jugadorMenu.addItem("Borrarme como jugador", null, null);
	MenuItem verPartidosInscriptos = partidoMenu.addItem("Ver partidos inscriptos", null, null);
	MenuItem calificarJugadores = partidoMenu.addItem("Calificar jugadores", null, null);
	
	
	
	final Panel panel = new Panel();
	
	com.vaadin.ui.Button atras = new com.vaadin.ui.Button("Atras");
	setHeight("100%");
	layout.setHeight("100%");
	addComponent(layout);
	menu.setSizeFull();
	//menuLayout.setHeight("10%");
	menuLayout.addComponent(menu);
	mainLayout.setSizeFull();
	mainLayout.addComponents(panel,atras);
	atras.setSizeFull();
	
	mainLayout.setExpandRatio(panel, 4);
	mainLayout.setExpandRatio(atras, 0.15f);
	layout.addComponents(menuLayout,mainLayout);
	layout.setExpandRatio(menuLayout,0.15f);
	layout.setExpandRatio(mainLayout,4);
	panel.setSizeFull();
	final InterfazPresenter interfazPresenter =SingletonJugadoresParaAprobar.getInstance().getPresenter();
	final InterfazDarmeDeAlta darmeDeAlta =new InterfazDarmeDeAlta();
	final InterfazInscribirmePartido inscribirmePartido= new InterfazInscribirmePartido();
	final InterfazDarmeDeBaja darmeDeBaja = new InterfazDarmeDeBaja();
	final InterfazProponerJugador proponerJugador = new InterfazProponerJugador();
	final InterfazCalificarJugadores calificaJugadores = new InterfazCalificarJugadores();
	final InterfazPartidosInscriptos InterfazPartidosInscriptos = new InterfazPartidosInscriptos();
	final InterfazVerAmigos interfazVerAmigos = new InterfazVerAmigos();
	final InterfazBorrarmeJugador borrarmeComojugador= new InterfazBorrarmeJugador();
	final InterfazDatosJugador interfazDatosJugador = new InterfazDatosJugador();
	
	atras.addListener(new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			getUI().getNavigator().navigateTo("");
			
		}
	});
	
	verPartidosInscriptos.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
		//Jugador jugador= new Jugador();
		
		
		interfazPresenter.vista = InterfazPartidosInscriptos;
		InterfazPartidosInscriptos.addListener(interfazPresenter);
		panel.setContent(InterfazPartidosInscriptos.InterfazPartidosInscriptos());
		
	
			
		}
	});
	/*elimiarseJugador.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
		//Jugador jugador= new Jugador();
		
		
		interfazPresenter.vista = borrarmeComojugador;
		borrarmeComojugador.addListener(interfazPresenter);
		panel.setContent(borrarmeComojugador.InterfazBorrarmejugador());
		
	
			
		}
	});*/
	
verAmigos.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
		//Jugador jugador= new Jugador();
		
		
		interfazPresenter.vista = interfazVerAmigos;
		interfazVerAmigos.addListener(interfazPresenter);
		panel.setContent(interfazVerAmigos.InterfazVerAmigos());
		
	
			
		}
	});

	calificarJugadores.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
		//Jugador jugador= new Jugador();
		
		
		interfazPresenter.vista = calificaJugadores;
		calificaJugadores.addListener(interfazPresenter);
		panel.setContent(calificaJugadores.InterfazCalificarJugadores());
		
	
			
		}
	});
	
	darseDeAltaJugador.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {		
		
		interfazPresenter.vista = darmeDeAlta;
		darmeDeAlta.addListener(interfazPresenter);
		panel.setContent(darmeDeAlta.InterfazDarmeDeAlta());
		
	
			
		}
	});

	inscribirmeAPartidoJugador.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
		interfazPresenter.vista=inscribirmePartido;
		inscribirmePartido.addListener(interfazPresenter);
		panel.setContent( inscribirmePartido.InterfazInscribirmePartido());
		
	
			
		}
	});
	
	darseDebajaJugador.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			interfazPresenter.vista=darmeDeBaja;
			darmeDeBaja.addListener(interfazPresenter);
		panel.setContent( darmeDeBaja.InterfazDarmeDeBaja());
		
	
			
		}
	});
	
proponerNuevoJugador.setCommand(new MenuBar.Command() {
		
		@Override
		public void menuSelected(MenuItem selectedItem) {
			interfazPresenter.vista=proponerJugador;
			proponerJugador.addListener(interfazPresenter);
		panel.setContent( proponerJugador.InterfazProponerJugador());
		
	
			
		}
	});
	
datosJugador.setCommand(new MenuBar.Command() {
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		interfazPresenter.vista=interfazDatosJugador;
		interfazDatosJugador.addListener(interfazPresenter);
		panel.setContent( interfazDatosJugador.InterfazDatosJugador());
	
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
