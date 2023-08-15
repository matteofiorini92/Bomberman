package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Enemy extends Character {
	
	private int points;

	public Enemy(int[] position, Double speed, int points)
	{
		super(position, speed);
		this.points = points;
		startMoving();
	}
	public int getPoints() { return points;	}
	public void setPoints(int points) { this.points = points; }
	
	public void startMoving() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		Runnable moveTask = () -> {
			boolean needsNewDirection = true;
			Direction randomDirection = getRandomDirection(this, Direction.class);
			needsNewDirection = !this.move(Board.getInstance(), randomDirection);
			if (needsNewDirection) {				
				randomDirection = getRandomDirection(this, Direction.class);
			}
		};
		executor.scheduleAtFixedRate(moveTask, 0, 500, TimeUnit.MILLISECONDS);
	}
	
	public static Direction getRandomDirection(Enemy e, Class<Direction> d) { // public?
		Element[][] cells = Board.getInstance().getCells();
		int[] currPosition = e.getPosition();
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
