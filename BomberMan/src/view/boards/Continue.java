package view.boards;

import controller.EndOptions;

/**
 * view of continue board
 * @author Matteo
 *
 */
public class Continue extends End {
	
	public Continue() {
		
		
		super(
			"Continue?",
			"Yes",
			30,
			event -> {
				String level = controller.Level.getCurrLevel();
				int currScore = model.BomberMan.getInstance().getScore();
				controller.Game.load(level, currScore - 500);
			},
			"No",
			30,
			event -> controller.End.load(EndOptions.DEFEAT)
		);
		
	}
	
}
