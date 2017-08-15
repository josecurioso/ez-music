package logic;

import org.json.JSONObject;

public class DownloaderMP3 implements Runnable{

	String videoURL;
	String requestPATH;
	
	
	public DownloaderMP3(String videoURL, String requestPATH){
		this.videoURL = videoURL;
		this.requestPATH = requestPATH;
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
				System.out.println("FAILED: " + videoURL);
			}
		}
	}
	
	public void optionA(String videoURL, String requestPATH) throws FailedDownloadException{
		try{
			JSONObject data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL);
			String fileName = "";
			String downloadURL = "";
			fileName = data.getString("title") + ".mp3";
			downloadURL = data.getString("link");
			
			if(!Downloader.downloadFile(downloadURL, requestPATH, fileName)){
				throw new FailedDownloadException();
			}
		}
		catch(Exception e){
			throw new FailedDownloadException();
		}
		System.out.println("File downloaded on first try");
	}
	
	public void optionB(String videoURL, String requestPATH) throws FailedDownloadException{
		try{
			JSONObject data = JSONTools.GETComplex("https://www.ytbmp3.com/i/search", "{\"q\":\"" + videoURL + "\"}", 100000, "POST", "https://www.ytbmp3.com");
			String fileName = data.getJSONArray("items").getJSONObject(0).getString("title") + ".mp3";
			String id = data.getJSONArray("items").getJSONObject(0).get("id").toString();  
			String downloadURL = JSONTools.GETComplex("https://www.ytbmp3.com/i/download", "{\"type\":\"mp3\",\"id\":\"" + id + "\",\"bitrate\":\"320\",\"start\":\"\",\"end\":\"\"}", 100000, "POST", "https://www.ytbmp3.com").getString("location");
			
			if(!downloadURL.contains("https")){
				downloadURL = "https://www.ytbmp3.com" + downloadURL;
			}
			
			
			
			if(Downloader.downloadFile(downloadURL, requestPATH, fileName)){
				throw new FailedDownloadException();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new FailedDownloadException();
		}
		System.out.println("File downloaded on second try");
	}
}