package ru.alex.st.hh.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageSource {
		
	private static final String BUNDLE_NAME = "Spider";
		
	public static String getMessage(String key, Locale locale) {
		ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		return rb.getString(key);
	}

}
