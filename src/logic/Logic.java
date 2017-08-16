package logic;

import java.util.ArrayList;

import org.json.JSONException;

import gui.MainWindow;

public class Logic implements Runnable{
	
	ArrayList<Thread> threads = new ArrayList<Thread>();
	MainWindow window;
	String requestURL;
	String requestPATH;
	String mode;
	Logger logger;

	public Logic(String requestURL, String requestPATH, String mode, MainWindow window, Logger logger){
		this.window = window;
		this.requestURL = requestURL;
		this.requestPATH = requestPATH;
		this.mode = mode;
		this.logger = logger;
		
	}
	
	@Override
	public void run(){		
		if (requestURL.contains("youtube.com") && requestURL.contains("watch?v=")) {
			download(requestURL, requestPATH);
		} 
		
		else if (requestURL.contains("youtube.com") && requestURL.contains("playlist?list=")) {
			ArrayList<String> videoLinks = null;
			try{
				videoLinks = YouTubeAPI.getLinks(requestURL, logger);
				for (String link : videoLinks) {
					download(link, requestPATH);
				}
			}
			catch(JSONException e){}
		}
		for (Thread t : this.threads){
			try{
				t.join();
			}
			catch(InterruptedException e){}
		}
		
		window.downloadFinished();
	}
	
	public void download(String videoURL, String requestPATH){
		Runnable r = null;
		if(mode.equals("audio")){ r = new DownloaderMP3(videoURL, requestPATH, logger); }
		if(mode.equals("video")){ r = new DownloaderMP4(videoURL, requestPATH, logger); }
		try{
			Thread t = new Thread(r);
			this.threads.add(t);
			t.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}
