package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Photos;
import model.Tag;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;

import model.*;

public class LibController {

	Stage primaryStage;
	String user;
	Photo selectedPhoto;
	String newType = "";
	String newContent = "";
	private static final String directory = "data";
	private static final String filename = "photoLib";
	File[] fileList;
	private ObservableList<Photo> obsList = FXCollections.observableArrayList();
	private TextArea txtArea;
	private Text actionStatus;
	private static final String defaultFileName = "temporary.jpg";
	private Stage savedStage;
	private ObservableList<Album> albumListView = FXCollections.observableArrayList();;
	
	@FXML MenuItem logoutButt;
	@FXML MenuButton  userButton;
	@FXML TextField searchTextField;
	@FXML TilePane imageDisplay;
	@FXML MenuItem adminControls;
	@FXML TextField captionField;
	@FXML TextField dateField;
	@FXML MenuItem addTagButt;
	@FXML MenuItem deleteTagButt;
	@FXML MenuItem deletePhotoButt;
	@FXML MenuItem addPhotoButt;
	@FXML MenuItem displayButt;
	@FXML Button saveEditButt;
	@FXML TableView<Tag> tagTable;
	@FXML TableColumn<Tag, String> typeCol;
	@FXML TableColumn<Tag, String> contentCol;
	@FXML TextField editTypeField;
	@FXML TextField editContentField;
	@FXML ImageView displayBox;
	@FXML TextField displayCap;
	@FXML Button prevButt;
	@FXML Button nextButt;
	@FXML MenuItem createOption;
	@FXML MenuItem renameOption;
	@FXML MenuItem deleteOption;
	@FXML MenuItem openOption;
	

	
	
