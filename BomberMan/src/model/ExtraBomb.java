package model;

public class ExtraBomb extends PowerUp {
	
	public static final int EXTRA_BOMB_POINTS = 10;

	public ExtraBomb()
	{
		super(EXTRA_BOMB_POINTS);
	}
	
	public void increaseBombs() {
		model.BomberMan b = model.BomberMan.getInstance();
		int currBombs = b.getBombs();
		b.setBombs(++currBombs);
	}

}
