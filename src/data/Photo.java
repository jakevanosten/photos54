package data;

import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.Image;

//contains information about photo object, which contains image as well as info and tags about image to use when clicked in library
//going to be stored in photo array called album
public class Photo {
	String caption;
	Image img;
	Date date;
	ArrayList<Tag> tags; //set of tags that belong to the photo, in pairs. ("location","prague"),("person","jake") etc.
	
	
	public Photo(String cap, Image img, Date date, ArrayList<Tag> tags) {
		this.caption = cap;
		this.img = img;
		this.date = date;
		this.tags = tags;
	}
	
	public void addTag(Tag t) {
		tags.add(t);
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public Image getImage() {
		return this.img;
	}
	
	public String getDate() {
		return this.date.toString();
	}
	
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
	
	public String printTags() {
		return this.tags.toString();
	}
}
