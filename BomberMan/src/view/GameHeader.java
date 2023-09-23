package view;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * view of the game header, with lives, timer, points etc.
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class GameHeader extends StackPane implements Observer {
	
	public static GameHeader gameHeader;
	
	private ImageView baseHeader = new ImageView(new Image("images/-board-header/header-start.png"));
	private ImageView lives = new ImageView();
	
	private GameHeader() {
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(view.Item.ITEM_HEIGHT * 2);
		this.setPrefWidth(scene.getWidth());
		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().add(baseHeader);
		
		// set initial lives
		
		setLives(model.BomberMan.INITIAL_LIVES);
		this.getChildren().add(lives);
		GameHeader.setMargin(lives, new Insets(27, 0, 0, 110));
		
		
	}
	
	/**
	 * singleton pattern
	 * @return only existing instance of the header, or create one
	 */
	public static GameHeader getInstance() {
		if (gameHeader == null) {
			gameHeader = new GameHeader();
		}
		return gameHeader;
	}

	/**
	 * resets the timer to its initial state. used when starting new levels
	 */
	public void resetTimer() {
		ObservableList<Node> children = this.getChildren();
		children.removeAll(children);
		
		children.add(baseHeader);
		setLives(model.BomberMan.getInstance().getLives());
		children.add(lives);
		GameHeader.setMargin(lives, new Insets(27, 0, 0, 110));
	}

	public void setLives(int lives) {
		this.lives.setImage(new Image("images/-board-header/-numbers/" + lives + ".png"));
	}

	/**
	 * OO pattern. used to update lives of bomberman
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.LOSE_LIFE) && o instanceof model.BomberMan) {
			setLives(((model.BomberMan) o).getLives());
		}
		
	}
}
