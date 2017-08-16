package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
	
	public static String downloadFile(String fileURL, String saveDir, String fileName, Logger logger) throws IOException{
		int BUFFER_SIZE = 8192;
		URL url = new URL(fileURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		int responseCode = connection.getResponseCode();
		
		//Check response code
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String disposition = connection.getHeaderField("Content-Disposition");
			String contentType = connection.getHeaderField("Content-Type");
			String saveFilePath = "";

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
			
			/*
			String contentType = httpConn.getContentType();
			
			System.out.println("####################################################################");
			System.out.println("Download URL = " + fileURL);
			System.out.println("Content-Type = " + contentType);
			System.out.println("fileName = " + fileName);
			System.out.println("####################################################################");

			*/
			
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

	public static String validateFilename(String file) {
		String[] ILLEGAL_CHARACTERS = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "\\", "<", ">", "|", "\"", ":" };
		
		for(int i=0; i<ILLEGAL_CHARACTERS.length; i++){
			file = file.replace(ILLEGAL_CHARACTERS[i], "");
		}
		
		return file;
	}
}
