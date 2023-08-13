package model;

public class BomberMan extends Character {
	public static final int INITIAL_LIVES = 3;
	public static final int INITIAL_BOMBS = 1;
	public static final int INITIAL_RANGE = 1;
	private int lives;
	private int bombs;
	private int range;
	
	public BomberMan(Position position, Double speed) {
		super(position, speed);
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

}
