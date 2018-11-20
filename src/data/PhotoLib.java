package data;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
 import javafx.stage.Stage;


public class PhotoLib extends Application {
	
	static Stage window;
	static Scene libScene;
	
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		window = primaryStage;
		/*---- Loading in FXML and starting up the scene ----*/
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(getClass().getResource("photolibrary.fxml"));
		AnchorPane root2 = (AnchorPane) loader2.load();
		
		libScene = new Scene(root2);
		
		PhotosController libraryController = loader.getController();
		libraryController.start(primaryStage);
		
		
		//Move menu bar to next scene (so probably within change scene)
		MenuItem yourAccount = new MenuItem("Your Account");
        MenuItem adminControls = new MenuItem("Admin Controls");
        MenuItem logout = new MenuItem("Logout");
        MenuItem quitApp = new MenuItem("Quit Application");
	
        //FileInputStream input = new FileInputStream("resources/images/iconmonstr-menu-5-32.png");
        //Image image = new Image(input);
        //ImageView imageView = new ImageView(image);
        //MenuButton userButton = new MenuButton("User Settings", imageView, yourAccount, adminControls, logout, quitApp);
        
        MenuButton userButton = new MenuButton("User Settings", null, yourAccount, adminControls, logout, quitApp);
        
        
		Scene scene = new Scene(root);
		root.getChildren().addAll(userButton);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Library Login");  
		primaryStage.show();
		
	}
	
	public void changeScene(String fxml,Stage primaryStage) throws IOException{
	    Parent pane = FXMLLoader.load(
	           getClass().getResource(fxml));

	   Scene scene = new Scene( pane );
	   primaryStage.setScene(scene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
