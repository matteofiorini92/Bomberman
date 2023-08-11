package model;

public class Bug extends Enemy {
	
	public static final int BUG_POINTS = 400;

	public Bug(Position position, Double speed, Direction direction)
	{
		super(position, speed, direction, BUG_POINTS);
	}

}
