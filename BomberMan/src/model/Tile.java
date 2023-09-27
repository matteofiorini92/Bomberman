package model;

/**
 * model of Tile element
 * @author Matteo
 *
 */
public abstract class Tile extends Element {
	
	private String label;

	public Tile(int[] position)
	{
		super(position);
	}

	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }

}
