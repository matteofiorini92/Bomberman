package model;

public class Accelerator extends PowerUp {
	
	public static final int ACCELERATOR_POINTS = 400;

	public Accelerator()
	{
		super(ACCELERATOR_POINTS);
	}
	
	@Override
	public void execute() {
		super.execute();
		model.BomberMan.getInstance().incSpeed(0.5);
		model.Player.getInstance().addPoints(ACCELERATOR_POINTS);
	}

}
