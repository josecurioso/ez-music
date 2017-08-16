package logic;

import java.io.File;

import org.json.JSONObject;

public class DownloaderMP3 implements Runnable{

	String videoURL;
	String requestPATH;
	Logger logger;
	
	
	public DownloaderMP3(String videoURL, String requestPATH, Logger logger){
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
			
			try{
				optionB(videoURL, requestPATH);
			}
			catch(FailedDownloadException i){
				logger.log("standard", "error", "FAILED: " + videoURL);
			}
		}
	}
	
	public void optionA(String videoURL, String requestPATH) throws FailedDownloadException{
		try{
			
			//First part
			
			JSONObject data = JSONTools.GETComplex("https://www.ytbmp3.com/i/search", "{\"q\":\"" + videoURL + "\"}", 100000, "POST", "https://www.ytbmp3.com", logger);
			String fileName = data.getJSONArray("items").getJSONObject(0).getString("title") + ".mp3";
			String id = data.getJSONArray("items").getJSONObject(0).get("id").toString();  
			String downloadURL = JSONTools.GETComplex("https://www.ytbmp3.com/i/download", "{\"type\":\"mp3\",\"id\":\"" + id + "\",\"bitrate\":\"320\",\"start\":\"\",\"end\":\"\"}", 100000, "POST", "https://www.ytbmp3.com", logger).getString("location");
			
			if(!downloadURL.contains("https")){
				downloadURL = "https://www.ytbmp3.com" + downloadURL;
			}

			
			String result = Downloader.downloadFile(downloadURL, requestPATH, fileName, logger);
			
			//Second part
			try{
				data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL, logger);
				fileName = "";
				downloadURL = "";
				fileName = data.getString("title") + ".mp3";
				downloadURL = data.getString("link");
				
				if(result.equals("failed")){
					throw new FailedDownloadException();
				}
				
				String result2 = Downloader.downloadFile(downloadURL, requestPATH + File.separator + "temp", fileName, logger);
				
				//Tag reassignment
				ID3Tools.copyTags(result2, result, logger);
			}
			catch(Exception e){
				
			}
			
		}
		catch(Exception e){
			throw new FailedDownloadException();
		}
		logger.log("standard", "info", "File downloaded on first try");
	}
	
	public void optionB(String videoURL, String requestPATH) throws FailedDownloadException{
		try{
			JSONObject data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL, logger);
			String fileName = "";
			String downloadURL = "";
			fileName = data.getString("title") + ".mp3";
			downloadURL = data.getString("link");
			
			String result = Downloader.downloadFile(downloadURL, requestPATH, fileName, logger);
			
			if(result.equals("failed")){
				throw new FailedDownloadException();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FailedDownloadException();
		}
		logger.log("standard", "info", "File downloaded on second try");
	}
}