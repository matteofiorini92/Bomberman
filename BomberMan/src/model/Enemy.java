package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public abstract class Enemy extends Character implements HidePowerUp {
	private static List<Enemy> aliveEnemies = new ArrayList<Enemy>();
	private static BoardGame board = BoardGame.getInstance();
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();	
	private int points;
	private model.PowerUp hiddenPowerUp = null;

	public Enemy(int[] position, Double speed, int points, int lives)
	{
		super(position, speed, lives);
		this.points = points;
		aliveEnemies.add(this);
	}
	
	public int getPoints() { return points;	}
	
	public model.PowerUp getHiddenPowerUp()	{ return hiddenPowerUp; }
	public void setHiddenPowerUp(model.PowerUp hiddenPowerUp) {	this.hiddenPowerUp = hiddenPowerUp; }
	
	@Override
	public boolean isHidingSomething() { return hiddenPowerUp != null; }
	
	@Override
	public void showHiddenPowerUp()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void die() {
		super.die();
		aliveEnemies.remove(this);
		if (isHidingSomething()) {
			hiddenPowerUp.setPosition(getPosition()); // update the position of the power up to the enemy's current position
			board.setCell(hiddenPowerUp, this.getPosition());
		}
		model.Player.getInstance().addPoints(this.getPoints());
		executor.shutdownNow(); //stop moving when the enemy dies
//		if (this.getLives() == 0) {
//		}
	}
	
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

		executor.scheduleAtFixedRate(moveTask, 0, (long)(INITIAL_TIME_FOR_MOVEMENT / this.getSpeed()), TimeUnit.MILLISECONDS);
	}
	
	public static Direction getRandomDirection(Enemy enemy) { // public?
		// Class<Direction> directionClass;
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
	
	public static List<Direction> getAvailableDirections(int[] currPosition, Element[][] cells) { // public ?
		List<Direction> availableDirection = new ArrayList<Direction>();
		int coordinateX = currPosition[1];
		int coordinateY = currPosition[0];
		if (cells[coordinateY][coordinateX-1] instanceof EmptyTile) {
			availableDirection.add(Direction.LEFT);
		}
		if (cells[coordinateY][coordinateX+1] instanceof EmptyTile) {
			availableDirection.add(Direction.RIGHT);
		}
		if (cells[coordinateY-1][coordinateX] instanceof EmptyTile) {
			availableDirection.add(Direction.UP);
		}
		if (cells[coordinateY+1][coordinateX] instanceof EmptyTile) {
			availableDirection.add(Direction.DOWN);
		}
		
		
		return availableDirection;
	}
	
	public static List<Enemy> getAliveEnemies() { return aliveEnemies;	}
	
}
