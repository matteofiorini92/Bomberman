package model;

public abstract class Character extends Element implements Move {
	
	private Double speed;
	private Direction direction;
	
	
	public Character(Position position, Double speed, Direction direction)
	{
		super(position);
		this.setSpeed(speed);
		this.setDirection(direction);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }
	public Direction getDirection()	{ return direction;	}
	public void setDirection(Direction direction) {	this.direction = direction;	}
	
}
