package model;

public class Accelerator extends PowerUp {
	
	public static final int ACCELERATOR_POINTS = 400;

	public Accelerator()
	{
		super(ACCELERATOR_POINTS);
	}
	
	public void accelerate() {
		model.BomberMan b = model.BomberMan.getInstance();
		Double currSpeed = b.getSpeed();
		b.setSpeed(currSpeed+0.5);
	}

}
