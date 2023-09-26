package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

/**
 * model of an enemy character
 * @author Matteo
 *
 */
public abstract class Enemy extends Character implements Hiding {
	private static List<Enemy> aliveEnemies = new ArrayList<Enemy>();
	private static GameBoard board = GameBoard.getInstance();
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();	
	private int points;
	private model.Hidable hiddenHidable = null;

	public Enemy(int[] position, Double speed, int points, int lives)
	{
		super(position, speed, lives);
		this.points = points;
		aliveEnemies.add(this);
	}
	
	public static List<Enemy> getAliveEnemies() { return aliveEnemies; }
	public int getPoints() { return points;	}
	
	public model.Hidable getHiddenHidable()	{ return hiddenHidable; }
	public void setHiddenHidable(model.Hidable hiddenHidable) {	this.hiddenHidable = hiddenHidable; }
	
	@Override
	public boolean isHidingSomething() { return hiddenHidable != null; }
	
	/**
	 * remove enemy from list of alive enemies, drops a power up if needed, adds point to bomberman
	 */
	@Override
	public void die() {
		super.die();
		aliveEnemies.remove(this);
		if (isHidingSomething()) {
			((Item)hiddenHidable).setPosition(this.getPosition()); // update the position of the power up to the enemy's current position
			board.setCell((Element)hiddenHidable, this.getPosition());
		}
		model.BomberMan.getInstance().addPoints(this.points);
		executor.shutdownNow();
	}
	
	/**
	 * used when starting a new game / continuing after dying
	 */
	public void removeFromBoard() {
		executor.shutdown();
		board.setCell(new EmptyTile(getPosition()), getPosition());
	}
	
	
	/**
	 * enemy start moving in a random direction
	 */
	public void startMoving() {
		boolean[] needsNewDirection = { true };
		Direction[] randomDirection = { getRandomDirection(this) };
		Runnable moveTask = () -> {
			Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread 				
				needsNewDirection[0] = !this.move(randomDirection[0]);
				if (needsNewDirection[0]) {				
					randomDirection[0] = getRandomDirection(this);
				}
			});
		};

		executor.scheduleAtFixedRate(moveTask, 0, (long)(TIME_FOR_MOVEMENT / this.getSpeed()), TimeUnit.MILLISECONDS);
	}
	
	private static Direction getRandomDirection(Enemy enemy) { // public?
		Element[][] cells = board.getCells();
		int[] currPosition = enemy.getPosition();
		List<Direction> availableDirections = getAvailableDirections(currPosition, cells);
		if (availableDirections.size() != 0) {			
			Random random = new Random();
			int index = random.nextInt(availableDirections.size());
			return availableDirections.get(index);
		}
		
		return Direction.RIGHT;
    }
	
	private static List<Direction> getAvailableDirections(int[] currPosition, Element[][] cells) { // public ?
		List<Direction> availableDirection = new ArrayList<Direction>();
		int coordinateX = currPosition[1];
		int coordinateY = currPosition[0];
		if (!(cells[coordinateY][coordinateX-1] instanceof Wall)) {
			availableDirection.add(Direction.LEFT);
		}
		if (!(cells[coordinateY][coordinateX+1] instanceof Wall)) {
			availableDirection.add(Direction.RIGHT);
		}
		if (!(cells[coordinateY-1][coordinateX] instanceof Wall)) {
			availableDirection.add(Direction.UP);
		}
		if (!(cells[coordinateY+1][coordinateX] instanceof Wall)) {
			availableDirection.add(Direction.DOWN);
		}
		
		
		return availableDirection;
	}
	
	
}
