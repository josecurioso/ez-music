package logic;

import java.io.File;

import org.json.JSONObject;

import except.FailedDownloadException;
import logger.Logger;
import tools.ID3Tools;
import tools.JSONTools;

public class DownloaderMP3 implements Runnable{

	private JSONObject info;
	private String videoURL;
	private String requestPATH;
	private Logger logger;
	
	
	public DownloaderMP3(JSONObject info, String videoURL, String requestPATH, Logger logger){
		this.info = info;
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
			/*
			try{
				optionB(videoURL, requestPATH);
			}
			catch(FailedDownloadException i){
				logger.log("standard", "error", "FAILED: " + videoURL);
			}
			*/
		}
	}
	
	private void optionA(String videoURL, String requestPATH) throws FailedDownloadException{
		try{
			
			//First part
			
			JSONObject data = JSONTools.GETComplex("https://www.ytbmp3.com/i/search", "{\"q\":\"" + videoURL + "\"}", 100000, "POST", "https://www.ytbmp3.com", logger);
			String fileName = "";
			//fileName = info.getString("title") + ".mp3";
			String id = videoURL.substring(32);
			String downloadURL = JSONTools.GETComplex("https://www.ytbmp3.com/i/download", "{\"type\":\"mp3\",\"id\":\"" + id + "\",\"bitrate\":\"320\",\"start\":\"\",\"end\":\"\"}", 100000, "POST", "https://www.ytbmp3.com", logger).getString("location");
			
				
			String result = Downloader.downloadFile(downloadURL, requestPATH, fileName, logger);
			
			//Second part
			/*
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
			*/
			
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FailedDownloadException();
		}
		logger.log("standard", "info", "File downloaded on first try");
	}
	/*
	private void optionB(String videoURL, String requestPATH) throws FailedDownloadException{
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
	*/
}