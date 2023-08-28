package model;


public class BomberMan extends Character {
	public static BomberMan bomberman;
	public static final int INITIAL_LIVES = 5;
	public static final int INITIAL_BOMBS = 1;
	public static final int INITIAL_RANGE = 1;
	public static final int[] INITIAL_POSITION = {1, 2};
	public static final double INITIAL_SPEED = 1.0;
	
	private int bombs;
	private int range;
	
	private BomberMan() {
		super(INITIAL_POSITION, INITIAL_SPEED, INITIAL_LIVES);
		becomeInvincible();
		bombs = INITIAL_BOMBS;
		range = INITIAL_RANGE;
	}
	
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
	


}
