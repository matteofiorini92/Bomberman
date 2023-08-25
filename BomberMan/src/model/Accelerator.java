package model;

public class Accelerator extends PowerUp {
	
	public static final int ACCELERATOR_POINTS = 400;
	public static final Double MAX_SPEED = 5.0;

	public Accelerator()
	{
		super(ACCELERATOR_POINTS);
	}
	
	@Override
	public void execute() {
		super.execute();
		Player.getInstance().addPoints(ACCELERATOR_POINTS);
		BomberMan bomberMan = BomberMan.getInstance();
		if (bomberMan.getSpeed() < MAX_SPEED) {	model.BomberMan.getInstance().incSpeed(0.5); }
	}

}
