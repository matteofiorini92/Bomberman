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
		
		
		ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
		baseGroupChildren.removeAll(baseGroupChildren);
		
		view.Boards.End boardEnd; 
		
		switch (option) {
			case CONTINUE:
				boardEnd = new view.Boards.Continue();
				break;
			case VICTORY:
				// TODO update player statistics
				boardEnd = new view.Boards.Victory();
				break;
			case DEFEAT:
				// TODO update player statistics
				boardEnd = new view.Boards.Defeat();
				break;
			default:
				boardEnd = new view.Boards.Continue();
		}
		
		view.BaseGroup.getInstance().getChildren().add(boardEnd);
		
	}

}
