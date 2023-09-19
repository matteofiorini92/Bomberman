package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import javafx.application.Platform;

public class Bomb extends Item {
	
	public static final int TIME_FOR_EXPLOSION = 3000;

	private static BoardGame board = model.BoardGame.getInstance();
	private int range;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> future;
	
	public Bomb(int range, int[] position)
	{
		super(position);
		board.setCell(this, position);
		this.range = model.BomberMan.getInstance().getRange();
	}

	public int getRange() { return range; }
	public ScheduledFuture<?> getScheduledFuture() { return future; }
	
	@SuppressWarnings("deprecation")
	public void trigger() {
		Runnable explode = () -> {
			Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread 				
				explode();
			});
		};
		
		future = executor.schedule(explode, TIME_FOR_EXPLOSION, TimeUnit.MILLISECONDS);
		Object[] args = { model.ChangeType.TRIGGER };
		setChanged();
		notifyObservers(args);
	}
	
	@SuppressWarnings("deprecation")
	public void explode() {
		Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread
			Map<Direction, List<Element>> surroundings = checkSurroundings();
			for(Map.Entry<Direction, List<Element>> surrounding : surroundings.entrySet()) {
				List<Element> values = surrounding.getValue();
				for (Element e : values) {					
					if (e instanceof model.SoftWall) {
						((SoftWall) e).destroy();
					}
					if (e instanceof model.Character) {
						((Character) e).loseLife();
					} 
					if (e instanceof model.Bomb) {
						((model.Bomb)e).getScheduledFuture().cancel(true);
						((model.Bomb)e).explode();
					}
				}
			}
			
			// simplistic. if the bomberman never leaves the cell, the cell shouldn't become empty.
			int[] bombPosition = this.getPosition();
			
			Platform.runLater(() -> {			
				board.setCell(new EmptyTile(bombPosition), bombPosition);
			});
			
			model.BomberMan.getInstance().incBombs();
			
			String[][] simplifiedSurroundings = simplifySurroundings(surroundings);
			Object[] args = { model.ChangeType.EXPLODE, simplifiedSurroundings };
			setChanged();
			notifyObservers(args);
		});
	}

	
	
	private Map<Direction, List<Element>> checkSurroundings() {
		int[] currPosition = this.getPosition();
		Element e;
		
		// array of "tuples"
		Object[][] directions = {
				{Direction.UP, 0, -1},
				{Direction.DOWN, 0, 1},
				{Direction.LEFT, -1, 0},
				{Direction.RIGHT, 1, 0}
		};

		/**
		 *  the result is a map like this
		 *  { UP: [empty, empty, wall],
		 *    DOWN: [character],
		 *    etc.
		 *  }
		 */
		
		Map<Direction, List<Element>> surroundings = new HashMap<Direction, List<Element>>();
		
		for (Object[] tuple : directions) {
			List<Element> l = new ArrayList<Element>();
			Direction d = (Direction) tuple[0];
			surroundings.put(d, l);
			int valX = (int)tuple[1];
			int valY = (int)tuple[2];
			for (int i = 1; i <= range; i++) {
				int x = currPosition[1] + valX * i;
				int y = currPosition[0] + valY * i;
				e = board.getCell(new int[] {y, x});
				l.add(e);
				if (!(e instanceof EmptyTile) && !(e instanceof Hidable)) { // check if there's an obstacle that confines the explosion
					break;
				}
			}
		}
	    return surroundings;
	}
	
	private String[][] simplifySurroundings(Map<Direction, List<Element>> surroundings) {
		
		String[][] result = new String[range*2+1][range*2+1];
		result[range][range] = "ex"; //explosion is at the centre of the grid
		
		
		
		/**
		 * could use this here as well ?
		 * 		Object[][] directions = {
				{Direction.UP, 0, -1},
				{Direction.DOWN, 0, 1},
				{Direction.LEFT, -1, 0},
				{Direction.RIGHT, 1, 0}
		};
		 */
		
		
		
		List<Element> elements;
		Predicate<Element> isWallOrSoftWall = element ->
        	element instanceof Wall || element instanceof SoftWall;
		
		elements = surroundings.get(Direction.UP);
		elements.removeIf(isWallOrSoftWall);
		
		for (int i = 1; i <= elements.size(); i++) {
			result[range - i][range] = i == range ? "edgeUp" : "midUp";
		}
		
		elements = surroundings.get(Direction.DOWN);
		elements.removeIf(isWallOrSoftWall);
		
		for (int i = 1; i <= elements.size(); i++) {
			result[range + i][range] = i == range ? "edgeDown" : "midDown";
		}
		
		elements = surroundings.get(Direction.LEFT);
		elements.removeIf(isWallOrSoftWall);
		
		for (int i = 1; i <= elements.size(); i++) {
			result[range][range - i] = i == range ? "edgeLeft" : "midLeft";
		}
		
		elements = surroundings.get(Direction.RIGHT);
		elements.removeIf(isWallOrSoftWall);
		
		for (int i = 1; i <= elements.size(); i++) {
			result[range][range + i] = i == range ? "edgeRight" : "midRight";
		}
		
		return result;
	}


	
}
