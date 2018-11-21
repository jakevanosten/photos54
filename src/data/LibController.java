package data;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class LibController {

	Stage primaryStage;
	String user;
	Photo selectedPhoto;
	String newType = "";
	String newContent = "";
	
	private ObservableList<Photo> obsList = FXCollections.observableArrayList();
	
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
	
	public LibController(){
		
	}
	
	
	public void initialize() {
		imageDisplay.setHgap(10);
		imageDisplay.setVgap(10);
		displayCap.setDisable(true);
		
        String path = "src/data/Images/stock/";
		
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
	

	public void addPhoto(ActionEvent e) {
	
	
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