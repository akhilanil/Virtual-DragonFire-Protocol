package org.communication;

public class ProcessPackets implements Runnable {

	SendReceive sr;
	
	public ProcessPackets(SendReceive sr) {
		this.sr = sr;
		Thread thread = new Thread(this,"ProcessPacket");
		thread.start();

	}
	
	public void run() {
		
		while(true)
			sr.processPacket();
	}

}
