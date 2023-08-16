package model;

public class Bomb extends Item {

	private static Board board = model.Board.getInstance();
	private int range;
	
	public Bomb(int range, int[] position)
	{
		super(position);
		board.setCell(this, position);
		this.range = range;		
	}

	public int getRange() { return range; }
	
	public void trigger() {		
		setChanged();
		notifyObservers("TRIGGER");
	}
	
}
