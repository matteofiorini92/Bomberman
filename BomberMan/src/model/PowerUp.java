package model;

/**
 * model of power up. implements hidable as power ups can be hidden behind enemies and soft walls
 * @author Matteo
 *
 */
public abstract class PowerUp extends Item implements Hidable {
	
	private int points;

	/**
	 * constructor
	 * @param points the amount of points the bomberman gets by picking up the power up
	 */
	public PowerUp(int points)
	{
		super(null);
		this.points = points;
		this.setDisappearOnWalkOn(true);
	}

	public int getPoints() { return points; }
	
	/**
	 * each power up has its own effect, defined by the specific class
	 */
	@SuppressWarnings("deprecation")
	public void execute() {
		BomberMan bomberMan = BomberMan.getInstance();
		bomberMan.addPoints(points);
		setChanged();
		notifyObservers();
	};

}
