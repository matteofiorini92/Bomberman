package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Board extends Observable {
	
	public static final int WIDTH = 17;
	public static final int HEIGHT = 13;
	private Element[][] cells = new Element[HEIGHT][WIDTH];
	private static Map<String, Class<? extends Element>> Elements = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
		Elements.put("tc", model.Wall.class);
		Elements.put("ht", model.Wall.class);
		Elements.put("htf", model.Wall.class);
		Elements.put("t", model.Wall.class);
		Elements.put("tcf", model.Wall.class);
		Elements.put("l", model.Wall.class);
		Elements.put("sl", model.Wall.class);
		Elements.put("ess", model.EmptyTile.class);
		Elements.put("ers", model.EmptyTile.class);
		Elements.put("e", model.EmptyTile.class);
		Elements.put("w", model.Wall.class);
		Elements.put("slf", model.Wall.class);
		Elements.put("lf", model.Wall.class);
		Elements.put("bc", model.Wall.class);
		Elements.put("sbl", model.Wall.class);
		Elements.put("b", model.Wall.class);
		Elements.put("sblf", model.Wall.class);
		Elements.put("bcf", model.Wall.class);
		Elements.put("sw", model.SoftWall.class);
		Elements.put("sws", model.SoftWall.class);
	}
	
	public void fillEmptyBoard(String levelNumber) {
		/**
		 * initialize empty board
		 */
		String desc;
		String levelFilePath = "src/levels/" + levelNumber + ".txt";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
		
	        for (int i = 0; i < 13; i++) {
	        	line = reader.readLine();
				String[] elements = line.split("\\s+");
	            for (int j = 0; j < 17; j++) {
	            	
	            	desc = elements[j];
	            	Class<? extends Element> c = Elements.get(desc);
	            	Element e;
	            	int[] p = {i, j};
					try {
						e = c.getDeclaredConstructor(int[].class).newInstance(p);
						((Tile) e).setLabel(desc);
		            	cells[i][j] = e;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
	            }
	        }
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers();
	}
	

	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public Element[][] getCells() { return cells; }
	public Element getCell(int[] coordinates) { return cells[coordinates[0]][coordinates[1]];}

}
