package tools;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import logger.Logger;

public class YouTubeAPITools {
	private final static String BASE_URL = "https://www.googleapis.com/youtube/v3/";
	private final static String DEVELOPER_KEY = "AIzaSyB-0w6c7lz4DNWcS4blcHFrb3xl_AkGsvk";

	public static ArrayList<String> getLinks(String playListLink, Logger logger) throws JSONException{
		ArrayList<String> videoLinks = new ArrayList<String>();
		String playListCode = "";
		JSONObject data;
		if(playListLink.contains("https")){
			playListCode = playListLink.substring(38);
		}
		else if(playListLink.contains("http")){
			playListCode = playListLink.substring(37);
		}
		
		
		String pageToken = "";
		String callUrl = BASE_URL + "playlistItems?key=" + DEVELOPER_KEY + "&part=snippet&playlistId=" + playListCode + "&pageToken=" + pageToken;
		data = JSONTools.GET(callUrl, logger);
		
		int vid_num = (int) data.getJSONObject("pageInfo").get("totalResults");
		int counter = 0;
		
		while(counter < vid_num){
			callUrl = BASE_URL + "playlistItems?key=" + DEVELOPER_KEY + "&part=snippet&playlistId=" + playListCode + "&pageToken=" + pageToken;
			data = JSONTools.GET(callUrl, logger);
			
			JSONArray videos = data.getJSONArray("items");
			
			for(int i = 0, size = videos.length(); i < size; i++){
				JSONObject item = videos.getJSONObject(i);
				videoLinks.add("https://www.youtube.com/watch?v=" + item.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId"));
			}
			
			try{
				pageToken = data.getString("nextPageToken");
			}
			catch(Exception e){
			
			}
			counter+=5;
		}
		
		
    	return videoLinks;
    }

	public static JSONObject getLinkInfo(String inUrl, Logger logger) throws JSONException {
		JSONObject data = new JSONObject();

		String url = fixUrl(inUrl, logger);
		
		if (url.contains("watch?v=")) {
			String id = url.substring(32);
			String callUrl = BASE_URL + "videos?key=" + DEVELOPER_KEY + "&part=contentDetails,snippet,statistics&id=" + id;
			
			logger.log("debug", "info", url);
			logger.log("debug", "info", id);
			logger.log("debug", "info", callUrl);
			
			
			JSONObject info = JSONTools.GET(callUrl, logger);
			data.put("title", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title"));
			data.put("description", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description"));
			data.put("channel", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle"));
			data.put("thumb", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
			data.put("qt", -1);
			return data;
		} 
		
		else if (url.contains("playlist?list=")) {
			String id = url.substring(38);
			String callUrl = BASE_URL + "playlists?key=" + DEVELOPER_KEY + "&part=contentDetails,snippet,status&id=" + id;
			
			logger.log("debug", "info", url);
			logger.log("debug", "info", id);
			logger.log("debug", "info", callUrl);
			
			
			JSONObject info = JSONTools.GET(callUrl, logger);			
			data.put("title", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title"));
			data.put("description", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description"));
			data.put("channel", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle"));
			data.put("thumb", info.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
			data.put("qt", info.getJSONArray("items").getJSONObject(0).getJSONObject("contentDetails").getInt("itemCount"));
			return data;
			
		}
		
		else{
			return null;
		}
	}
	
	
	private static String fixUrl(String inUrl, Logger logger){
		String url = inUrl;
		
		String[] videoOptions = {"https://www.youtube.com/watch?v=", "https://youtube.com/watch?v=", "https://youtu.be/", "http://www.youtube.com/watch?v=", "http://youtube.com/watch?v=", "http://youtu.be/", "www.youtube.com/watch?v=", "youtube.com/watch?v=", "youtu.be/"};
		String[] playlistOptions = {"https://www.youtube.com/playlist?list=", "https://youtube.com/playlist?list=", "http://www.youtube.com/playlist?list=", "http://youtube.com/playlist?list=", "www.youtube.com/playlist?list=", "youtube.com/playlist?list="};
		
		for(String i : videoOptions){
			if(url.startsWith(i)){
				url = url.replace(i, "").replace("/", "");
				url = "https://www.youtube.com/watch?v=" + url.substring(0, 11);
				return url;
			}
		}
		
		for(String i : playlistOptions){
			if(url.startsWith(i)){				
				url = url.replace(i, "").replace("/", "");
				url = "https://www.youtube.com/playlist?list=" + url.substring(0, 34);
				return url;
			}
		}
		
		return "";
	}	
}
