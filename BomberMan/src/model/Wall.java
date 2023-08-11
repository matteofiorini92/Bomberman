package model;

public class Wall extends Tile {
	
	public static final TileType WALL = TileType.WALL;

	public Wall(Position position)
	{
		super(position, WALL);
		
	}

}
