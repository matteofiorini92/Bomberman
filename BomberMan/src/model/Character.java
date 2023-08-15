package model;

import javafx.scene.input.KeyEvent;

public abstract class Character extends Element {
	
	private Double speed;
	private Direction direction;
	
	public Character(int[] position, Double speed)
	{
		super(position);
		this.setSpeed(speed);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	
	public boolean move(Board board, Direction direction) {
		boolean hasMoved = true;
		int[] prevPosition = this.getPosition();
		int[] newPosition = new int[2];
		Element newCell;
		
		setDirection(direction);
		
		switch (direction) {
		case DOWN:
			newPosition = new int[] {prevPosition[0] + 1, prevPosition[1]};
			break;
		case UP:
			newPosition = new int[] {prevPosition[0] - 1, prevPosition[1]};
			break;
		case LEFT:
			newPosition = new int[] {prevPosition[0], prevPosition[1] - 1};
			break;
		case RIGHT:
			newPosition = new int[] {prevPosition[0], prevPosition[1] + 1};
			break;
		default:
			newPosition = prevPosition;
			hasMoved = false;
			break;
		}

		newCell = board.getCell(newPosition);
		
		if (newCell instanceof Bomb || (newCell instanceof Tile && (((Tile)newCell).getType() == TileType.WALL || ((Tile)newCell).getType() == TileType.SOFT_WALL))) { // can't walk over walls or bombs
			newPosition = prevPosition;
			hasMoved = false;
		}
		board.setCell(new EmptyTile(prevPosition), prevPosition);
		board.setCell(this, newPosition);
		this.setPosition(prevPosition, newPosition);
		return hasMoved;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}
	
//	public void move(Board board, int[] prevPosition, int[] newPosition) {
//		Element newCell = board.getCell(newPosition);
//		if (newCell instanceof Bomb || (newCell instanceof Tile && (((Tile)newCell).getType() == TileType.WALL || ((Tile)newCell).getType() == TileType.SOFT_WALL))) { // can't walk over walls or bombs
//			newPosition = prevPosition;
//		}
//		board.setCell(new EmptyTile(prevPosition), prevPosition);
//		board.setCell(this, newPosition);
//		this.setPosition(prevPosition, newPosition);
//	}
	

}
