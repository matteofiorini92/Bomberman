package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import javafx.application.Platform;

public class Bomb extends Item {

	private static Board board = model.Board.getInstance();
	private int range;
	
	public Bomb(int range, int[] position)
	{
		super(position);
		board.setCell(this, position);
		this.range = 3;		
	}

	public int getRange() { return range; }
	
	public void trigger() {
		setChanged();
		notifyObservers(model.ChangeType.TRIGGER);
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.schedule(this::explode, 3000, TimeUnit.MILLISECONDS);
	}
	
	public void explode() {
		Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread 				
			System.out.println("BOOOOOM");
			Map<Direction, List<Element>> surroundings = checkSurroundings();
			for(Map.Entry<Direction, List<Element>> surrounding : surroundings.entrySet()) {
				List<Element> values = surrounding.getValue();
				for (Element e : values) {					
					if (e instanceof model.SoftWall) {
						((SoftWall) e).destroy();
						System.out.println("I'm hitting a soft wall on my " + surrounding.getKey().toString());
					}
					if (e instanceof model.Character) {
						// TODO kills character
						((Character) e).loseLife();
						System.out.println("I'm hitting a character on my " + surrounding.getKey().toString());
					}
					if (e instanceof model.EmptyTile) {
						// TODO becomes explosion
						System.out.println("I'm hitting an empty tile on my " + surrounding.getKey().toString());
					} 
					if (e instanceof model.Bomb) {
						// TODO explode
						System.out.println("I'm hitting a bomb on my " + surrounding.getKey().toString());
					}
				}
			}
			
			int[] bombPosition = this.getPosition();
			board.setCell(new EmptyTile(bombPosition), bombPosition);
			
			
			model.BomberMan bm = model.BomberMan.getInstance();
			bm.setBombs(bm.getBombs()+1);
			
			
			List<Object> options = new ArrayList();
			options.add(model.ChangeType.EXPLODE);
			String[][] simplifiedSurroundings = simplifySurroundings(surroundings);
			options.add(simplifiedSurroundings);
			setChanged();
			notifyObservers(options);
		});
		
	}
	
	private Map<Direction, List<Element>> checkSurroundings() {
		int[] currPosition = this.getPosition();
		Element e;
		
		Object[][] directions = {
				{Direction.UP, 0, -1},
				{Direction.DOWN, 0, 1},
				{Direction.LEFT, -1, 0},
				{Direction.RIGHT, 1, 0}
		};

		Map<Direction, List<Element>> surroundings = new HashMap<Direction, List<Element>>();
		
		for (Object[] direction : directions) {
			List<Element> l = new ArrayList<Element>();
			Direction d = (Direction) direction[0];
			surroundings.put(d, l);
			int valX = (int)direction[1];
			int valY = (int)direction[2];
			for (int i = 1; i <= range; i++) {
				int x = currPosition[1] + valX * i;
				int y = currPosition[0] + valY * i;
				e = board.getCell(new int[] {y, x});
				l.add(e);
				if (!(e instanceof EmptyTile)) { // check if there's an obstacle that confines the explosion
					break;
				}
			}
		}
		
	    return surroundings;
	}
	
	private String[][] simplifySurroundings(Map<Direction, List<Element>> surroundings) {
		String[][] result = new String[range*2+1][range*2+1];
		int[] currPosition = this.getPosition();
		
		result[range][range] = "ex"; //explosion is at the centre of the grid
		
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
