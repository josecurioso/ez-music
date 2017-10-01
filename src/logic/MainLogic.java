package logic;

import java.io.File;

import gui.MainWindow;
import logger.Logger;

public class MainLogic {
	Logger logger;
	MainWindow main;
	
	public MainLogic(MainWindow main, Logger logger){
		this.logger = logger;
		this.main = main;
	}
	
	public void startDownloadSession(){
		String requestedURL = "";
		String savePath = "";
		String mode = "";
		
		String [] request = main.getCurrentRequest();
		
		requestedURL = request[0];
		savePath = request[1];
		mode = request[2];
		
    	if(checkFields(requestedURL, savePath)){				
			new File(savePath + "\\temp").mkdirs();
			
			logger.log("standard", "info", "Starting download, hold tight!");
			main.downloadStarted();
			
			Runnable logic = new DownloadSession(requestedURL, savePath, mode, this, logger);
			Thread t = new Thread(logic);
			t.start();
			
			
    	}
    	else{
    		logger.log("standard", "error", "Error in one of the fields");
    		main.downloadFinished();
    	}
	}
	
	public void finishDownloadSession(){
		String [] request = main.getCurrentRequest();
		String savePath = request[1];
		
		File file = new File(savePath + "\\temp");
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	        	f.delete();
	        }
	    }
	    file.delete();
		logger.log("standard", "info", "Proccess finished");
	    main.downloadFinished();
	}

	private boolean checkFields(String link, String path){
		boolean clink = false;
        boolean cpath = false;
        if (link.contains("http://www.youtube.com/playlist?list=") || link.contains("https://www.youtube.com/playlist?list=") || link.contains("http://www.youtube.com/watch?v=") || link.contains("https://www.youtube.com/watch?v=")){
        	clink = true;
        }
        if (path != ""){
        	cpath = true;
        }
        return(cpath && clink);
	}
}
