package org.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.main.View;

public class ConnectionDetails {
	
	public Map <String, ArrayList<String>> neighbouringstations;
	public ArrayList<String> availablestations;
	View view;
	boolean ishost;
	String deviceid;
	
	public ConnectionDetails() {
		this.ishost = false;
		neighbouringstations = new HashMap<String, ArrayList<String>>();
		availablestations = new ArrayList <String>(); 
		view = new View();
	}
	
	public void setDeviceID(String id){
		
		this.deviceid = id;
		
	}
	public String getDeviceID(){
		
		return this.deviceid;
		
	}
	
	public void setNeighbouringStations(Map <String, ArrayList<String>> neighbouringstations) {
		this.neighbouringstations = neighbouringstations;
	}
	
	
	public void setAllStations(ArrayList<String> availableStations) {
		
		this.availablestations.addAll(availableStations);
		isHost(true);
		
	}
	
	private void isHost(boolean state) {
		this.ishost = state;
	}
	
	public ArrayList<String> getGroupID() {
		System.out.println("to ret: "+availablestations);
		return availablestations;
	}
	

}
