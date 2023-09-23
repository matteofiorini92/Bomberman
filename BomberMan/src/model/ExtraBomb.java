package model;

/**
 * model of extra bomb powerup
 * @author Matteo
 *
 */
public class ExtraBomb extends PowerUp {
	
	public static final int EXTRA_BOMB_POINTS = 10;
	public static final int MAX_BOMBS = 10;

	public ExtraBomb()
	{
		super(EXTRA_BOMB_POINTS);
	}

	/**
	 * increase the number of bombs carried by the bomberman if it's not already at its max
	 */
	@Override
	public void execute()
	{
		super.execute();
		BomberMan bomberMan = BomberMan.getInstance();
		if (bomberMan.getBombs() < MAX_BOMBS) { bomberMan.incBombs(); }
	}

}
