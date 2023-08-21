package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public abstract class Enemy extends Character {
	
	private static Board board = Board.getInstance();
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();	
	private int points;

	public Enemy(int[] position, Double speed, int points, int lives)
	{
		super(position, speed, lives);
		this.points = points;
		//startMoving();
	}
	public int getPoints() { return points;	}
	public void setPoints(int points) { this.points = points; }
	
	@Override
	public void die() {
		super.die();
		if (this.getLives() == 0) {
			executor.shutdownNow(); //stop moving when the enemy dies
		}
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

		executor.scheduleAtFixedRate(moveTask, 0, 375, TimeUnit.MILLISECONDS);
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
	
}
