package data;

	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.*;

import javafx.beans.InvalidationListener;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.*;

	/**
	 * The Class Album.
	 */
	public class Album  {
		private String album;
		private String filename;
		/*
		private static List <Photo> photoData = new ArrayList<>();
		
		ObservableList<Photo> observableList = FXCollections.observableArrayList(photoData);
		
		public ObservableList<Photo> getList(){
			return observableList;
		}
		*/
		
		/**
		 * 
		 */
		//private static final long serialVersionUID = 1L;

		/** The name. */
		public String name;
		
		/** The photos. */
		public ArrayList<Photo> photos = new ArrayList<Photo>();

		/**
		 * Gets the photos.
		 *
		 * @return the photos
		 */
		public ArrayList<Photo> getPhotos(){
			return photos;
		}
		
		public void setAlbum(String album){
			this.album = album;
		}
		
		/**
		 * Instantiates a new album.
		 *
		 * @param name the name
		 */
		public Album(String name) {
			this.name = name;
		}

		/**
		 * Adds the photo.
		 *
		 * @param p the p
		 */
		public void addPhoto(Photo p) {
			photos.add(p);
			//Collections.sort(photos);
		}
		
		/**
		 * Gets the date.
		 *
		 * @return the date
		 */
		public String getDate(){
			if(photos.size() == 0){
				return "";
			}else{
				return photos.get(0).getDate() + "-" + photos.get(photos.size()-1).getDate();
			}
		}

		/**
		 * Delete photo.
		 *
		 * @param p the p
		 */
		public void deletePhoto(Photo p) {
			photos.remove(p);
		}

		/**
		 * Gets the size.
		 *
		 * @return the size
		 */
		public int getSize() {
			return photos.size();
		}

		@Override
		public boolean equals(Object object) {
			boolean result = false;
			if (object == null || object.getClass() != getClass()) {
				result = false;
			} else {
				Album a = (Album) object;
				if (this.name.toLowerCase().equals(a.name.toLowerCase())) {
					result = true;
				}
			}
			return result;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName(){
			return name;
		}
		
		
		public String toString(){
			return name;
		}
		
		

		
	}

