package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.control.Alert.AlertType;

import java.io.FileInputStream;



/*
 * Photo Library Assignment
 * CS213 Soft Meth
 * 
 * Project Done by:
 * Tiffany Moral (tm558)
 * Jake Van Osten (jrv89)
 * 
 * 
 */


public class PhotosController {

	
	@FXML Button signinButt;
	@FXML Button newUserButt;
	@FXML TextField loginField;
	@FXML TextField newField;
	@FXML MenuButton  userButton;
	@FXML TextField searchTextField;
	@FXML TilePane imageDisplay;
	
	public void start(Stage primaryStage) {
			String path = "C:\\Users\\jakev\\OneDrive\\Documents\\photos54\\src\\data\\Images\\stock\\";
			
			File folder = new File(path);
			File[] fileList = folder.listFiles();

			for (File file : fileList) {
        		final Image image = new Image("File:stock/dolphins.jpg");
                imageDisplay.getChildren().addAll(new ImageView(image));
			}
		
	}
	
	private ImageView createImageView(final File imageFile) {
        // DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
        // The last two arguments are: preserveRatio, and use smooth (slower)
        // resizing

        ImageView imageView = null;
        final Image image = new Image("File:" + imageFile, 150, 0, true, true);
		imageView = new ImageView(image);
		imageView.setFitWidth(150);
        
        return imageView;
    }

	public void signin(ActionEvent e) throws FileNotFoundException {
		if (loginField.getText().isEmpty()) {
			emptyFieldAlert();
		}else {
			//have to check if username is in userList.txt, if it is the changestage to library, if isnt then  
				File savedUsers = new File("userList.txt");
			
				@SuppressWarnings("resource")
				Scanner fileScan = new Scanner(savedUsers).useDelimiter("\n");
				while(fileScan.hasNext()) {
					if(loginField.getText().equals(fileScan.next())) { //username matches one in saved list
						Stage nextWindow = PhotoLib.window;
						nextWindow.setScene(PhotoLib.libScene);
						return;
					}
				}
				userNotFoundAlert();
		}	
	}
	
	
	public void newuser(ActionEvent e) throws IOException {
		if (newField.getText().isEmpty()) {
			emptyFieldAlert();
		}else {
			//have to check if username is in userList.txt, if it is the changestage to library, if isnt then  
				File savedUsers = new File("userList.txt");
			
				@SuppressWarnings("resource")
				Scanner fileScan = new Scanner(savedUsers).useDelimiter("\n");
				while(fileScan.hasNext()) {
					if(newField.getText().equals(fileScan.next())) { //username matches one in saved list
						//switch stages and open up previously uploaded pictures
						userAlreadyExistsAlert();
						return;
					}
				}
				System.out.println("Created account!");
				addUser(newField.getText());
				Stage nextWindow = PhotoLib.window;
				nextWindow.setScene(PhotoLib.libScene);
				//now have to add name to userList, switch stages and open up new library
		}
	}
	
	@SuppressWarnings("resource")
	private void addUser(String userName) throws IOException {
		 File savedUsers = new File("userList.txt");
		 PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("userList.txt",true)));
		 Scanner fileScan = new Scanner(savedUsers).useDelimiter("\n");
			while(fileScan.hasNext()) {
				fileScan.next();
			}
			writer.print("\n" + userName);
		    writer.close();
	}

	private void userAlreadyExistsAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - User already exists");
		      alert.setHeaderText("Try a new username.");
		      alert.showAndWait();
	}

	private void userNotFoundAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - User not found");
		      alert.setHeaderText("This is not a registered username.");
		      alert.showAndWait();
	}

	private void emptyFieldAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - Empty Field");
		      alert.setHeaderText("Please type in a valid username.");
		      alert.showAndWait();
	}
	
	private void emptySearchFieldAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - Empty Field");
		      alert.setHeaderText("Please type in a valid search field.");
		      alert.showAndWait();
	}
	
	
	public void displayStock(){
		
		if(loginField.getText().equals("stock")){
			
		}
		
		
		
	}
	
	public void displayMenu() {
		
		
	}
	
	public void displayUserInfo() {
		
		
	}
	
	public void giveAdminControl() {
		
		
	}
	
	public void logout() {
		
		
	}
	public void quitApplication() {
	
	
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
