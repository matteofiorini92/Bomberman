package model;

/**
 * model of explosion expander power up
 * @author Matteo
 *
 */
public class ExplosionExpander extends PowerUp {
	
	public static final int EXPLOSION_EXPANDER_POINTS = 200;
	public static final int MAX_RANGE = 10;

	public ExplosionExpander()
	{
		super(EXPLOSION_EXPANDER_POINTS);
	}

	/**
	 * increase bomberman range if it's not already at its max
	 */
	@Override
	public void execute()
	{
		super.execute();
		BomberMan bomberMan = BomberMan.getInstance();
		if (bomberMan.getRange() < MAX_RANGE) { model.BomberMan.getInstance().incRange(); }
//		return false;
	}
}
