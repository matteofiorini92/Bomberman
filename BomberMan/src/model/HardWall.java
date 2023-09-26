package model;

/**
 * model of a wall tile
 * @author Matteo
 *
 */
public class HardWall extends Wall {
	
	public HardWall(int[] position)
	{
		super(position, TileType.HARD_WALL);
	}

}
