package logic;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import logger.Logger;
import tools.YouTubeAPITools;

public class DownloadSession implements Runnable{
	
	private ArrayList<Thread> threads = new ArrayList<Thread>();
	private MainLogic logicMain;
	private String requestURL;
	private String requestPATH;
	private String mode;
	private Logger logger;

	public DownloadSession(String requestURL, String requestPATH, String mode, MainLogic logicMain, Logger logger){
		this.logicMain = logicMain;
		this.requestURL = requestURL;
		this.requestPATH = requestPATH;
		this.mode = mode;
		this.logger = logger;
	}
	
	@Override
	public void run(){		
		if (requestURL.contains("youtube.com") && requestURL.contains("watch?v=")) {
			try {
				download(YouTubeAPITools.getLinkInfo(requestURL, logger), requestURL, requestPATH);
			} catch (JSONException e) {
			}
		} 
		
		else if (requestURL.contains("youtube.com") && requestURL.contains("playlist?list=")) {
			ArrayList<String> videoLinks = null;
			try{
				videoLinks = YouTubeAPITools.getLinks(requestURL, logger);
				for (String link : videoLinks) {
					download(YouTubeAPITools.getLinkInfo(link, logger), link, requestPATH);
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
		
		logicMain.finishDownloadSession();
	}
	
	private void download(JSONObject info, String videoURL, String requestPATH){
		Runnable r = null;
		if(mode.equals("audio")){ r = new DownloaderMP3(info, videoURL, requestPATH, logger); }
		if(mode.equals("video")){ r = new DownloaderMP4(info, videoURL, requestPATH, logger); }
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
