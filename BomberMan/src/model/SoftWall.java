package model;

public class SoftWall extends Tile {
	
	public static final TileType SOFT_WALL = TileType.SOFT_WALL;

	public SoftWall(int[] position)
	{
		super(position, SOFT_WALL);
	}

}
