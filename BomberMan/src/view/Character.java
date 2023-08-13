package view;

import model.Position;

public abstract class Character extends Element {
	
    public static final int CHARACTER_HEIGHT = 96;
    public static final int CHARACTER_WIDTH = 64;

	private Direction direction;
	
	public Character(Position position, Direction direction)
	{
		super(position);
		this.direction = direction;
	}

	public Direction getDirection()	{ return direction;	}
	public void setDirection(Direction direction) {	this.direction = direction;	}
}
