package model;

public class Accelerator extends PowerUp {
	
	public static final int ACCELERATOR_POINTS = 400;

	public Accelerator(Position position)
	{
		super(position, ACCELERATOR_POINTS);
	}
	
	public void accelerate(BomberMan b) {
		Double currSpeed = b.getSpeed();
		b.setSpeed(currSpeed+0.5);
	}

}
