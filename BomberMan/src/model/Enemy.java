package model;

public abstract class Enemy extends Character {
	
	private int points;

	public Enemy(int[] position, Double speed, int points)
	{
		super(position, speed);
		this.points = points;
	}
	public int getPoints() { return points;	}
	public void setPoints(int points) { this.points = points; }
	
}
