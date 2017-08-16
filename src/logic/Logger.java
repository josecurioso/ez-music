package logic;

public class Logger {

	boolean activate;

	boolean debug;
	

	public Logger(){
		this.activate = true;
		this.debug = false;
	}
	
	public Logger(boolean activate, boolean debug){
		this.activate = activate;
		this.debug = debug;
	}
	
	public void setDebug(boolean debug){
		this.debug = debug;
	}
	
	
	public void log(String level, String status, String message){
		if(activate){
			if(status.equals("info")){
				if(level.equals("standard") || (level.equals("debug") && debug)){
					System.out.println(message);
				}
			}
			if(status.equals("error")){
				if(level.equals("standard") || (level.equals("debug") && debug)){
					System.err.println(message);
				}
			}
		}
	}
}
