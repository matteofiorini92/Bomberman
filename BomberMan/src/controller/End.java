package controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class End {
	
	public static void load(EndOptions option) {
		
		ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
		baseGroupChildren.removeAll(baseGroupChildren);
		
		view.Boards.End boardEnd; 
		
		switch (option) {
			case CONTINUE:
				boardEnd = new view.Boards.Continue();
				break;
			case VICTORY:
				boardEnd = new view.Boards.Victory();
				break;
			case DEFEAT:
				boardEnd = new view.Boards.Defeat();
				break;
			default:
				boardEnd = new view.Boards.Continue();
		}
		
		view.BaseGroup.getInstance().getChildren().add(boardEnd);
		
		
		// probably need to "kill" all enemies
		
	}

}
