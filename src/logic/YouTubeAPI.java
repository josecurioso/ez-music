package logic;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouTubeAPI {
	public final static String BASE_URL = "https://www.googleapis.com/youtube/v3/";
	public final static String DEVELOPER_KEY = "AIzaSyB-0w6c7lz4DNWcS4blcHFrb3xl_AkGsvk";

	public static ArrayList<String> getLinks(String playListLink) throws JSONException{
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
		data = JSONTools.GET(callUrl);
		
		int vid_num = (int) data.getJSONObject("pageInfo").get("totalResults");
		int counter = 0;
		
		while(counter < vid_num){
			callUrl = BASE_URL + "playlistItems?key=" + DEVELOPER_KEY + "&part=snippet&playlistId=" + playListCode + "&pageToken=" + pageToken;
			data = JSONTools.GET(callUrl);
			
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
}
