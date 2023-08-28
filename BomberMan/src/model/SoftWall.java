package model;

public class SoftWall extends Tile implements HidePowerUp {
	
	public static final TileType SOFT_WALL = TileType.SOFT_WALL;
	public static BoardGame board = model.BoardGame.getInstance();
	
	private model.PowerUp hiddenPowerUp = null;
	
	public SoftWall(int[] position)
	{
		super(position, SOFT_WALL);
	}

	public model.PowerUp getHiddenPowerUp()	{ return hiddenPowerUp; }
	public void setHiddenPowerUp(model.PowerUp hiddenPowerUp) {	this.hiddenPowerUp = hiddenPowerUp; }
	
	@SuppressWarnings("deprecation")
	public void destroy() {
		int[] position = this.getPosition();
		if (isHidingSomething()) {
			board.setCell(hiddenPowerUp, position);
		} else {
			board.setCell(new EmptyTile(position), position);
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isHidingSomething() { return hiddenPowerUp != null; }

	@Override
	public void showHiddenPowerUp()
	{
		// TODO Auto-generated method stub
		
	}


}
