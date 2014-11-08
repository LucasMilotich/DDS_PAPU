package com.example.interfazv2;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class InterfazBorrarmeJugador extends VerticalLayout implements View, InterfazVistas {
	ViewListener listener ;
	
	public VerticalLayout InterfazBorrarmejugador(){
		VerticalLayout layout = new VerticalLayout();
		Button confirmar = new Button("Confirmar");
		addComponent(layout);
		
		setMargin(true);setSizeFull();setSpacing(true);
		layout.setMargin(true);layout.setSizeFull();layout.setSpacing(true);
		layout.addComponent(confirmar);
		
		
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
