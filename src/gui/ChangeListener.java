package gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.Logger;

public class ChangeListener implements DocumentListener{
	MainWindow window;
	Logger logger;
	
	public ChangeListener(MainWindow window, Logger logger){
		this.logger = logger;
		this.window = window;
	}
	
    public void insertUpdate(DocumentEvent e) {
    	window.updateInfo();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    	window.updateInfo();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    	window.updateInfo();
    }	

}
