package com.example.interfazv2;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;











import BD.BDConnection;
import Logica.Administrador;
import Patrones.SingletonJugadoresParaAprobar;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("interfazv2")
public class Interfazv2UI extends UI implements InterfazVistas {

	
	//public admin admin;
	public Navigator navigator;
	
	public static String getAdmin() {
		return ADMIN;
	}


	public Navigator getNavigator() {
		return navigator;
	}


	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}


	//protected static final String MAINVIEW = "main";
	protected static final String JUGADOR = "jugador";
	protected static final String ADMIN = "admin";
	protected static final String CREARJUGADOR = "crearJugador";
	
	
	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Interfazv2UI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		Administrador admin = new Administrador();
		SingletonJugadoresParaAprobar singleton = SingletonJugadoresParaAprobar.getInstance();
		singleton.setAdmin(admin);
		singleton.setPresenter(new InterfazPresenter(null, null));
		singleton.getPresenter().setAdmin(admin);
		getPage().setTitle("Organizator v15.06");
	//	admin = new admin();
		// Create a navigator to control the views
		navigator = new Navigator(this, this);

		// Create and register the views
		final InterfazPresenter interfazPresenter =singleton.getPresenter();
		final InterfazLogin interfazLogin = new InterfazLogin();
		interfazPresenter.vista = interfazLogin;
		interfazLogin.addListener(interfazPresenter);
		
		
		navigator.addView("", interfazLogin);//ACA MODIFIQUE
		navigator.addView(JUGADOR, new InterfazJugador());
		navigator.addView(ADMIN, new InterfazAdmin());
		navigator.addView(CREARJUGADOR, new InterfazDarmeDeAlta());
		
	/*	ConexionBD asd = new ConexionBD();
		try {
			asd.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
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