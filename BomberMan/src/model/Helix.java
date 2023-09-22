package model;

/**
 * model of the helix enemy
 * @author Matteo
 *
 */
public class Helix extends Enemy {

	public static final int POINTS = 100;
	public static final double SPEED  = 0.5;
	public static final int INITIAL_LIVES = 1;

	public Helix(int[] position)
	{
		super(position, SPEED, POINTS, INITIAL_LIVES);
	}

}
