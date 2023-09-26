package model;

import java.util.Observable;

/**
 * model of a generic element of the game (e.g. characters, items etc)
 * @author Matteo
 */

@SuppressWarnings("deprecation")
public abstract class Element extends Observable {
	
	private int[] position = new int[2];
	private boolean disappearOnWalkOn = false;
	
	public Element(int[] position) {
		this.position = position;
	}

	public int[] getPosition() {	return position; }
	public void setPosition(int[] position) { 
		this.position = position; 
	}

	public boolean disappearsOnWalkOn() { return disappearOnWalkOn; }
	public void setDisappearOnWalkOn(boolean disappearOnWalkOn) { this.disappearOnWalkOn = disappearOnWalkOn; }
}
