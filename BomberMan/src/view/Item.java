package view;

import javafx.scene.image.Image;

/**
 * view of a generic item (e.g. bombs and hidables)
 * @author Matteo
 *
 */
public abstract class Item extends Element {
	
    public static final int ITEM_HEIGHT = 48;
    public static final int ITEM_WIDTH = 48;

	public Item(Image image)
	{
		super(image);
	}



}
