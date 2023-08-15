package view;

import model.Direction;
import model.Position;

public abstract class Character extends Element {
	
    public static final int CHARACTER_HEIGHT = 96;
    public static final int CHARACTER_WIDTH = 64;

	private Direction direction;
	
	public Character(int[] position)
	{
		super(position);
		this.direction = Direction.INITIAL;
	}

	public Direction getDirection()	{ return direction;	}
	public void setDirection(Direction direction) {	this.direction = direction;	}
}
