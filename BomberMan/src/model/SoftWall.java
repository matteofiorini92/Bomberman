package model;

public class SoftWall extends Tile {
	
	public static final TileType SOFT_WALL = TileType.SOFT_WALL;
	public static Board board = model.Board.getInstance();
	
	public SoftWall(int[] position)
	{
		super(position, SOFT_WALL);
	}
	
	public void destroy() {
		int[] position = this.getPosition();
		
		board.setCell(new EmptyTile(position), position);
		setChanged();
		notifyObservers();
	}

}
