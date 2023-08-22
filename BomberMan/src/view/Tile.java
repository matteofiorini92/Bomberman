package view;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

@SuppressWarnings("deprecation")
public class Tile extends Item {

    private String desc;

    public Tile(String desc, Image image) {
    	super(new ImageView(image));
    	this.desc = desc;
    }

	public String getDesc()	{ return desc; }

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}
}
