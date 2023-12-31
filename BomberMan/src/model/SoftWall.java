package model;

/**
 * model of a soft wall (wall that can be destroyed by a bomb)
 * implements Hiding as soft walls can hide power ups and exit
 * @author Matteo
 *
 */
public class SoftWall extends Wall implements Hiding {
	
	public static GameBoard board = GameBoard.getInstance();
	
	private Hidable hiddenHidable = null;
	
	public SoftWall(int[] position)
	{
		super(position);
	}

	public Hidable getHiddenHidable()	{ return hiddenHidable; }
	
	@Override
	public void setHiddenHidable(Hidable hiddenHidable) {	this.hiddenHidable = hiddenHidable; }
	
	/**
	 * destroy the soft wall due to a bomb's explosion
	 */
	@SuppressWarnings("deprecation")
	public void destroy() {
		// TODO  destroy after x seconds. create constant. "import" constant in view
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


}
