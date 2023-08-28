package view;

import java.util.Observable;

import javafx.scene.image.Image;

@SuppressWarnings("deprecation")
public class Tile extends Item {

    private String desc;

    public Tile(String desc, Image image) {
    	super(image);
    	this.desc = desc;
    }

	public String getDesc()	{ return desc; }

	@Override
	public void update(Observable o, Object arg)
	{
		// TODO Auto-generated method stub
		
	}
}
