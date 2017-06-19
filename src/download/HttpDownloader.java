package download;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import gui.MainWindow;



public class HttpDownloader implements Runnable{
	
	ArrayList<Thread> threads = new ArrayList<Thread>();
	String requestedURL;
	String savePath;
	MainWindow window;
	
	public HttpDownloader(String requestedURL, String savePath, MainWindow window){
		this.requestedURL = requestedURL;
		this.savePath = savePath;
		this.window = window;
		
	}

	@Override
	public void run() {
		try {
			main(this.requestedURL, this.savePath);
		} catch (JSONException | InterruptedException e) {
			e.printStackTrace();
		}
		window.downloadFinished();
	}
	
	public boolean main(String requestedURL, String savePath) throws JSONException, InterruptedException {
		if (requestedURL.contains("youtube.com") && requestedURL.contains("watch?v=")) {
			download(requestedURL, savePath);
		} 
		
		else if (requestedURL.contains("youtube.com") && requestedURL.contains("playlist?list=")) {
			ArrayList<String> videoLinks = YouTubeAPI.getLinks(requestedURL);
			for (String link : videoLinks) {
				download(link, savePath);
			}
			for (Thread t : this.threads){
				t.join();
			}
		}
		return true;
	}

	public void download(String videoURL, String savePath) {
		String downloadURL = "";
		String fileName = "";
		try {
			JSONObject data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL);
			downloadURL = data.getString("link");
			fileName = data.getString("title") + ".mp3";
			//Create thread and start it
			Runnable r = new HttpDownloadUtility(downloadURL, savePath, fileName);
			Thread t = new Thread(r);
			this.threads.add(t);
			t.start();
			r.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}