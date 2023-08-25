package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item extends Element {
	
    public static final int ITEM_HEIGHT = 48;
    public static final int ITEM_WIDTH = 48;

	public Item(Image image)
	{
		super(image);
	}



}
