package model;

public class Wall extends Tile {
	
	public static final TileType WALL = TileType.WALL;

	public Wall(int[] position)
	{
		super(position, WALL);
	}

}
