package org.main;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;



public class View {
	
	@FXML
	private ComboBox<String> destbox;
	
	public void setGroupID(ArrayList<String> id){
		
		//new MainController().setGroupID(id);
	}

}
