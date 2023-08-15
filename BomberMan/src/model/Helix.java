package model;

public class Helix extends Enemy {

	public static final int HELIX_POINTS = 100;
	public static final double HELIX_SPEED  = 1.0;

	public Helix(int[] position)
	{
		super(position, HELIX_SPEED, HELIX_POINTS);
	}

}
