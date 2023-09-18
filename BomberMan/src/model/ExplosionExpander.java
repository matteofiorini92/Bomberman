package model;

public class ExplosionExpander extends PowerUp {
	
	public static final int EXPLOSION_EXPANDER_POINTS = 200;
	public static final int MAX_RANGE = 10;

	public ExplosionExpander()
	{
		super(EXPLOSION_EXPANDER_POINTS);
	}

	@Override
	public boolean execute()
	{
		super.execute();
		BomberMan bomberMan = BomberMan.getInstance();
		if (bomberMan.getRange() < MAX_RANGE) { model.BomberMan.getInstance().incRange(); }
		return false;
	}
}
