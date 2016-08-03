package org.communication;

public class Send implements Runnable {

	SendReceive sr;
	
	public Send(SendReceive sr){
		Thread thread = new Thread(this, "Send");
		this.sr = sr;
		thread.start();
	}
	public void run() {
		// TODO Auto-generated method stub
		while(true)
			sr.sendPacket();
		
	}
	
	
	
	

}
