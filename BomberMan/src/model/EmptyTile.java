package model;

public class EmptyTile extends Tile {
	
	public static final TileType EMPTY_TILE = TileType.EMPTY_TILE;

	public EmptyTile(int[] position)
	{
		super(position, EMPTY_TILE);
	}

}
