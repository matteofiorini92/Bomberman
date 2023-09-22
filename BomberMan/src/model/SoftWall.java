package model;

public class SoftWall extends Tile implements Hiding {
	
	public static final TileType SOFT_WALL = TileType.SOFT_WALL;
	public static GameBoard board = model.GameBoard.getInstance();
	
	private model.Hidable hiddenHidable = null;
	
	public SoftWall(int[] position)
	{
		super(position, SOFT_WALL);
	}

	public model.Hidable getHiddenHidable()	{ return hiddenHidable; }
	
	@Override
	public void setHiddenHidable(model.Hidable hiddenHidable) {	this.hiddenHidable = hiddenHidable; }
	
	@SuppressWarnings("deprecation")
	public void destroy() {
		int[] position = this.getPosition();
		if (isHidingSomething()) {
			board.setCell((Element)hiddenHidable, position);
		} else {
			board.setCell(new EmptyTile(position), position);
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean isHidingSomething() { return hiddenHidable != null; }

	@Override
	public void showHiddenHidable()
	{
		// TODO Auto-generated method stub
		
	}


}
