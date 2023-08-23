package model;

public abstract class PowerUp extends Item {
	
	private int points;

	public PowerUp(int points)
	{
		super(null);
		this.points = points;
	}

	public int getPoints() { return points; }

}
