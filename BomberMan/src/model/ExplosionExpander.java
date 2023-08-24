package model;

public class ExplosionExpander extends PowerUp {
	
	public static final int EXPLOSION_EXPANDER_POINTS = 200;

	public ExplosionExpander()
	{
		super(EXPLOSION_EXPANDER_POINTS);
	}

	@Override
	public void execute()
	{
		super.execute();
		model.BomberMan.getInstance().incRange();
		model.Player.getInstance().addPoints(EXPLOSION_EXPANDER_POINTS);
	}
}
