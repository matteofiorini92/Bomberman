package model;

public abstract class Enemy extends Character {
	
	private int points;

	public Enemy(Position position, Double speed, Direction direction, int points)
	{
		super(position, speed, direction);
		this.points = points;
	}
	public int getPoints() { return points;	}
	public void setPoints(int points) { this.points = points; }
	
}
