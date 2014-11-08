package com.example.interfazv2;

import java.awt.Panel;

import Patrones.SingletonJugadoresParaAprobar;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.ibm.icu.impl.CalendarAstronomer.Horizon;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class InterfazLogin extends VerticalLayout implements View,InterfazVistas {
	ViewListener listener ;
	public InterfazLogin() {
		
		final InterfazPresenter interfazPresenter = SingletonJugadoresParaAprobar.getInstance().getPresenter();
		final InterfazDarmeDeAlta darmeDeAlta = new InterfazDarmeDeAlta();
		final VerticalLayout layout = new VerticalLayout();
		final HorizontalLayout form = new HorizontalLayout();
		final VerticalLayout login = new VerticalLayout();
		final HorizontalLayout botonera = new HorizontalLayout();
		TextField campoDNI = new TextField("D.N.I.");
		//PasswordField contrasenia = new PasswordField("Contraseña: ");
		Button admin = new Button("Admin");
		Button jugador = new Button("Jugador");
		Button crearJugador = new Button("Crear jugador");
		final com.vaadin.data.util.ObjectProperty<Integer> DNI = new com.vaadin.data.util.ObjectProperty<Integer>(0, Integer.class);
		
		final Window loginWindow = new Window("Sing up");
		VerticalLayout layoutLogin = new VerticalLayout();
		loginWindow.center();
		loginWindow.setContent(layoutLogin);
		final com.vaadin.ui.Panel panelLogin= new com.vaadin.ui.Panel();
		layoutLogin.addComponent(panelLogin);
		
		campoDNI.setPropertyDataSource(DNI);
		setSpacing(true);
		addComponent(layout);
	
		layout.addComponent(login);
		login.addComponents(form,botonera);
		form.addComponents(campoDNI);
		botonera.addComponents(admin,jugador,crearJugador);
	
		//layout.setSizeFull();
		layout.setMargin(true);
		layout.setSpacing(true);
		
		form.setSpacing(true);
		form.setMargin(true);
		
		botonera.setMargin(true);
		botonera.setSpacing(true);
		
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
		
		admin.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo("admin");
			}
		});
		
		jugador.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				//getUI().getNavigator().navigateTo("jugador");
				if (listener.loguearseJugador(DNI.getValue())!=null){
					getUI().getNavigator().navigateTo("jugador");
				
				}
			}
		});
		
		crearJugador.addClickListener(new ClickListener() {
			
			public void buttonClick(ClickEvent event){
				
				UI.getCurrent().addWindow(loginWindow);
				final InterfazDarmeDeAlta darmeDeAlta =new InterfazDarmeDeAlta();
				interfazPresenter.vista = darmeDeAlta;
				darmeDeAlta.addListener(interfazPresenter);
				panelLogin.setContent(darmeDeAlta.InterfazDarmeDeAlta());
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
		this.listener = listener;
	}

}
