package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSONTools {
	
	public static JSONObject GET(String URL){
		try{
    		JSONObject json = new JSONObject(IOUtils.toString(new URL(URL), Charset.forName("UTF-8")));
        	return json;
    	} catch (Exception e) {
    		System.out.println("Exception while accesing URL");
    	    return null;
    	}
	}
	
	public static JSONObject GETComplex(String url, String json, int timeout, String method, String referer){
		HttpURLConnection connection = null;
		try {
	        URL u = new URL(url);
	        connection = (HttpURLConnection) u.openConnection();
	        connection.setRequestMethod(method);
	         
	        //set the sending type and receiving type to json
	        //connection.setRequestProperty("Content-Type", "application/json");
	        //connection.setRequestProperty("Accept", "application/json");
	        
	        

			connection.setRequestProperty("Accept", "application/json, text/plain, */*");
			connection.setRequestProperty("Accept-Encoding", "br");
			connection.setRequestProperty("Accept-Language", "en-US,es-ES;q=0.8,es;q=0.6,de-DE;q=0.4,de;q=0.2,en;q=0.2");
			connection.setRequestProperty("Origin", referer);
			connection.setRequestProperty("Referer", referer + "/");
			connection.setRequestProperty("X-SecID", "YGMUHRFFUVJXARM3TOBOVZDO6Y");
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			connection.setRequestProperty("Cookie", "secid=YGMUHRFFUVJXARM3TOBOVZDO6Y");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
	        
	        
	 
	        connection.setAllowUserInteraction(false);
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	 
	        if (json != null) {
	            //set the content length of the body
	            connection.setRequestProperty("Content-length", json.getBytes().length + "");
	            connection.setDoInput(true);
	            connection.setDoOutput(true);
	            connection.setUseCaches(false);
	 
	            //send the json as body of the request
	            OutputStream outputStream = connection.getOutputStream();
	            outputStream.write(json.getBytes("UTF-8"));
	            outputStream.close();
	        }
	 
	        //Connect to the server
	        connection.connect();
	 
	        int status = connection.getResponseCode();
	        e("HTTP Client", "HTTP status code : " + status);
	        switch (status) {
	            case 200:
	            case 201:
            		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = bufferedReader.readLine()) != null) {
	                    sb.append(line + "\n");
	                }
	                bufferedReader.close();
	                e("HTTP Client", "Received String : " + sb.toString());
	                return new JSONObject(sb.toString());             
	        }
	 
	    } catch (MalformedURLException ex) {
	    	ex.printStackTrace();
	        e("HTTP Client", "Error in http connection" + ex.toString());
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	        e("HTTP Client", "Error in http connection" + ex.toString());
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	        e("HTTP Client", "Error in http connection" + ex.toString());
	    } finally {
	        if (connection != null) {
	            try {
	                connection.disconnect();
	            } catch (Exception ex) {
	                e("HTTP Client", "Error in http connection" + ex.toString());
	            }
	        }
	    }
	    return null;
	}
	
	
	
	
	public static void e(String A, String B){
		//System.out.println(A + B);
	}
	
	
}
