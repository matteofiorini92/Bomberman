package model;

public class Helix extends Enemy {

	public static final int HELIX_POINTS = 100;

	public Helix(int[] position, Double speed)
	{
		super(position, speed, HELIX_POINTS);
	}

}
