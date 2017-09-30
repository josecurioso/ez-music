package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logger.Logger;

public class GuiActionListener implements ActionListener {
	Logger logger = null;	
	MainWindow main = null;
	TextOutput txtOputput = null;
	
	public GuiActionListener(MainWindow main, TextOutput txtOputput, Logger logger){
		this.main = main;
		this.logger = logger;
		this.txtOputput = txtOputput;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
	    if ("download".equals(e.getActionCommand())) {
	    	main.getLogicMain().startDownloadSession();
	    } 
	    if("select".equals(e.getActionCommand())){
	    	main.openFileChooser();
	    }
		if("audioSwitch".equals(e.getActionCommand())){
			logger.log("standard", "info", "Now in audio mode");
		}
		if("videoSwitch".equals(e.getActionCommand())){
			logger.log("standard", "info", "Now in video mode");
		}
		if("textOutput".equals(e.getActionCommand())){
			main.showTextOutput();
		}
		if("cbLanguage".equals(e.getActionCommand())){
			logger.log("standard", "info", "Language switched");
			main.changeLocale();
		}
		if("paste".equals(e.getActionCommand())){
			logger.log("standard", "info", "Link pasted");
			main.pasteAction();
		}
	}

}
