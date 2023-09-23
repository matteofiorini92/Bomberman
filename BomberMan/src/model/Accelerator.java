package model;

/**
 * model of accelerator power up
 * @author Matteo
 *
 */
public class Accelerator extends PowerUp {
	
	public static final int ACCELERATOR_POINTS = 400;
	public static final Double MAX_SPEED = 5.0;

	public Accelerator()
	{
		super(ACCELERATOR_POINTS);
	}
	
	/**
	 * increase speed by 0.5 if it's not already at it's max
	 */
	@Override
	public void execute() {
		super.execute();
		BomberMan bomberMan = BomberMan.getInstance();
		if (bomberMan.getSpeed() < MAX_SPEED) { bomberMan.incSpeed(0.5); }
//		return false;
	}

}
