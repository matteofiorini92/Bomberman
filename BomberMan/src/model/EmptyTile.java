package model;

/**
 * model of an empty tile of the board
 * @author Matteo
 *
 */
public class EmptyTile extends Tile {
	
	public EmptyTile(int[] position)
	{
		super(position);
		this.setDisappearOnWalkOn(true);
	}

}
