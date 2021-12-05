package threads;

import javafx.application.Platform;
import ui.GUIController;


public class BarThread extends Thread {
	
	private GUIController app;
	private int time;
	
	
	public BarThread(GUIController a) {
		
		app = a;
		time = 100;
	}
	
	
	public void run(){		
		
		while (time > 0) {
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						
						app.updateBar(time);						
					} catch (Exception e) {					
						
					}					
				}
			});
			
			try {
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			time --;			
		}
	}

}
