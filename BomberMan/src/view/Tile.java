package view;

import java.util.Observable;

import javafx.scene.image.Image;

/**
 * view of a tile. each tile has a description to handle different shadows from other tiles/items
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Tile extends Item {

    private String desc;

    /**
     * constructor
     * @param desc the short description of the tile, used to handle different shadows from other tiles/items
     * @param image the image of the tile
     */
    public Tile(String desc, Image image) {
    	super(image);
    	this.desc = desc;
    }

	public String getDesc()	{ return desc; }

	/**
	 * OO pattern
	 * doesn't require to be implemented as soft walls have their own implementation of update
	 * walls and empty tiles don't need to be updated by model
	 */
	@Override
	public void update(Observable o, Object arg) {}
}
