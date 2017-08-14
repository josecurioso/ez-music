package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class Downloader implements Runnable{

	String videoURL;
	String requestPATH;
	
	public Downloader(String videoURL, String requestPATH){
		this.videoURL = videoURL;
		this.requestPATH = requestPATH;
	}
	
	@Override
	public void run() {		
		//Try 2 options
		try{
			optionA(videoURL, requestPATH);
		}
		catch(FailedDownloadException e){
			try{
				optionB(videoURL, requestPATH);
			}
			catch(FailedDownloadException i){
				System.out.println("File NOT downloaded on any try: " + videoURL);
			}
		}
	}
	
	public void optionA(String videoURL, String requestPATH) throws FailedDownloadException{
		JSONObject data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL);
		String fileName = "";
		String downloadURL = "";
		try{
			fileName = data.getString("title") + ".mp3";
			downloadURL = data.getString("link");
		}
		catch(JSONException e){
			throw new FailedDownloadException();
		}
		
		try{
			if(!downloadFile(downloadURL, requestPATH, fileName)){
				throw new FailedDownloadException();
			}
		}
		catch(IOException e){
			
		}
		System.out.println("File downloaded on first try");
		
	}
	
	public void optionB(String videoURL, String requestPATH) throws FailedDownloadException{
		JSONObject data = JSONTools.GET("http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + videoURL);
		String fileName = "";
		String downloadURL = "";
		try{
			fileName = data.getString("title") + ".mp3";
		}
		catch(JSONException e){
			throw new FailedDownloadException();
		}
		//downloadURL = //new method

		try{
			if(!downloadFile(downloadURL, requestPATH, fileName)){
				throw new FailedDownloadException();
			}
		}
		catch(IOException e){
			
		}
		System.out.println("File downloaded on second try");
	}
	
	
	public boolean downloadFile(String fileURL, String saveDir, String fileName) throws IOException{
		int BUFFER_SIZE = 4096;
		int contentLength = -1;
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			contentLength = httpConn.getContentLength();
			
			if(disposition == null){
				httpConn.disconnect();
				return false;
			}

			else if (disposition != null && !isFilenameValid(fileName) && fileName != "") {
					// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			}
			
			/*
			System.out.println("####################################################################");
			System.out.println("Download URL = " + fileURL);
			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);
			System.out.println("####################################################################");
			*/

			/*
			System.out.print("####################################################################\n"
			+ "Download URL = " + fileURL + "\n"
			+ "Content-Type = " + contentType + "\n"
			+ "Content-Disposition = " + disposition + "\n"
			+ "Content-Length = " + contentLength + "\n"
			+ "fileName = " + fileName + "\n"
			+ "####################################################################\n");

			 */

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();
			if(contentLength == -1){
				httpConn.disconnect();
				return false;
			}
			else{
				httpConn.disconnect();
				return true;
			}
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
			httpConn.disconnect();
			return false;
		}
	}

	public boolean isFilenameValid(String file) {
		File f = new File(file);
		try {
			return f.getCanonicalFile().getName().equals(file);
		} catch (IOException e) {
			return false;
		}
	}
	
	
	
	
}
