package model;

public abstract class Tile extends Item {
	
	private TileType type;
	private String label;

	public Tile(int[] position, TileType type)
	{
		super(position);
		this.setType(type);
	}

	public TileType getType() { return type; }
	public void setType(TileType type) { this.type = type; }

	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }

}
