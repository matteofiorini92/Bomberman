package view;

import java.util.Observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * view of an element. all elements are image views.
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public abstract class Element extends ImageView implements Observer {

	public Element (Image image) {
		this.setImage(image);
	}

}
