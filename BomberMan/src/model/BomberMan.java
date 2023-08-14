package model;

public class BomberMan extends Character {
	public static final int INITIAL_LIVES = 3;
	public static final int INITIAL_BOMBS = 1;
	public static final int INITIAL_RANGE = 1;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final double INITIAL_SPEED = 1.0;
	private int lives;
	private int bombs;
	private int range;
	private Direction direction;
	
	public BomberMan() {
		super(INITIAL_POSITION, INITIAL_SPEED);
		lives = INITIAL_LIVES;
		bombs = INITIAL_BOMBS;
		range = INITIAL_RANGE;
	}
	
	public int getLives() { return lives; }
	public void setLives(int lives)	{ this.lives = lives; }
	public int getBombs() {	return bombs; }
	public void setBombs(int bombs)	{ this.bombs = bombs; }
	public int getRange() {	return range; }
	public void setRange(int range)	{ this.range = range; }
	
	public void dropBomb() {
		bombs--;
		System.out.println(getPosition());
	}
	
	public void die() {
		lives--;
		System.out.println("You have " + lives + " lives left.");
	}

	public Direction getDirection()	{ return direction; }
	public void setDirection(Direction direction) { 
		this.direction = direction;
		setChanged();
		notifyObservers();
	}


}
