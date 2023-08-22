package view;

import java.util.Observer;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Element extends StackPane implements Observer { // SHOULD EACH ELEMENT BE A STACKPANE?? OR RATHER AN IMAGEVIEW?


	private ImageView imageView;
	
	public Element (ImageView imageView) {
		this.imageView = imageView;
		getChildren().add(imageView);
	}

	public ImageView getImageView()	{ return imageView; }
	public void setImageView(ImageView imageView) { this.imageView = imageView; }

}
