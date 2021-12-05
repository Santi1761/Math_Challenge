package threads;

import javafx.application.Platform;
import ui.GUIController;


public class Timer extends Thread {
	
	private int time;
	private GUIController app;
	
	
	public Timer(int time,GUIController a) {
		
		this.time = time;
		app = a;
		
	}
		
	
	public void run() {
		
		while (time > 0) {
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						app.updateSeconds();
						
					}catch (Error e) {
						
					}
					
				}
			});
			
			time --;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					app.closeGame();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
	}
}
