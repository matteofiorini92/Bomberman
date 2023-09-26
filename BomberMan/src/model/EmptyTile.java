package model;

/**
 * model of an empty tile of the board
 * @author Matteo
 *
 */
public class EmptyTile extends Tile {
	
	public EmptyTile(int[] position)
	{
		super(position, TileType.EMPTY_TILE);
		this.setDisappearOnWalkOn(true);
	}

}
