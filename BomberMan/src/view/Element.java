//package view;
//
//import java.util.Observer;
//
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
//
//@SuppressWarnings("deprecation")
//public abstract class Element extends StackPane implements Observer { // SHOULD EACH ELEMENT BE A STACKPANE?? OR RATHER AN IMAGEVIEW?
//
//
//	private ImageView imageView;
//	
//	public Element (ImageView imageView) {
//		this.imageView = imageView;
//		getChildren().add(imageView);
//	}
//
//	public ImageView getImageView()	{ return imageView; }
//	public void setImageView(ImageView imageView) { this.imageView = imageView; }
//
//}


package view;

import java.util.Observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

@SuppressWarnings("deprecation")
public abstract class Element extends ImageView implements Observer { // SHOULD EACH ELEMENT BE A STACKPANE?? OR RATHER AN IMAGEVIEW?


//	private ImageView imageView;
	
	public Element (Image image) {
		this.setImage(image);
//		getChildren().add(imageView);
	}
//
//	public ImageView getImageView()	{ return imageView; }
//	public void setImageView(ImageView imageView) { this.imageView = imageView; }

}
