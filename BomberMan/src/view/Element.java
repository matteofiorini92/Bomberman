package view;

import java.util.Observer;

import javafx.scene.layout.StackPane;
import model.Position;

public abstract class Element extends StackPane implements Observer {

	private Position position;
	
	public Element(Position position) {
		this.position = position;
	}

	public Position getPosition() {	return position; }
	public void setPosition(Position position) { this.position = position; }

}
