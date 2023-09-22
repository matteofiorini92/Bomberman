package controller;

import java.util.Arrays;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * to load the game board with its cells and configuration
 * @author Matteo
 *
 */
public abstract class GameBoard {
	
	/**
	 * 
	 * @param level the level of the board to load
	 * @return an array with all the board softwalls (used to hide power ups and exit)
	 */
	@SuppressWarnings("deprecation")
	public static model.SoftWall[] load(String level) {
		
		view.BaseGroup.getInstance().getChildren().add(view.GameScreen.getInstance());
		
		model.GameBoard modelBoard = model.GameBoard.getInstance();
		view.GameBoard viewBoard = view.GameBoard.getInstance();
		modelBoard.addObserver(viewBoard);
		
		// populate modelBoard based on the relevant file, and the viewBoard due to OO
		modelBoard.fillBoard(level);
		
		model.Element[][] cells = modelBoard.getCells();
		
		// set every single view tile observer of the relevant model cell
		view.Item[][] tiles = viewBoard.getTiles();
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j].addObserver(tiles[i][j]);
			}
		}
		
		
		Stream<model.Element[]> streamOfArrays = Arrays.stream(cells);	// convert cells to a stream of arrays
		model.SoftWall[] softWalls = streamOfArrays
				.flatMap(array -> Arrays.stream(array)					// flatten the stream
				.filter(element -> element instanceof model.SoftWall))	// filter the flattened stream looking only for softWalls
				.toArray(model.SoftWall[]::new);

		return softWalls;
	}

}
