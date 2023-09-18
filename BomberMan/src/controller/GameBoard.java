package controller;

import java.util.Arrays;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public abstract class GameBoard {
	
	@SuppressWarnings("deprecation")
	public static model.SoftWall[] load(String level) {
		
		ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
//		baseGroupChildren.removeAll(baseGroupChildren);
		baseGroupChildren.add(view.GameBoard.getInstance());
		
		model.BoardGame modelBoard = model.BoardGame.getInstance();
		view.GameBody viewBoard = view.GameBody.getInstance();
		modelBoard.addObserver(viewBoard);
		
		// at this point the modelBoard is populated based on the relevant file, and the viewBoard is populated as well due to OO
		modelBoard.fillEmptyBoard(level);
		
		model.Element[][] cells = modelBoard.getCells();
		
		Stream<model.Element[]> streamOfArrays = Arrays.stream(cells);						// convert cells to a stream of arrays
		
		model.SoftWall[] softWalls = streamOfArrays.flatMap(array -> Arrays.stream(array)					// flatten the stream (concatenate the arrays of the stream)
								  .filter(element -> element instanceof model.SoftWall))	// filter the flattened stream looking only for softWalls
								  .toArray(model.SoftWall[]::new);
		
		view.Item[][] tiles = viewBoard.getTiles();
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j].addObserver(tiles[i][j]);
			}
		}
		
		return softWalls;
	}

}
