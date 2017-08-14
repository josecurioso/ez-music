package logic;

import java.util.ArrayList;

import org.json.JSONException;

import gui.MainWindow;

public class Logic implements Runnable{
	
	ArrayList<Thread> threads = new ArrayList<Thread>();
	MainWindow window;
	String requestURL;
	String requestPATH;

	public Logic(String requestURL, String requestPATH, MainWindow window){
		this.window = window;
		this.requestURL = requestURL;
		this.requestPATH = requestPATH;
		
	}
	
	@Override
	public void run(){		
		if (requestURL.contains("youtube.com") && requestURL.contains("watch?v=")) {
			download(requestURL, requestPATH);
		} 
		
		else if (requestURL.contains("youtube.com") && requestURL.contains("playlist?list=")) {
			ArrayList<String> videoLinks = null;
			try{
				videoLinks = YouTubeAPI.getLinks(requestURL);
				for (String link : videoLinks) {
					download(link, requestPATH);
				}
				for (Thread t : this.threads){
					try{
						t.join();
					}
					catch(InterruptedException e){}
				}
			}
			catch(JSONException e){}
		}
		
		window.downloadFinished();
	}
	
	public void download(String videoURL, String requestPATH){
		try{
			Runnable r = new Downloader(videoURL, requestPATH);
			Thread t = new Thread(r);
			this.threads.add(t);
			t.start();
			r.run();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
	}
}
