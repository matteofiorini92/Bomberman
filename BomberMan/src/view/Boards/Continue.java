package view.Boards;

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
				new controller.Level(level);
			},
			"No",
			30,
			event -> controller.End.load(EndOptions.DEFEAT)
		);
		
	}
	
}
