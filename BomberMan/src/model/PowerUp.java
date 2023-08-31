package model;

public abstract class PowerUp extends Item {
	
	private int points;

	public PowerUp(int points)
	{
		super(null);
		this.points = points;
		this.setDisappearOnWalkOn(true);
	}

	public int getPoints() { return points; }
	
	@SuppressWarnings("deprecation")
	public void execute() {
		BomberMan bomberMan = BomberMan.getInstance();
		bomberMan.addPoints(points);
		setChanged();
		notifyObservers();
	};

}