	public void initialize() {
		imageDisplay.setHgap(10);
		imageDisplay.setVgap(10);
		displayCap.setDisable(true);
		
        //if(user.equals("stock")){
        	String path = "src/Images/stock/";
		
        	File folder = new File(path);
        	File[] fileList = folder.listFiles();
        	for (File file : fileList) {
        		final Image image = new Image(file.toURI().toString(), 150,0,true,true);
        		Photo newPhoto = new Photo(file.getName(),image,new GregorianCalendar(), FXCollections.observableArrayList());
        		obsList.add(newPhoto);
        		ImageView iv = new ImageView(image);
        		iv.setImage(image);
        		iv.setFitWidth(150); 
        		imageDisplay.getChildren().add(iv);
	    	
        		iv.setOnMouseClicked(event -> {
        			captionField.setText(newPhoto.getCaption());
        			dateField.setText(newPhoto.getDate());
        			displayTags(newPhoto);
        			selectedPhoto = newPhoto;
        			addTagButt.setDisable(false);
        			deleteTagButt.setDisable(false);
        			deletePhotoButt.setDisable(false);
        		});
        	}
		
		
		
	
		
		
    }
	
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.centerOnScreen();
		user = LoginController.userName;
		if(user.equals("admin")) {
			adminControls.setDisable(false);
		}
		
	}
	
	private SimpleStringProperty tagTypeProperty = new SimpleStringProperty();
	private SimpleStringProperty tagContentProperty = new SimpleStringProperty();
	
    public void setTagType(String tagType) {
        this.tagTypeProperty.set(tagType);
    }
    
    public String getTagType() {
        return tagTypeProperty.getValue();
    }
    
    public SimpleStringProperty typeProperty() {
	    return this.tagTypeProperty;
	}
    
    public void setTagContent(String tagContent) {
        this.tagContentProperty.set(tagContent);
    }

    public String getTagContent() {
        return tagContentProperty.getValue();
    }
    
    public SimpleStringProperty contentProperty() {
        return this.tagContentProperty;
    }
	
    
	private void displayTags(Photo newPhoto) {
		ObservableList<Tag> tagList = newPhoto.tags;
		tagTable.setItems(tagList);
	}
	
	public void editCaption(MouseEvent e) {
        if(e.getButton().equals(MouseButton.PRIMARY)){
            if(e.getClickCount() == 2){
                 captionField.setEditable(true);//On double click set editable to true

                 captionField.setOnKeyPressed(event ->{
                	 if(event.getCode().toString().equals("ENTER")){
                		 selectedPhoto.caption = captionField.getText();
                		 selectedPhoto.updateDate();
                		 dateField.setText(selectedPhoto.getDate().toString());
                		 captionField.setEditable(false);//On enter set editable to false
                	 }
                 });
            }
        }
    }
	
	public void addTag(ActionEvent e) {
		newType = editTypeField.getText();
		newContent = editContentField.getText();
		if(newType.equals("") || newContent.equals("")){
			blankTagAlert();
			return;
		}
		Tag newTag = new Tag(newType, newContent);
		for(Tag t : selectedPhoto.getTags()) {
			if(newTag.equals(t)) {
				redundantTagAlert();
				editTypeField.clear();
				editContentField.clear();
				return;
			}
		}
		selectedPhoto.getTags().add(newTag);
		selectedPhoto.updateDate();
		dateField.setText(selectedPhoto.getDate().toString());
		editTypeField.clear();
		editContentField.clear();
	}
	
	private void redundantTagAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - Tag Already Exists");
		      alert.setHeaderText("Please type in a new tag.");
		      alert.showAndWait();	
	}
	
	private void blankTagAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - No tag present");
		      alert.setHeaderText("Please type in new tag type AND content before trying to add.");
		      alert.showAndWait();		
	}
	
	public void deleteTag(ActionEvent e) {
		Tag selectedTag = tagTable.getSelectionModel().getSelectedItem();
		ObservableList<Tag> tagList = selectedPhoto.getTags();
	    int index = tagList.indexOf(selectedTag);
	    
	    if(tagList.size() == 1) {
	    	tagList.remove(selectedTag);
	    	selectedPhoto.updateDate();
   		 	dateField.setText(selectedPhoto.getDate().toString());
	    }
	    else if(tagList.size() > index+1) { //can select row after deleted
	    	tagTable.getSelectionModel().clearAndSelect(index+1);
	    	tagList.remove(selectedTag);
	    	selectedPhoto.updateDate();
   		 	dateField.setText(selectedPhoto.getDate().toString());
	    }else{
	    	tagTable.getSelectionModel().clearAndSelect(index-11);
	    	tagList.remove(selectedTag);
	    	selectedPhoto.updateDate();
   		 	dateField.setText(selectedPhoto.getDate().toString());
	    } 
	}
	
	private class SaveButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showSaveFileChooser();
		}
	}
	
	

	
	private void showSaveFileChooser() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.setInitialFileName(defaultFileName);
		File savedFile = fileChooser.showSaveDialog(savedStage);

		if (savedFile != null) {

			try {
				saveFileRoutine(savedFile);
			}
			catch(IOException e) {
			
				e.printStackTrace();
				actionStatus.setText("An ERROR occurred while saving the file!" +
						savedFile.toString());
				return;
			}
			
			actionStatus.setText("File saved: " + savedFile.toString());
		}
		else {
			actionStatus.setText("File save cancelled.");
		}
	}
	
	private void saveFileRoutine(File file)
			throws IOException{
		// Creates a new file and writes the txtArea contents into it
		String txt = txtArea.getText();
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(txt);
		writer.close();
	}
	
	public void addPhoto(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		File dir = new File(user);
		dir.mkdir();
	
		fileChooser.setTitle("Select Photo File");
		fileChooser.setInitialDirectory(dir);
		
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		fileChooser.setInitialFileName("temp");
		
		File selectedFile = fileChooser.showOpenDialog(null);
		File savedFile = fileChooser.showSaveDialog(primaryStage);
	
		if(savedFile != null){
			try{
				saveFileRoutine(savedFile);
			}catch(IOException ex){
				
				ex.printStackTrace();
				
			}
		}
	
		/*
		if(selectedFile != null){
			Dialog.setText("File selected: " + selectedFile.getName());
		}
		*/
	}
	
	public void deletePhoto(ActionEvent e) {
		
	}
	
	public void displayPhoto(ActionEvent e) {
		displayBox.setFitWidth(300);
		displayBox.setFitHeight(400);
		displayBox.setImage(selectedPhoto.getImage());
		displayCap.setText(selectedPhoto.getCaption());
		if(obsList.size() == 0){
			noPhotosAlert();
			return;
		}else if(obsList.size() == 1) {
			prevButt.setDisable(true);
			nextButt.setDisable(true);
		}else if(obsList.indexOf(selectedPhoto) == obsList.size()-1) { //last photo in list
			nextButt.setDisable(true);
			prevButt.setDisable(false);
		}else if(obsList.indexOf(selectedPhoto) == 0){
			prevButt.setDisable(true);
			nextButt.setDisable(false);
		}else {
			prevButt.setDisable(false);
			nextButt.setDisable(false);
		}
	}
	
	private void noPhotosAlert() {
		Alert alert = 
		         new Alert(AlertType.INFORMATION);
		      alert.setTitle("Error - No Photos in Gallery");
		      alert.setHeaderText("There are no photos to display at this time.");
		      alert.showAndWait();
	}
	
	public void prevPic(ActionEvent e) {
		selectedPhoto = obsList.get(obsList.indexOf(selectedPhoto)-1);
		captionField.setText(selectedPhoto.getCaption());
		displayCap.setText(selectedPhoto.getCaption());
		dateField.setText(selectedPhoto.getDate());
		displayTags(selectedPhoto);
		displayPhoto(e);
	}
	
	public void nextPic(ActionEvent e) {
		selectedPhoto = obsList.get(obsList.indexOf(selectedPhoto)+1);
		captionField.setText(selectedPhoto.getCaption());
		displayCap.setText(selectedPhoto.getCaption());
		dateField.setText(selectedPhoto.getDate());
		displayTags(selectedPhoto);
		displayPhoto(e);
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
	
	public void logout(ActionEvent e) {
		Stage nextWindow = Photos.window;
		nextWindow.setScene(Photos.loginScene);
		Photos.loginController.start(primaryStage);
	}
	
	
	public void quitApplication(ActionEvent e) {
		//saves files to disk before closing out the stage
	
			File file = new File(directory);
			
			if (!file.exists())
			{	
				file.mkdir();
			}
			
			try{
				FileOutputStream outputDir = new FileOutputStream(directory + File.separator + filename);
				ObjectOutputStream output = new ObjectOutputStream(outputDir);
				output.writeObject(fileList);
				output.close();
				outputDir.close();
				System.out.println("Saved Photo Library.");
				
				Platform.exit();
				
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
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
	public void createAlbum(ActionEvent e) {
		MenuItem createOption = new MenuItem();
		Image thumbnaildefault = new Image("http://www.nust.na/sites/all/modules/media_gallery/images/empty_gallery.png", 100, 0, false, false);
		//Album currAlbum = new Album();
		/*
		ArrayList<Photo> photoAlbum =
		//Album newAlbum = new Album()
				String item = "Create New Album";
				TextInputDialog dialog = new TextInputDialog(item);
				 dialog.setContentText("Enter Album Name: ");
				 Optional<String> result = dialog.showAndWait();
				 String x = result.get();
				 //ArrayList<Photos> (result.get()) = new ArrayList();
				 
				 if (result.isPresent()) { obsList.addAll(e); }
	
				 */
		TextInputDialog td = new TextInputDialog("Enter new album title: ");
		
		td.setHeaderText("Create New Album");
		td.show();
		Optional<String> result = td.showAndWait();
		
		Album curr = new Album(result, thumbnaildefault);
		if (result.isPresent()){
			albumListView.add(curr);
		}
		
		

		Photo thumbnail = new Photo ("", thumbnaildefault);
		ImageView imageView = new ImageView(thumbnaildefault);
		
		imageView.setImage(thumbnaildefault);
		
		obsList.add(thumbnail);
		
		imageDisplay.getChildren().add(imageView);
		
		String path = "src/Images/stock/";
		
    	File folder = new File(path);
    	File[] fileList = folder.listFiles();
    	for (File file : fileList) {
    		final Image image = new Image(file.toURI().toString(), 150,0,true,true);
    		Photo newPhoto = new Photo(file.getName(),image,new GregorianCalendar(), FXCollections.observableArrayList());
    		obsList.add(newPhoto);
    		ImageView iv = new ImageView(image);
    		iv.setImage(image);
    		iv.setFitWidth(150); 
    		imageDisplay.getChildren().add(iv);
    	
    		iv.setOnMouseClicked(event -> {
    			captionField.setText(newPhoto.getCaption());
    			dateField.setText(newPhoto.getDate());
    			displayTags(newPhoto);
    			selectedPhoto = newPhoto;
    			addTagButt.setDisable(false);
    			deleteTagButt.setDisable(false);
    			deletePhotoButt.setDisable(false);
    		});
    	}
		
	}
	
	/*
	private void showItemInputDialog(Stage mainStage) {
		 String item = listView.getSelectionModel().getSelectedItem();
		 int index = listView.getSelectionModel().getSelectedIndex();
		 
		 dialog.initOwner(mainStage); dialog.setTitle("List Item");
		 dialog.setHeaderText("Selected Item (Index: " + index + ")");
		 dialog.setContentText("Enter name: ");
		 Optional<String> result = dialog.showAndWait();
		 if (result.isPresent()) { obsList.set(index, result.get()); }
	
	}
	*/	
	
	
	
	public void renameAlbum() {
		
		
	}
	
	public void deleteAlbum() {
		
		
	}
	
	public void openAlbum() {
		
	}
	
	
}