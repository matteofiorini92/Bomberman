package model;

public class ExplosionExpander extends PowerUp {
	
	public static final int EXPLOSION_EXPANDER_POINTS = 200;

	public ExplosionExpander(Position position)
	{
		super(position, EXPLOSION_EXPANDER_POINTS);
	}
	
	public void increaseRange(BomberMan b) {
		int currRange = b.getRange();
		b.setRange(++currRange);
	}
}
