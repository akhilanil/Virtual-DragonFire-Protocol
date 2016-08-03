package support.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public  class DialogView implements ErrorDialog {

	@Override
	public void createErrorDialog(String title, String header, String content) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}
	
	
	
	

}
