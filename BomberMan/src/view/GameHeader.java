package view;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

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
	private StackPane score = new StackPane();
	
	private GameHeader() {
		Scene scene = BaseScene.getInstance();
		this.setPrefHeight(view.Item.ITEM_HEIGHT * 2);
		this.setPrefWidth(scene.getWidth());
		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().add(baseHeader);
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
		
		setScore(model.BomberMan.getInstance().getScore());
		children.add(score);
		
		
		
	}

	private void setLives(int lives) {
		this.lives.setImage(new Image("images/-board-header/-numbers/" + lives + ".png"));
	}
	
	private void setScore(int score) {
		ObservableList<Node> currentScore = this.score.getChildren();
		this.score.getChildren().removeAll(currentScore);
		StackPane tempPane = new StackPane();
		tempPane.setAlignment(Pos.TOP_LEFT);
		char[] scoreAsArray = String.valueOf(score).toCharArray();
		for (int i = 0; i < scoreAsArray.length; i++) {
			ImageView imageView = new ImageView();
			imageView.setImage(new Image("images/-board-header/-numbers/" + scoreAsArray[i] + ".png"));
			tempPane.getChildren().add(imageView);
			StackPane.setMargin(imageView, new Insets(0,0,0,i * 24));
			
		}
		this.score.getChildren().add(tempPane);
		GameHeader.setMargin(this.score, new Insets(27, 0, 0, 365 - 24*scoreAsArray.length));
	}
	
	

	/**
	 * OO pattern. used to update lives and score of bomberman
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		Object[] args = (Object[]) arg;
		
		if (args[0].equals(model.ChangeType.LOSE_LIFE) && o instanceof model.BomberMan) {
			setLives(((model.BomberMan) o).getLives());
		}
		if (args[0].equals(model.ChangeType.CHANGE_POINTS) && o instanceof model.BomberMan) {
			setScore(((model.BomberMan) o).getScore());
		}
		
	}
}
