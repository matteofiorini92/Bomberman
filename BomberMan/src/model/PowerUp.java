package model;

public abstract class PowerUp extends Item {
	
	private int points;

	public PowerUp(Position position, int points)
	{
		super(position);
		this.points = points;
	}

	public int getPoints() { return points; }

}
