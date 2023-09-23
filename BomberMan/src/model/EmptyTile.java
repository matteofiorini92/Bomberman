package model;

/**
 * model of an empty tile of the board
 * @author Matteo
 *
 */
public class EmptyTile extends Tile {
	
	public static final TileType EMPTY_TILE = TileType.EMPTY_TILE;

	public EmptyTile(int[] position)
	{
		super(position, EMPTY_TILE);
		this.setDisappearOnWalkOn(true);
	}

}
