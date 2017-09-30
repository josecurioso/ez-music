package gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONObject;

import logger.Logger;
import tools.YouTubeAPITools;

public class ChangeListener implements DocumentListener{
	MainWindow window;
	Logger logger;
	
	public ChangeListener(MainWindow window, Logger logger){
		this.logger = logger;
		this.window = window;
	}
	
	@Override
    public void insertUpdate(DocumentEvent e) {
    	window.updateInfoPane(getInfo(window.getCurrentRequest()[0]));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    	window.updateInfoPane(getInfo(window.getCurrentRequest()[0]));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    	window.updateInfoPane(getInfo(window.getCurrentRequest()[0]));
    }	
    
    
    
    private String[] getInfo(String link){
    	String[] info = {"", "", "", "", ""};    
    	System.out.println("cambio detectado");
    	try {
			JSONObject data = YouTubeAPITools.getLinkInfo(link, logger);
			if(data != null){
				//Log details
				logger.log("debug", "info", data.getString("title"));
				logger.log("debug", "info", data.getString("channel"));
				logger.log("debug", "info", new Integer(data.getInt("qt")).toString());
				logger.log("debug", "info", data.getString("description"));
				//Set Title
				info[0] = data.getString("title");							
				//Set Channel
				info[1] = data.getString("channel");				
				//Set Amount
				if(data.getInt("qt") != -1) info[2] = new Integer(data.getInt("qt")).toString();
				else info[2] = "1";				
				//SetDescription
				info[3] = data.getString("description");				
				//Set Thumbnail
				info[4] = data.getString("thumb");
			}
			else if(data == null){
				throw new RuntimeException();
			}
		}
		catch (Exception e) {}
    	return info;
    }
}
