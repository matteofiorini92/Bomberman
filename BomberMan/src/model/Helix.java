package model;

public class Helix extends Enemy {

	public static final int HELIX_POINTS = 100;
	public static final double HELIX_SPEED  = 0.5;
	public static final int INITIAL_LIVES = 1;

	public Helix(int[] position)
	{
		super(position, HELIX_SPEED, HELIX_POINTS, INITIAL_LIVES);
	}

}
