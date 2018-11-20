package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


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

	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		
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
						//switch stages and open up previously uploaded pictures
						System.out.println("User was found");
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
}
