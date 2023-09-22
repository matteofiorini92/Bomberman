package model;

/**
 * model of the bug enemy
 * @author Matteo
 *
 */
public class Bug extends Enemy {
	
	public static final int POINTS = 400;
	public static final double SPEED  = 1.0;
	public static final int INITIAL_LIVES = 2;

	public Bug(int[] position)
	{
		super(position, SPEED, POINTS, INITIAL_LIVES);
	}

}
