package org.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.model.ConnectionDetails;

import support.utils.PacketBuilder;
import support.utils.PacketType;

public class SendReceive {
	
	private Map<String, String> packetTracer; //contains packets whose acknowledgement in awiated
	private Map<String, InetAddress> ipTracer;
	private int sendState = 0;
	DatagramSocket socket;
	private String packet = null;
	PacketType packetType;
	InetAddress destip = null;
	String deviceID;
	private boolean flood = false, retack = false, start = false;
	ConnectionDetails connectionDetails;
	private String groupID; 
	String message,packetID;
	
	public SendReceive(DatagramSocket socket, ConnectionDetails connectionDetails){
		this.socket = socket;
		this.deviceID = connectionDetails.getDeviceID();
		this.connectionDetails = connectionDetails;
		packetTracer = new HashMap<String, String>();
		ipTracer = new HashMap<String, InetAddress>();
	}
	
	public synchronized void receivePacket(){
		
		while(sendState != 0) {
			try {
				wait();
			} catch(InterruptedException e){e.printStackTrace();}
		}
		byte[] rbyte = new byte[1024];
		
		DatagramPacket receivePacket= new DatagramPacket(rbyte,rbyte.length);
		setIPAddress(receivePacket.getAddress());
		try{
			if(start){
				socket = new DatagramSocket(8080);
				start = false;
			}
			socket.receive(receivePacket);
			byte[] sbyte = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
			this.setPacket(new String(sbyte));
			this.sendState = 1;
			notify();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	
	public synchronized void processPacket() {
		
		while(sendState != 1) {
			try {
				wait();
			} catch(InterruptedException e){e.printStackTrace();}
		}
		String packet = this.getPacket();
		String[] messageList = packet.split("`934~");
		
		this.packetID = messageList[0];
		int ack = Integer.parseInt(messageList[1]);
		int ping = Integer.parseInt(messageList[2]);
		this.groupID = messageList[3];
		this.setMessage(messageList[4]);
		
		if( ack == 0 && ping == 0 ){ // Data packet
			this.setPacketType(PacketType.DATA_PACKET);
			if(this.groupID == this.deviceID){
				flood = false;
				//this.setMessage("");
			}
			else
				flood = true;
				
			//process data ( echo it back )
		
		}

		else if( ack == 0 && ping == 1 ) { //Ping Packet
			this.setPacketType(PacketType.PING_PACKET);
			//send the packet to all reachable nodes
			
			
		}
		else if( ack == 1 && ping == 0 ) { //Acknowledgement for Data Packet. Send the packet back to host
			
			this.setPacketType(PacketType.DATA_ACK_PACKET);
			
			this.retack = true;
			
			
		}
		else{							//Acknowledgement for Ping Packet
			this.setPacketType(PacketType.PING_ACK_PACKET);
			//send the packet back to host
		}
		this.sendState = 2;
		notify();
		
	}
	
	
	
	
	public  synchronized void sendPacket() {
		
		while(sendState != 2) {
			try {
				wait();
			} catch(InterruptedException e){e.printStackTrace();}
		}
		
		
		
		byte [] tosend = new byte[1024];
		
		if(this.retack){
			if(!packetTracer.containsKey(this.packetID))
				System.out.println("Received Ack: "+this.getPacket());
			else{
				String pid = packetTracer.get(this.packetID);
			
				String packet = new PacketBuilder().generatePacket(PacketType.DATA_ACK_PACKET, this.getMessage(), null, pid);
				tosend = packet.getBytes();
			
			
				DatagramPacket sendPacket=new DatagramPacket(tosend, tosend.length);
				sendPacket.setAddress(ipTracer.get(pid));
				sendPacket.setPort(8080);
				try {
					socket.send(sendPacket);
				} catch (IOException e) {e.printStackTrace();}
				this.retack = false;
			}
		}
		else if(this.flood){
			flood = false;
			DatagramPacket sendPacket = null;
			floodMessage(sendPacket, tosend);
			
		}
		else{
			String packet = new PacketBuilder().generatePacket(PacketType.DATA_ACK_PACKET, this.getMessage(), null, this.packetID);
			tosend = packet.getBytes();
			
			
			DatagramPacket sendPacket=new DatagramPacket(tosend, tosend.length);
			sendPacket.setAddress(this.getIPAddress());
			sendPacket.setPort(8080);
			try {
				socket.send(sendPacket);
			} catch (IOException e) {e.printStackTrace();}
		}

		
		
		sendState = 0;
		notify();
	}
	
	
	private void floodMessage(DatagramPacket sendPacket, byte[] tosend){
		
		 
		
		
		
		Iterator <Map.Entry<String, ArrayList<String>>> iterator = connectionDetails.neighbouringstations.entrySet().iterator();
		while( iterator.hasNext() ) {
			
			String packet = new PacketBuilder().generatePacket(this.getPacketType(), this.getMessage(), this.groupID, this.deviceID);//new packet with new counter is generated

			packetTracer.put(packet.split("`934")[0], this.packetID);
			ipTracer.put(packet.split("`934")[0],this.getIPAddress());
			
			tosend = packet.getBytes();
			sendPacket=new DatagramPacket(tosend, tosend.length);
			sendPacket.setPort(8080);
			
			Map.Entry<String, ArrayList<String>> pair = (Map.Entry <String, ArrayList<String>>) iterator.next();
			ArrayList<String> list = pair.getValue();
			
			try {
				sendPacket.setAddress(InetAddress.getByName(list.get(0)));
				socket.send(sendPacket);
			} catch (IOException e) {e.printStackTrace();}
			
			
			
		}
	}
	
	public void interrupt(String data, String groupid){
		socket.close();
		start = true;
		sendState = 4;
		try {
			this.socket = new DatagramSocket(8080);
			byte[] tosend = new byte[1024];
			
			tosend = new PacketBuilder().generatePacket(PacketType.DATA_PACKET, data, groupid, connectionDetails.getDeviceID()).getBytes();
			DatagramPacket sendPacket = new DatagramPacket(tosend, tosend.length);
			floodMessage(sendPacket, tosend);
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
		sendState = 0;
	}
	
	
	public void setPacket(String packet){
	
		this.packet = packet;
	}
	
	public String getPacket(){
		return this.packet;
	}
	
	public void setPacketType(PacketType packetType){
	
		this.packetType = packetType;
	}
	
	public PacketType getPacketType(){
		
		return this.packetType;
	}
	
	public InetAddress getIPAddress(){
		
		return this.destip;
	}
	
	public void setIPAddress(InetAddress ip){
		this.destip = ip;
	}
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	
}
