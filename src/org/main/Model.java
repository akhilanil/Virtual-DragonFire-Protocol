package org.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.model.*;


public class Model {
	
	Map <String, ArrayList<String>> neighbouringstations;
	ConnectionDetails connectionDetails;
	
	public Model(){
		connectionDetails = new ConnectionDetails();
		new Instance().setInstanceofConnectionDetails(connectionDetails);
	}
	
	public void setNeighbouringStations(ArrayList<String> station1, ArrayList<String> station2){
		ArrayList<String> station;
		neighbouringstations = new HashMap<String, ArrayList<String>>();
		if(station1 != null){
			
			station = new ArrayList<String>();
			station.add(station1.get(1));
			station.add(station1.get(2));
			
			neighbouringstations.put(station1.get(0), station);
			
		}
		
		if(station2 != null){
			
			station = new ArrayList<String>();
			station.add(station2.get(1));
			station.add(station2.get(2));
			
			neighbouringstations.put(station2.get(0), station);
			
		}
		connectionDetails.setNeighbouringStations(neighbouringstations);
		
	}
	
	
	public void setAllStations(ArrayList<String> availableStations) {
		
		connectionDetails.setAllStations(availableStations);
		
	}
	
	public void setDeviceID(String id) {
		connectionDetails.setDeviceID(id);
	}
	
	
	

}
