package model;

public class Bomb extends Item {

	private int range;
	
	public Bomb(int range, int[] position)
	{
		super(position);
		this.range = range;
		
	}

	public int getRange() { return range; }
	
	public void trigger() {		
		setChanged();
		notifyObservers("TRIGGER");
	}
	
}
