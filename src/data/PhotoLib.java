package data;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
 import javafx.stage.Stage;


public class PhotoLib extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		
		/*---- Loading in FXML and starting up the scene ----*/
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("photolibrary.fxml"));
		AnchorPane root = loader.load();
		
		PhotosController libraryController = loader.getController();
		libraryController.start(primaryStage);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Library");  
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
