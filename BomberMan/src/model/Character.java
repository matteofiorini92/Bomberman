package model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * model of character class
 * @author Matteo
 *
 */
public abstract class Character extends Element {
	
	/**
	 * time a character becomes invincible when losing a life (or at the beginning of a level for bomberman)
	 */
	public static final int INVINCIBILITY_TIME = 3000;
	/**
	 * time it takes a character to move (affected by its speed)
	 */
	public static final int TIME_FOR_MOVEMENT = 375;
	
	private static GameBoard board = model.GameBoard.getInstance();
	private Double speed;
	private Direction direction;
	private int lives;
	private boolean isInvincible;
	private Element tempStorage;
	
	/**
	 * constructor
	 * @param position the initial position of a character
	 * @param speed the initial speed of a character. can change for bomberman
	 * @param lives initial number of lives of a character
	 */
	public Character(int[] position, Double speed, int lives)
	{
		super(position);
		this.speed = speed;
		this.lives = lives;
		setInvincible(false);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	
	public Direction getDirection() { return direction; }
	public void setDirection(Direction direction) {	this.direction = direction; }
	
	public int getLives() { return lives; }
	public void setLives(int lives) { this.lives = lives; }
	
	public boolean isInvincible() {	return isInvincible; }
	
	/**
	 * used to get the Element a character is temporarly storing (e.g. power ups)
	 * @return the tempStorage
	 */
	public Element getTempStorage()	{ return tempStorage; }

	/**
	 * used to set the Element a character is temporarly storing (e.g. power ups)
	 * @param tempStorage the tempStorage to set
	 */
	public void setTempStorage(Element tempStorage) { this.tempStorage = tempStorage; }
	
	
	/**
	 * set isInvincible for the character to true/false, for instance when a character dies 
	 * @param isInvincible
	 */
	@SuppressWarnings("deprecation")
	private void setInvincible(boolean isInvincible) { 
		this.isInvincible = isInvincible;
		if (isInvincible) {
			Object[] args = { model.ChangeType.BECOME_INVINCIBLE, null };
			setChanged();
			notifyObservers(args);
		}
	}
	
	/**
	 * move the character
	 * @param direction the direction the character is moving in
	 * @return true if the character has moved, false otherwise
	 */
	@SuppressWarnings("deprecation")
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
		boolean isOut = false;
		
		if (
			newCell instanceof Bomb || 
			newCell instanceof Character || 
			(newCell instanceof Tile && 
					(((Tile)newCell).getType() == TileType.HARD_WALL ||
					((Tile)newCell).getType() == TileType.SOFT_WALL))) // same as !emptyTile?
		{ // can't walk over walls, bombs or characters
			newPosition = prevPosition;
			hasMoved = false;
		}
		
		if ((this instanceof BomberMan && newCell instanceof model.Enemy) || (this instanceof Enemy && newCell instanceof BomberMan)) {
			BomberMan.getInstance().loseLife();
		}
		
		if (this instanceof BomberMan && newCell instanceof PowerUp) {
			((model.PowerUp)newCell).execute();
		}
		
		if (this instanceof BomberMan && newCell instanceof Exit) {
			isOut = ((model.Exit)newCell).getOut();
		}
		
		
		if (!isOut) {
			if (this.getTempStorage() == null && hasMoved ) {
				board.setCell(new EmptyTile(prevPosition), prevPosition);
			}
			if (this.getTempStorage() != null && hasMoved) {
				board.setCell(this.getTempStorage(), prevPosition);
				this.setTempStorage(null);
			}
			
			
			if (newCell instanceof EmptyTile) {
				board.setCell(this, newPosition);
			} else if ((this instanceof Enemy && newCell instanceof PowerUp) || (this instanceof Enemy && newCell instanceof Exit)) {
				this.setTempStorage(newCell);
				board.setCell(this, newPosition);
			} else if (this instanceof BomberMan && newCell instanceof PowerUp && newCell.disappearsOnWalkOn()) {
				board.setCell(this, newPosition);
			} else if (this instanceof BomberMan && newCell instanceof Exit) {
				this.setTempStorage(newCell);
				board.setCell(this, newPosition);
			}
			
			
			this.setPosition(newPosition);
			
			Object[] args = { model.ChangeType.MOVE, prevPosition };
			setChanged();
			notifyObservers(args);
			
		}
		
		return hasMoved;
	}
 
	/**
	 * decrease the amount of lives of a character. if it reaches 0, the character dies.
	 */
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

	/**
	 * make the character invincible. schedule its return to being mortal after INVINCIBILITY_TIME milliseconds
	 */
	public void becomeInvincible() {
		setInvincible(true);
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable becomeMortal = () -> {
			Platform.runLater(() -> {
				setInvincible(false);
			});
		};
		executor.schedule(becomeMortal, INVINCIBILITY_TIME, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * remove a character from the game board
	 */
	@SuppressWarnings("deprecation")
	public void die() {
		board.setCell(new EmptyTile(getPosition()), getPosition());
		Object[] args = { model.ChangeType.DIE, lives };
		setChanged();
		notifyObservers(args);
		
	}


	
}
