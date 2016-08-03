package org.main;

import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import support.utils.*;

public class InitialController {
	
	
	
	ArrayList <String> station1;
	ArrayList <String> station2;
	ArrayList <String> allstations;
	
	Model m = new Model();

	@FXML
	private ComboBox<String> destbox;
	
	@FXML
	private TextField name1,name2,ip1,ip2,port1,port2,myname;
	
	@FXML
	ToggleGroup DeviceType;
	
	@FXML
	private Button done;
	
	@FXML
	private RadioButton host,station;
	
	@FXML
	private TextField hname1,hname2,hname3,hname4;
	
	
	public void submitDetails(ActionEvent e) {
		int flag = 0;
		if(!name1.getText().isEmpty() && !ip1.getText().isEmpty() && !port1.getText().isEmpty()) {
			
			station1 = new ArrayList<String> ();
			
			station1.add(name1.getText());
			station1.add(ip1.getText());
			station1.add(port1.getText());
			
			flag=1;
			
		}
		if(!name2.getText().isEmpty() && !ip2.getText().isEmpty() && !port2.getText().isEmpty()) {
			
			station2 = new ArrayList<String> ();
			station2.add(name2.getText());
			station2.add(ip2.getText());
			station2.add(port2.getText());
			
			flag=1;
			
		}
		
		if(host.isSelected()){
			
			if(hname1.getText().isEmpty() || hname2.getText().isEmpty() || hname3.getText().isEmpty() || hname4.getText().isEmpty())
				flag = 2;
			else{
				allstations = new ArrayList<String>();
				allstations.add(hname1.getText());
				allstations.add(hname2.getText());
				allstations.add(hname3.getText());
				allstations.add(hname4.getText());
				
				
			}
				
		}
		
		if(flag == 0)
			new DialogView().createErrorDialog("Empty Fields", "Fields Cannot be Empty", "Fill atleast one station");
		else if(flag == 2)
			new DialogView().createErrorDialog("Empty Fields", "Group ID Fields Cannot be Empty", "Fill group ID all 4 Stations");
		else{
			m.setNeighbouringStations(station1, station2);
			if(host.isSelected())
				m.setAllStations(allstations);
			m.setDeviceID(myname.getText());
			Stage stage = (Stage) done.getScene().getWindow();
		    stage.close();
		}
			
		
	}
	
	
	public void hostSelected(ActionEvent e){
		
		boolean state = host.isSelected();
		
		hname1.setDisable(!state);
		hname2.setDisable(!state);
		hname3.setDisable(!state);
		hname4.setDisable(!state);
		
		
		
	}
	
	
	
	

}
