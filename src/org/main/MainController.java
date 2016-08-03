package org.main;


import java.util.ArrayList;


import support.utils.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class MainController {
	
	
	@FXML
	private RadioButton ping,data;
	
	@FXML
	private ComboBox<String> destbox;
	
	@FXML
	private TextField dataField ;
	
	
	public void selectPacketType(ActionEvent e){
		
		boolean state = data.isSelected();
		
		if(state){
			
			ArrayList<String> id = new Instance().getInstanceofConnectionDetails().getGroupID();
			destbox.getItems().clear();
			destbox.getItems().addAll(id);
			
		}
		dataField.setDisable(!state);
		destbox.setDisable(!state);
	}
	
	public void sendPacket(ActionEvent e) {
		System.out.println(dataField.getText());
		if(data.isSelected()) {
			if(!destbox.getValue().equals("Destination")){
				//String packet = new PacketBuilder().generatePacket(PacketType.DATA_PACKET, dataField.getText(), destbox.getValue());
				//System.out.println("Packet: "+packet);
			}
			else
				new DialogView().createErrorDialog("No selection", "Invalid Selection for Destination", "Select a group ID");
		}
		else if(ping.isSelected()){
			//String packet = new PacketBuilder().generatePacket(PacketType.DATA_PACKET, null, null);
			//System.out.println("Packet: "+packet);
		}
		
	}
	

}
