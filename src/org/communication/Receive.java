package org.communication;

public class Receive implements Runnable {
	SendReceive sr;
	public Receive(SendReceive sr) {
		Thread thread = new Thread(this,"Receive");
		this.sr = sr;
		thread.start();
	}
	
	public void run() {
		while(true)
			sr.receivePacket();
		
	}

}
