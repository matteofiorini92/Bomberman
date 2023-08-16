package view;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Item {


    private String desc;
    private ImageView imageView;

    public Tile(String desc, Image image) {
    	this.desc = desc;
        imageView = new ImageView(image);
        imageView.setFitWidth(ITEM_WIDTH);
        imageView.setFitHeight(ITEM_HEIGHT);

        Rectangle rectangle = new Rectangle(ITEM_WIDTH, ITEM_HEIGHT, Color.TRANSPARENT);
        getChildren().addAll(rectangle, imageView);
    }

	public String getDesc()	{ return desc; }
	// public void setDesc(String desc)	{ this.desc = desc; }

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}
}
