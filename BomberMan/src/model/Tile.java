package model;

public abstract class Tile extends Item {
	
	private TileType type;

	public Tile(Position position, TileType type)
	{
		super(position);
		this.setType(type);
	}

	public TileType getType() { return type; }
	public void setType(TileType type) { this.type = type; }

}
