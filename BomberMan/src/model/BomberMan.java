package model;


public class BomberMan extends Character {
	public static BomberMan bomberman;
	public static final int INITIAL_LIVES = 5;
	public static final int INITIAL_BOMBS = 10;
	public static final int INITIAL_RANGE = 10;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final double INITIAL_SPEED = 5.0;
	public static final int INITIAL_SCORE = 0;
	
	private int bombs;
	private int range;
	private int score;

	
	private BomberMan() {
		super(INITIAL_POSITION, INITIAL_SPEED, INITIAL_LIVES);
		becomeInvincible();
		bombs = INITIAL_BOMBS;
		range = INITIAL_RANGE;
		score = INITIAL_SCORE;
	}
	
//	private BomberMan(int bombs, int range, int score, double speed, int lives) {
//		super(INITIAL_POSITION, speed, lives);
//		becomeInvincible();
//		this.bombs = bombs;
//		this.range = range;
//		this.score = score;
//	}
	
	public static BomberMan getInstance(){
		if (bomberman == null) {
			bomberman = new BomberMan();
		}
		return bomberman;
	}
	
	
	public int getBombs() {	return bombs; }
	public void setBombs(int bombs)	{ this.bombs = bombs; }
	public void incBombs() { bombs++; }
	public void decBombs() { bombs--; }
	public int getRange() {	return range; }
	public void setRange(int range)	{ this.range = range; }
	public void incRange() { range++; }
	public void decRange() { range--; }
	
	@SuppressWarnings("deprecation")
	public void incSpeed(Double increase) { 
		Double newSpeed = getSpeed() + increase;
		setSpeed(newSpeed);
		Object[] args = { model.ChangeType.CHANGE_SPEED, newSpeed };
		setChanged();
		notifyObservers(args);
	}
	
	@SuppressWarnings("deprecation")
	public void decSpeed(Double decrease) { 
		Double newSpeed = getSpeed() - decrease;
		setSpeed(newSpeed);
		Object[] args = { model.ChangeType.CHANGE_SPEED, newSpeed };
		setChanged();
		notifyObservers(args);
	}

	public void addPoints(int points) { score += points; }
	public void losePoints(int points) {
		int partial = score - points;
		points = partial > 0 ? partial : 0;
	}

//	public void startNewLevel() {
//		int currBombs = bombs;
//		int currRange = range;
//		int currScore = score;
//		double speed = this.getSpeed();
//		int lives = this.getLives();
//		
//		BomberMan.bomberman = null;
//		BomberMan.bomberman = new BomberMan(currBombs, currRange, currScore, speed, lives);
//	}
	
	
	


}
