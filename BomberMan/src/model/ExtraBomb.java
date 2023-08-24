package model;

public class ExtraBomb extends PowerUp {
	
	public static final int EXTRA_BOMB_POINTS = 10;

	public ExtraBomb()
	{
		super(EXTRA_BOMB_POINTS);
	}

	@Override
	public void execute()
	{
		super.execute();
		model.BomberMan.getInstance().incBombs();
		model.Player.getInstance().addPoints(EXTRA_BOMB_POINTS);
	}

}
