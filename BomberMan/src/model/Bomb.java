package model;

public class Bomb extends Item {

	private int range;
	
	public Bomb(BomberMan b)
	{
		super(b.getPosition());
		range = b.getRange();
	}

	public int getRange() { return range; }
	
}
