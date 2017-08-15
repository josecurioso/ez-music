package logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
	
	public static boolean downloadFile(String fileURL, String saveDir, String fileName) throws IOException{
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
			
			
			int index = disposition.indexOf("filename=");
			if (index > 0) {
				fileName = disposition.substring(index + 10, disposition.length() - 1);
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
			String saveFilePath = saveDir + File.separator + fileName.replace("\"", "").replace("/","").replace("\\","");

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

	public static boolean isFilenameValid(String file) {
		File f = new File(file);
		try {
			return f.getCanonicalFile().getName().equals(file);
		} catch (IOException e) {
			return false;
		}
	}
}
