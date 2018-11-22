package model;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import controller.LibController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class Photos extends Application {
	
	public static Stage window;
	public static Scene libScene;
	static TilePane tilePane;
	public static Scene loginScene;
	public static LibController libController;
	public static LoginController loginController;
	
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		window = primaryStage;
		/*---- Loading in FXML and starting up the scene ----*/
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(getClass().getResource("/view/photolibrary.fxml"));
		AnchorPane root2 = (AnchorPane) loader2.load();
		
		loginScene = new Scene(root);
		libScene = new Scene(root2);
		
		
		
		tilePane = new TilePane();
		
		
		
		
		loginController = loader.getController();
		libController = loader2.getController();
		
		
		loginController.start(primaryStage);
		

		primaryStage.setScene(loginScene);
		primaryStage.setTitle("Photo Library");  
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
