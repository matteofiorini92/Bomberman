package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public class Bomb extends Item {

	private static Board board = model.Board.getInstance();
	private int range;
	
	public Bomb(int range, int[] position)
	{
		super(position);
		board.setCell(this, position);
		this.range = 4;		
	}

	public int getRange() { return range; }
	
	public void trigger() {
		setChanged();
		notifyObservers("TRIGGER");
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
						// TODO destroy soft wall
						System.out.println("I'm hitting a soft wall on my " + surrounding.getKey().toString());
					}
					if (e instanceof model.Character) {
						// TODO kills character
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
			List<Object> options = new ArrayList();
			options.add("EXPLODE");
			options.add(surroundings);
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


	
}
