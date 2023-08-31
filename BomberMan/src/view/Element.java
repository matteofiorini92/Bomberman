package view;

import java.util.Observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("deprecation")
public abstract class Element extends ImageView implements Observer {

	public Element (Image image) {
		this.setImage(image);
	}

}
