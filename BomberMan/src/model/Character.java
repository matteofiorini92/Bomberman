package model;

public abstract class Character extends Element {
	
	private Double speed;
	
	public Character(int[] position, Double speed)
	{
		super(position);
		this.setSpeed(speed);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	
	public void move(Board board, int[] prevPosition, int[] newPosition) {
		Element newCell = board.getCell(newPosition);
		if (newCell instanceof Bomb || (newCell instanceof Tile && (((Tile)newCell).getType() == TileType.WALL || ((Tile)newCell).getType() == TileType.SOFT_WALL))) { // can't walk over walls or bombs
			newPosition = prevPosition;
		}
		board.setCell(new EmptyTile(prevPosition), prevPosition);
		board.setCell(this, newPosition);
		this.setPosition(prevPosition, newPosition);
	}
	

}
