package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Board extends Observable {
	
	private static Board board;
	public static final int WIDTH = 17;
	public static final int HEIGHT = 13;
	private Element[][] cells = new Element[HEIGHT][WIDTH];
	private static Map<String, Class<? extends Element>> Elements = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
		Elements.put("tc", model.Wall.class);			// top corner
		Elements.put("st", model.Wall.class);			// second top
		Elements.put("stf", model.Wall.class);			// second top flipped
		Elements.put("t", model.Wall.class);			// top
		Elements.put("tcf", model.Wall.class);			// top corner flipped
		Elements.put("l", model.Wall.class);			// left
		Elements.put("sl", model.Wall.class);			// second left
		Elements.put("ebs", model.EmptyTile.class);		// empty with border shadow
		Elements.put("ews", model.EmptyTile.class);		// empty with wall shadow
		Elements.put("esws", model.EmptyTile.class);	// empty with soft wall shadow
		Elements.put("e", model.EmptyTile.class);		// empty
		Elements.put("w", model.Wall.class);			// wall
		Elements.put("slf", model.Wall.class);			// second left flipped
		Elements.put("lf", model.Wall.class);			// left flipped
		Elements.put("bc", model.Wall.class);			// bottom corner
		Elements.put("sbl", model.Wall.class);			// second bottom left
		Elements.put("b", model.Wall.class);			// bottom
		Elements.put("sblf", model.Wall.class);			// second bottom left flipped
		Elements.put("bcf", model.Wall.class);			// bottom corner flipped
		Elements.put("sw", model.SoftWall.class);		// soft wall
		Elements.put("sws", model.SoftWall.class);		// soft wall with shadow
	}
	
	private Board() {}
	
	
	/**
	 * Singleton pattern
	 * @return only existing instance of board (creates one if it doesn't already exist)
	 */
	public static Board getInstance(){
		if (board == null) {
			board = new Board();
		}
		return board;
	}
	
	public void fillEmptyBoard(String levelNumber) {
		/**
		 * initialize board
		 */
		String desc;
		String levelFilePath = "src/levels/" + levelNumber + ".txt";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
		
	        for (int i = 0; i < HEIGHT; i++) {
	        	line = reader.readLine();
				String[] elements = line.split("\\s+");
	            for (int j = 0; j < WIDTH; j++) {
	            	
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
	public Element getCell(int[] coordinates) { return cells[coordinates[0]][coordinates[1]]; }
	public void setCell(Element e, int[] coordinates) { this.cells[coordinates[0]][coordinates[1]] = e; }

}
