package model;

public abstract class Character extends Element implements Move {
	
	private Double speed;
	
	public Character(int[] position, Double speed)
	{
		super(position);
		this.setSpeed(speed);
	}
	
	public Double getSpeed() { return speed; }
	public void setSpeed(Double speed) { this.speed = speed; }

}
