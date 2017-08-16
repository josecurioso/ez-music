package logic;

import org.json.JSONObject;

public class DownloaderMP4 implements Runnable{

	String videoURL;
	String requestPATH;
	Logger logger;
	
	
	public DownloaderMP4(String videoURL, String requestPATH, Logger logger){
		this.videoURL = videoURL;
		this.requestPATH = requestPATH;
		this.logger = logger;
	}
	
	@Override
	public void run() {		
		try{
			optionA(videoURL, requestPATH);
		}
		catch(FailedDownloadException e){
			logger.log("standard", "error", "FAILED: " + videoURL);
		}
	}
	
	
	public void optionA(String videoURL, String requestPATH) throws FailedDownloadException{		
		try{
			JSONObject videoInfo = JSONTools.GETComplex("https://www.ytbmp4.com/i/search", "{\"q\":\"" + videoURL + "\"}", 100000, "POST", "https://www.ytbmp4.com", logger);
			String id = videoInfo.getJSONArray("items").getJSONObject(0).get("id").toString();
			String quality = videoInfo.getJSONArray("items").getJSONObject(0).getJSONArray("mp4_formats").getString(0);
			String downloadLink = JSONTools.GETComplex("https://www.ytbmp4.com/i/download", "{\"type\":\"mp4\",\"id\":\"" + id + "\",\"quality\":\"" + quality + "\"}", 100000, "POST", "https://www.ytbmp4.com", logger).getString("location");
			String title = videoInfo.getJSONArray("items").getJSONObject(0).getString("title");
					
			Downloader.downloadFile(downloadLink, requestPATH, title + ".mp4", logger);
		}
		catch(Exception e){
			throw new FailedDownloadException();
		}
		logger.log("standard", "info", "File downloaded on first try");
	}
}
