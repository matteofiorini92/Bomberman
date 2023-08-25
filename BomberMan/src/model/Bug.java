package model;

public class Bug extends Enemy {
	
	public static final int BUG_POINTS = 400;
	public static final double BUG_SPEED  = 1.0;
	public static final int INITIAL_LIVES = 2;

	public Bug(int[] position)
	{
		super(position, BUG_SPEED, BUG_POINTS, INITIAL_LIVES);
	}

}
