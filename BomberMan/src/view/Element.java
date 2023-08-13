package view;

import javafx.scene.layout.StackPane;
import model.Position;

public abstract class Element extends StackPane {

	private Position position;
	
	public Element(Position position) {
		this.position = position;
	}

	public Position getPosition() {	return position; }
	public void setPosition(Position position) { this.position = position; }

}
