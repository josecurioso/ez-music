package logic;

public class Pruebas {

	public static void main(String[] args) {
		Logger logger = new Logger();
		
		
		String trialURL = "www.youtube.com/playlist?list=PL9fZWvwimefvv0TqnjaBHcWvYhQM3po";
		
		
		System.out.println(YouTubeAPI.fixUrl(trialURL, logger));

	}

}
