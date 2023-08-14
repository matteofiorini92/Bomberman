package model;

public abstract class PowerUp extends Item {
	
	private int points;

	public PowerUp(int[] position, int points)
	{
		super(position);
		this.points = points;
	}

	public int getPoints() { return points; }

}
