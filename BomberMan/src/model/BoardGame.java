package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class BoardGame extends Observable {
	
	private static BoardGame board;
	public static final int WIDTH = 17;
	public static final int HEIGHT = 13;
	private Element[][] cells = new Element[HEIGHT][WIDTH];
	/**
	 * Loading k:v pairs from resources/tiles/model.properties into Elements
	 */
	private static Map<String, Class<? extends Element>> Elements = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
		utilities.LoadProperties.loadStringClassProperties(Elements, "resources/tiles/model.properties");
	}
	
	private BoardGame() {}
	
	
	/**
	 * Singleton pattern
	 * @return only existing instance of board (creates one if it doesn't already exist)
	 */
	public static BoardGame getInstance(){
		if (board == null) {
			board = new BoardGame();
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
	
	public void print() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				String className[] = cells[i][j].getClass().toString().split("\\.");
				int l = className.length;
				System.out.print(className[l-1].charAt(0) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public void printExt() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				String className[] = cells[i][j].getClass().toString().split("\\.");
				int l = className.length;
				System.out.print(className[l-1] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
