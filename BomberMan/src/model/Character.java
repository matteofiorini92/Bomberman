package model;

public abstract class Character extends Element {
	
	private static Board board = model.Board.getInstance();
	private Double speed;
	private Direction direction;
	private int lives;
	
	public Character(int[] position, Double speed, int lives)
	{
		super(position);
		this.speed = speed;
		this.lives = lives;
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	
	public Direction getDirection() { return direction; }
	public void setDirection(Direction direction) {	this.direction = direction; }
	
	public int getLives() { return lives; }
	public void setLives(int lives) { this.lives = lives; }

	
	public boolean move(Direction direction) {
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
		
		if (
				newCell instanceof Bomb || 
				newCell instanceof Character || 
				(newCell instanceof Tile && (((Tile)newCell).getType() == TileType.WALL || ((Tile)newCell).getType() == TileType.SOFT_WALL))) 
		{ // can't walk over walls, bombs or characters
			newPosition = prevPosition;
			hasMoved = false;
		}
		model.Element prevCell = board.getCell(prevPosition);
		prevCell = prevCell instanceof Bomb ? prevCell : new EmptyTile(prevPosition);// to prevent BM from setting an EmptyTile where he's just dropped a bomb
		//board.setCell(new EmptyTile(prevPosition), prevPosition);
		board.setCell(prevCell, prevPosition);
		board.setCell(this, newPosition);
		this.setPosition(prevPosition, newPosition);
		return hasMoved;
	}

	
	public void loseLife() {
		lives--;
		
		if (lives == 0) {
			die();
		} else {
			Object[] args = { model.ChangeType.LOSE_LIFE, lives };
			setChanged();
			notifyObservers(args);
			System.out.println("You have " + lives + " lives left.");	
		}		
	}

	public void die() {
		board.setCell(new EmptyTile(getPosition()), getPosition());
		Object[] args = { model.ChangeType.DIE, lives };
		setChanged();
		notifyObservers(args);
		System.out.println("You're dead.");	
		
	}
	
}
