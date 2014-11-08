package com.example.interfazv2;

import java.util.regex.PatternSyntaxException;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Label;

public class MyCustomFilter implements Container.Filter {
	protected String propertyId;
	protected String regex;
	protected Label status;

	public MyCustomFilter(String propertyId, String regex, Label status) {
		this.propertyId = propertyId;
		this.regex = regex;
		this.status = status;
	}

	@Override
	public boolean passesFilter(Object itemId, Item item)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		Property p = item.getItemProperty(propertyId);

		// Should always check validity
		if (p == null || !p.getType().equals(String.class))
			return false;
		String value = (String) p.getValue();

		// Pass all if regex not given
		if (regex.isEmpty()) {
			status.setValue("Empty filter");
			return true;
		}

		// The actual filter logic + error handling
		try {
			boolean result = value.matches(regex);
			status.setValue(""); // OK
			return result;
		} catch (PatternSyntaxException e) {
			status.setValue("Invalid pattern");
			return false;

		}
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		// TODO Auto-generated method stub
		 return propertyId != null &&
	               propertyId.equals(this.propertyId);
	}

}
