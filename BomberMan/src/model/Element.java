package model;

import java.util.Observable;

/**
 * 
 * @author Matteo
 *
 */

@SuppressWarnings("deprecation")
public abstract class Element extends Observable {
	/**
	 * 
	 */
	
	private int[] position = new int[2];
	private boolean disappearOnWalkOn = false;
	private boolean disappearOnWalkOff = false;
	
	public Element(int[] position) {
		this.position = position;
	}

	public int[] getPosition() {	return position; }
	public void setPosition(int[] position) { this.position = position; }
//	public void setPosition(int[] prevPosition, int[] newPosition) { this.position = newPosition; }

	/**
	 * @return the disappearOnWalkOn
	 */
	public boolean disappearsOnWalkOn() { return disappearOnWalkOn; }

	/**
	 * @param disappearOnWalkOn the disappearOnWalkOn to set
	 */
	public void setDisappearOnWalkOn(boolean disappearOnWalkOn) { this.disappearOnWalkOn = disappearOnWalkOn; }

	/**
	 * @return the disappearOnWalkOff
	 */
	public boolean disappearsOnWalkOff() {	return disappearOnWalkOff; }

	/**
	 * @param disappearOnWalkOff the disappearOnWalkOff to set
	 */
	public void setDisappearOnWalkOff(boolean disappearOnWalkOff) { this.disappearOnWalkOff = disappearOnWalkOff; }
	
}
