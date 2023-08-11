package model;

public class Bomb extends Item {

	private int range;
	
	public Bomb(Bomberman b)
	{
		super(b.getPosition());
		range = b.getRange();
	}

	public int getRange() { return range; }
	
}
