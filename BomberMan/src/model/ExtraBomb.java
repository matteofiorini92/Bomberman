package model;

public class ExtraBomb extends PowerUp {
	
	public static final int EXTRA_BOMB_POINTS = 10;

	public ExtraBomb(Position position)
	{
		super(position, EXTRA_BOMB_POINTS);
	}
	
	public void increaseBombs(Bomberman b) {
		int currBombs = b.getBombs();
		b.setBombs(++currBombs);
	}

}
