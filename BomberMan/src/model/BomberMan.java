package model;

/**
 * model of the bomberman character
 * @author Matteo
 *
 */
public class BomberMan extends Character {
	public static BomberMan bomberman;
	public static final int INITIAL_LIVES = 5;
	public static final int INITIAL_BOMBS = 1;
	public static final int INITIAL_RANGE = 1;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final double INITIAL_SPEED = 1.0;
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
	
	/**
	 * singleton pattern
	 * @return the only existing instance of the class, or creates one
	 */
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
	public int getScore() { return score; }
	
	@SuppressWarnings("deprecation")
	public void setScore(int score) {
		this.score = score > 0 ? score : 0;
		Object[] args = { model.ChangeType.CHANGE_POINTS, score };
		setChanged();
		notifyObservers(args);
	}
	
	@SuppressWarnings("deprecation")
	public void addPoints(int points) {
		score += points;
		Object[] args = { model.ChangeType.CHANGE_POINTS, score };
		setChanged();
		notifyObservers(args);
	}
	
	@SuppressWarnings("deprecation")
	public void losePoints(int points) {
		int partial = score - points;
		points = partial > 0 ? partial : 0;
		Object[] args = { model.ChangeType.CHANGE_POINTS, score };
		setChanged();
		notifyObservers(args);
	}

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
	
	/**
	 * used to start a new game
	 */
	public void reset() {
		this.setPosition(INITIAL_POSITION);
		this.setSpeed(INITIAL_SPEED);
		this.setLives(INITIAL_LIVES);
		this.setTempStorage(null);
		becomeInvincible();
		bombs = INITIAL_BOMBS;
		range = INITIAL_RANGE;
		score = INITIAL_SCORE;
	}
	
	
}
