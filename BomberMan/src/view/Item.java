package view;

import javafx.scene.image.ImageView;

public abstract class Item extends Element {
	
    public static final int ITEM_HEIGHT = 64;
    public static final int ITEM_WIDTH = 64;

	public Item(ImageView imageView)
	{
		super(imageView);
	}



}
