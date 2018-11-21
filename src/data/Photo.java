package data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

//contains information about photo object, which contains image as well as info and tags about image to use when clicked in library
//going to be stored in photo array called album
public class Photo {
	String caption;
	Image img;
	Calendar cal;
	ObservableList<Tag> tags; //set of tags that belong to the photo, in pairs. ("location","prague"),("person","jake") etc.
	
	
	public Photo(String cap, Image img, Calendar cal, ObservableList<Tag> tags) {
		this.caption = cap;
		this.img = img;
		cal.set(Calendar.MILLISECOND,0);
		this.cal = cal;
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
		return this.cal.getTime().toString();
	}
	
	public void updateDate() {
		GregorianCalendar newCal = new GregorianCalendar();
		newCal.set(Calendar.MILLISECOND,0);
		this.cal = newCal;
	}
	
	public ObservableList<Tag> getTags(){
		return this.tags;
	}
}
