package data;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
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
	
	static Stage window;
	static Scene libScene;
	static TilePane tilePane;
	static Scene loginScene;
	static LibController libController;
	static LoginController loginController;
	
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
		
		loginScene = new Scene(root);
		libScene = new Scene(root2);
		
		
		
		tilePane = new TilePane();
		
		
		
		
		loginController = loader.getController();
		libController = loader2.getController();
		
		//loader.setController(new loginController(path));
		
		loginController.start(primaryStage);
		

		primaryStage.setScene(loginScene);
		primaryStage.setTitle("Photo Library");  
		primaryStage.show();
		
	}
	/* trying to see if i can load tile pane
	 protected Void call() throws Exception {
         DirectoryChooser directoryChooser = new DirectoryChooser();
         final File selectedDirectory = directoryChooser.showDialog(null);
         //there is an ImageFileFilter class below
         File[] imageFiles = selectedDirectory.listFiles(new FilenameFilter());

         tilePane.getChildren().clear();
         if(){
        	 
         }
         else{
        	 for (File file : imageFiles) {
             	try {
                     ImageView imageView = new ImageView();
                  
                     //add imageView to the TilePane
                     tilePane.getChildren().add(imageView);
             	} catch (FileNotFoundException e) {             
             		e.printStackTrace();
             	}
         	}   
         	return null;
         }
     }
 
	
	 private class ImageFileFilter implements FileFilter {
		 
	        private final String[] validFileExtension = new String[] {"jpg", "jpeg", "png", "gif"}; 
	        public boolean accept(File pathname) {
	            for (String extension : validFileExtension) {
	                if (pathname.getName().toLowerCase().endsWith(extension)) {
	                    return true;
	                }
	            }
	            return false;
	        }
	 
	    }
	 
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
