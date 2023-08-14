package model;

public class Bug extends Enemy {
	
	public static final int BUG_POINTS = 400;

	public Bug(int[] position, Double speed)
	{
		super(position, speed, BUG_POINTS);
	}

}
