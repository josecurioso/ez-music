package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import logger.Logger;

public class Downloader {
	
	public static String downloadFile(String fileURL, String saveDir, String fileName, Logger logger) throws IOException{
		int BUFFER_SIZE = 8192;
		URL url = new URL(fileURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
		connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
		connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connection.setRequestProperty("Accept-Language", "en-US,es-ES;q=0.8,es;q=0.6,de-DE;q=0.4,de;q=0.2,en;q=0.2");
		connection.setRequestProperty("Cookie", "sec=FOCUXSEHPDQS4NI46XIHXAU574; _ga=GA1.2.413961396.1518282873; _gid=GA1.2.1261233046.1518282873");
		
		
		int responseCode = connection.getResponseCode();
		
		//Check response code
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = connection.getHeaderField("content-disposition");
			String contentType = connection.getHeaderField("Content-Type");
			String saveFilePath = "";

			
			
			System.out.println(disposition);

			System.out.println(fileURL);
			
			
			//Check disposition
			if(disposition == null){
				connection.disconnect();
				return "failed";
			}
			
			//Extract filename from disposition	if it is not provided
			if(fileName.equals("")){
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			}
			
			//Validate filename
			fileName = validateFilename(fileName);
			
			//Assemble save path from directory and filename
			saveFilePath = saveDir + File.separator + fileName;
			
			logger.log("debug", "info", "####################################################################\n"
			+ "Download URL = " + fileURL + "\n"
			+ "Content-Type = " + contentType + "\n"
			+ "Filename = " + fileName + "\n"
			+ "Directory = " + saveDir + "\n"
			+ "Full path = " + saveFilePath + "\n"
			+ "####################################################################\n");

			//Sets input from the HTTP connection
			InputStream inputStream = connection.getInputStream();

			//Sets output to the specified file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath); 

			//Read input in chunks and write to output
			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			
			//Close both input/output streams and kill the connection
			outputStream.close();
			inputStream.close();
			connection.disconnect();
			
			return saveFilePath;
		} 
		else {
			logger.log("debug", "error", "FAILED: Server replied HTTP code: " + responseCode);
			
			//Kill the connection
			connection.disconnect();
			return "failed";
		}
	}

	private static String validateFilename(String file) {
		String[] ILLEGAL_CHARACTERS = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "\\", "<", ">", "|", "\"", ":" };
		
		for(int i=0; i<ILLEGAL_CHARACTERS.length; i++){
			file = file.replace(ILLEGAL_CHARACTERS[i], "");
		}
		
		return file;
	}
}
