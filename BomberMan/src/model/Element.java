package model;

import java.util.Observable;

/**
 * 
 * @author Matteo
 *
 */

public abstract class Element extends Observable {
	/**
	 * 
	 */
	
	private int[] position = new int[2];
	private String label;
	
	public Element(int[] position) {
		this.position = position;
	}

	public int[] getPosition() {	return position; }
	public void setPosition(int[] position) { 
		this.position = position;
		setChanged();
		notifyObservers();
	}
	
}
