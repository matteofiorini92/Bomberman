package controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * handles the end of a game
 * @author Matteo
 *
 */
public class End {
	
	/**
	 * loads a different screen based on the outcome of the game
	 * @param option one of CONTINUE, VICTORY or DEFEAT
	 */
	public static void load(EndOptions option) {
		
		Level.pauseBackgroundMusic();
		Timer.stop();
		ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
		baseGroupChildren.removeAll(baseGroupChildren);
		
		view.boards.End boardEnd; 
		
		switch (option) {
			case CONTINUE:
				boardEnd = new view.boards.Continue();
				break;
			case VICTORY:
				model.Player.getInstance().addWin();
				model.Player.getInstance().addPoints(model.BomberMan.getInstance().getScore());
				ExistingProfile.updateStats();
				boardEnd = new view.boards.Victory();
				break;
			case DEFEAT:
				model.Player.getInstance().addLoss();
				model.Player.getInstance().addPoints(model.BomberMan.getInstance().getScore());
				ExistingProfile.updateStats();
				boardEnd = new view.boards.Defeat();
				break;
			default:
				boardEnd = new view.boards.Continue();
		}
		
		view.BaseGroup.getInstance().getChildren().add(boardEnd);
		
	}

}
