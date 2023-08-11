package model;

public class Helix extends Enemy {

	public static final int HELIX_POINTS = 100;

	public Helix(Position position, Double speed, Direction direction)
	{
		super(position, speed, direction, HELIX_POINTS);
	}

}
