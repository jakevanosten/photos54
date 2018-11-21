package data;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class LibController {

	Stage primaryStage;
	
	@FXML MenuItem logoutButt;
	@FXML MenuButton  userButton;
	@FXML TextField searchTextField;
	@FXML TilePane imageDisplay;

	
	public void initialize() {
		imageDisplay.setHgap(10);
		imageDisplay.setVgap(10);
        String path = "C:\\Users\\jakev\\OneDrive\\Documents\\photos54\\src\\data\\Images\\stock\\";
		
		File folder = new File(path);
		File[] fileList = folder.listFiles();
		for (File file : fileList) {
			final Image image = new Image(file.toURI().toString(), 150,0,true,true);
			ImageView iv = new ImageView(image);
			iv.setImage(image);
			iv.setFitWidth(150);
	    	imageDisplay.getChildren().add(iv);
		}
    }
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
	
	}
	
	
	
	private void emptySearchFieldAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - Empty Field");
		      alert.setHeaderText("Please type in a valid search field.");
		      alert.showAndWait();
	}
	
	
	public void displayMenu() {
		
		
	}
	
	public void displayUserInfo() {
		
		
	}
	
	public void giveAdminControl() {
		
		
	}
	
	public void logout(ActionEvent e) {
		Stage nextWindow = PhotoLib.window;
		nextWindow.setScene(PhotoLib.loginScene);
		PhotoLib.loginController.start(primaryStage);
	}
	
	
	public void quitApplication() {
		//saves files to disk before closing out the stage
	
	}
	
	//Search Field Methods
	public void clearSearchField() {
		searchTextField.clear();
	}
	
	public void searchLibrary(ActionEvent e){
		String searchInput;
		
		if (searchTextField.getText().isEmpty()) {
			emptySearchFieldAlert();
		}else {
			searchInput = searchTextField.getText();
			
			//compare to items with date range
			
			//compare to items with same tag
			
				
			
		}
	}
	
	
	//Album Menu Methods
	public void createAlbum() {
		
		
	}
	
	public void renameAlbum() {
		
		
	}
	
	public void deleteAlbum() {
		
		
	}
	
	public void openAlbum() {
		
	}
	
	
}