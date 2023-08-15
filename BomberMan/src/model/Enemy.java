package model;

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
			Direction randomDirection = getRandomDirection(Direction.class);
			needsNewDirection = !this.move(Board.getInstance(), randomDirection);
			if (needsNewDirection) {				
				randomDirection = getRandomDirection(Direction.class);
			}
		};
		executor.scheduleAtFixedRate(moveTask, 0, 375, TimeUnit.MILLISECONDS);

		
	}
	
	public static Direction getRandomDirection(Class<Direction> d) {
        Direction[] directions = d.getEnumConstants();
        Random random = new Random();
        int index = random.nextInt(directions.length);
        return directions[index];
    }
	
}
