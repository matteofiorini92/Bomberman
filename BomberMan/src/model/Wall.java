package model;

/**
 * model of a wall tile
 * @author Matteo
 *
 */
public class Wall extends Tile {
	
	public static final TileType WALL = TileType.WALL;

	public Wall(int[] position)
	{
		super(position, WALL);
	}

}
