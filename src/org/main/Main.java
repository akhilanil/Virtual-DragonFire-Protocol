package org.main;
	
import java.net.DatagramSocket;

import org.communication.ProcessPackets;
import org.communication.Receive;
import org.communication.Send;
import org.communication.SendReceive;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=FXMLLoader.load(getClass().getResource("/org/main/Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/Initialize.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Initialize");
            stage.setScene(new Scene(root1));  
            stage.show();
			
            DatagramSocket dSocket = new DatagramSocket(8080);
            SendReceive sr = new SendReceive(dSocket, new Instance().getInstanceofConnectionDetails());
            new Send(sr);
            new Receive(sr);
            new ProcessPackets(sr);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}



	
	
