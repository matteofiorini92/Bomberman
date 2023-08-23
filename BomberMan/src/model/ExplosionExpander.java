package model;

public class ExplosionExpander extends PowerUp {
	
	public static final int EXPLOSION_EXPANDER_POINTS = 200;

	public ExplosionExpander()
	{
		super(EXPLOSION_EXPANDER_POINTS);
	}
	
	public void increaseRange() {
		model.BomberMan b = model.BomberMan.getInstance();
		int currRange = b.getRange();
		b.setRange(++currRange);
	}
}
