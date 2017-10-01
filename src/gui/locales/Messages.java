package gui.locales;

import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private final String BASE_NAME = "gui.locales.messages";
	private ResourceBundle RESOURCE_BUNDLE;
	
	private final List<String> languages = Arrays.asList("English", "Espa\u00f1ol");
	private final String[] codes = {"en", "es"};

	
	public Messages() {
		this("en");
	}
	
	public Messages(String code) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BASE_NAME + "_" + code);
	}

	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public void switchSource(String source){
		String code = codes[languages.indexOf(source)];
		this.RESOURCE_BUNDLE = ResourceBundle.getBundle(BASE_NAME + "_" + code);
	}
	
	public String[] getLanguages(){
		String[] a = new String[languages.size()];
		languages.toArray(a);
		return a;
		
	}
}
