package model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public abstract class Character extends Element {
	
	public static final int INVINCIBILITY_TIME = 3000;
	public static final int INITIAL_TIME_FOR_MOVEMENT = 375;
	
	private static BoardGame board = model.BoardGame.getInstance();
	private Double speed;
	private Direction direction;
	private int lives;
	private boolean isInvincible;
	private Element tempStorage;
	
	
	public Character(int[] position, Double speed, int lives)
	{
		super(position);
		this.speed = speed;
		this.lives = lives;
		setInvincible(false);
		this.setDisappearOnWalkOff(true);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	
	public Direction getDirection() { return direction; }
	public void setDirection(Direction direction) {	this.direction = direction; }
	
	public int getLives() { return lives; }
	public void setLives(int lives) { this.lives = lives; }
	
	public boolean isInvincible() {	return isInvincible; }
	
	/**
	 * @return the tempStorage
	 */
	public Element getTempStorage()	{ return tempStorage; }

	/**
	 * @param tempStorage the tempStorage to set
	 */
	public void setTempStorage(Element tempStorage) { this.tempStorage = tempStorage; }
	
	@SuppressWarnings("deprecation")
	public void setInvincible(boolean isInvincible) { 
		this.isInvincible = isInvincible;
		if (isInvincible) {
			Object[] args = { model.ChangeType.BECOME_INVINCIBLE, null };
			setChanged();
			notifyObservers(args);
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean move(Direction direction) {
		boolean hasMoved = true;
		int[] prevPosition = this.getPosition();
		int[] newPosition = new int[2];
		Element newCell;
		Element prevCell;
		
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
			(newCell instanceof Tile && 
					(((Tile)newCell).getType() == TileType.WALL ||
					((Tile)newCell).getType() == TileType.SOFT_WALL))) // same as !emptyTile?
		{ // can't walk over walls, bombs or characters
			newPosition = prevPosition;
			hasMoved = false;
		}
		
		if ((this instanceof BomberMan && newCell instanceof model.Enemy) || (this instanceof Enemy && newCell instanceof BomberMan)) {
			model.BomberMan.getInstance().loseLife();
		}
		
		if (this instanceof BomberMan && newCell instanceof PowerUp) {
			((model.PowerUp)newCell).execute();
		}
		
		
		// probably needs to be improved
		
		if (newCell instanceof EmptyTile) {
			board.setCell(this, newPosition);
		} else if (this instanceof Enemy && newCell instanceof PowerUp) {
			this.setTempStorage(newCell);
			board.setCell(this, newPosition);
		} else if (this instanceof BomberMan && newCell instanceof PowerUp && newCell.disappearsOnWalkOn()) {
			board.setCell(this, newPosition);
		} else if (this instanceof BomberMan && newCell instanceof Exit) {
			this.setTempStorage(newCell);
			board.setCell(this, newPosition);
		}
		
		if (this.getTempStorage() == null && hasMoved ) {
			board.setCell(new EmptyTile(prevPosition), prevPosition);
		}
		
		
		if (this.getTempStorage() != null && hasMoved) {
			board.setCell(this.getTempStorage(), prevPosition);
			this.setTempStorage(null);
		}
		
		this.setPosition(newPosition);
		
		Object[] args = { model.ChangeType.MOVE, prevPosition };
		setChanged();
		notifyObservers(args);
		return hasMoved;
	}
 
	
	@SuppressWarnings("deprecation")
	public void loseLife() {
		if (!isInvincible) {
			lives--;
			Object[] args = { model.ChangeType.LOSE_LIFE, lives };
			setChanged();
			notifyObservers(args);
			if (lives == 0) {
				die();
			} else {
				becomeInvincible();
			}		
		}
	}

	public void becomeInvincible() {
		setInvincible(true);
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable becomeMortal = () -> {
			Platform.runLater(() -> {
				setInvincible(false);
			});
		};
		executor.schedule(becomeMortal, INVINCIBILITY_TIME, TimeUnit.MILLISECONDS);
//		Object[] args = { model.ChangeType.LOSE_LIFE, lives };
//		setChanged();
//		notifyObservers(args);
	}
	
	@SuppressWarnings("deprecation")
	public void die() {
		board.setCell(new EmptyTile(getPosition()), getPosition());
		Object[] args = { model.ChangeType.DIE, lives };
		setChanged();
		notifyObservers(args);
		
	}


	
}
