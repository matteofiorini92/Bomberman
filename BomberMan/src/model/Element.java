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
	
	private Position position;
	
	public Element(Position position) {
		this.position = position;
	}

	public Position getPosition() {	return position; }
	public void setPosition(Position position) { 
		this.position = position;
		setChanged();
		notifyObservers();
	}
	
}
