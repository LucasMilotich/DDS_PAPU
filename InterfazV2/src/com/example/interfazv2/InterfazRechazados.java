package com.example.interfazv2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Logica.Partido;

import com.example.interfazv2.InterfazVistas.ViewListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

public class InterfazRechazados extends VerticalLayout implements View,
		InterfazVistas, ClickListener {
	ViewListener listener;

	public VerticalLayout InterfazRechazados() {
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout tiposPartidos = new HorizontalLayout();
		final Table rechazos = new Table("Rechazos");
		Table partidos = new Table("Partidos");
		

		layout.setMargin(true);
		layout.setSpacing(true);
		tiposPartidos.setSpacing(true);
		tiposPartidos.setMargin(true);
		
		
		
		
		
		
		/*partidosAbiertos.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
*/
				listener.obtenerRechazosYBindiarDeUnPartido(rechazos,
						null);
				rechazos.setVisibleColumns(new String[] { "motivo", "nombre", "apellido", "jugadorProponedor.nombre","jugadorProponedor.apellido","fecha" });
				rechazos.setColumnHeaders(new String[] { " Motivo", "Nombre del propuesto",
						"Apellido del propuesto", "Nombre proponedor","Apellido proponedor", "Fecha" });
	/*		}
		});*/
				rechazos.setConverter("fecha", new StringToDateConverter(){

					public DateFormat getFormat(Locale locale){

					return new SimpleDateFormat("dd-MM-YYYY");

					}

					});
		
		layout.addComponents(tiposPartidos,rechazos);
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
