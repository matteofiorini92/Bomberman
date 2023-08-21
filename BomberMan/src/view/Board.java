package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
//import model.Board;

public class Board implements Observer {
	
	private static view.Board board;
	private static Map<String, String> imageFiles = new HashMap<>();
	static {
		imageFiles.put("tc", "01");				// top corner
		imageFiles.put("st", "02");				// second top
		imageFiles.put("stf", "02-flipped");	// second top flipped
		imageFiles.put("t", "04");				// top
		imageFiles.put("tcf", "01-flipped");	// top corner flipped
		imageFiles.put("l", "09");				// left
		imageFiles.put("sl", "10");				// second left
		imageFiles.put("ebs", "12");			// empty with border shadow
		imageFiles.put("ews", "12");			// empty with wall shadow
		imageFiles.put("esws", "21");			// empty with soft wall shadow
		imageFiles.put("e", "20");				// empty
		imageFiles.put("w", "11");				// wall
		imageFiles.put("slf", "10-flipped");	// second left flipped
		imageFiles.put("lf", "09-flipped");		// left flipped
		imageFiles.put("bc", "17");				// bottom corner
		imageFiles.put("sbl", "18");			// second bottom left
		imageFiles.put("b", "19");				// bottom
		imageFiles.put("sblf", "18-flipped");	// second bottom left flipped
		imageFiles.put("bcf", "17-flipped");	// bottom corner flipped
		imageFiles.put("sw", "05 06 07 08");	// soft wall
		imageFiles.put("sws", "13 14 15 16");	// soft wall with shadow
	}
	
	private model.Board modelBoard = model.Board.getInstance();
	private int height = modelBoard.getHeight();
	private int width = modelBoard.getWidth();
	private Item[][] tiles = new Item[height][width];
	private GridPane gridPane = new GridPane();
	
	private Board() {}
	public static view.Board getInstance(){
		if (board == null) {
			board = new view.Board();
		}
		return board;
	}
	
	
	public GridPane getGridPane() { return gridPane; }
	public Item[][] getTiles() { return tiles; }
	public Item getTile(int[] position) { return tiles[position[0]][position[1]]; }
	public void setTile(Tile t, int[] position) { tiles[position[0]][position[1]] = t; }
	public void setTile(SoftWall sw, int[] position) { gridPane.add(sw, position[1], position[0]); }
	
	
	@Override
	public void update(Observable o, Object arg)
	{
		model.Element[][] cells = ((model.Board) o).getCells();
		for (int i=0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				String desc = ((model.Tile)cells[i][j]).getLabel();
            	Item item;
            	
            	if (desc.equals("sw") || desc.equals("sws")) {
            		item = new SoftWall(desc);
            	}        	
            	else {	            		
            		Image image = new Image("tiles-64x64/" + imageFiles.get(desc) + ".png");
            		item = new Tile(desc, image);
            	}
            	
            	tiles[i][j] = item;
            	gridPane.add(item, j, i);
			}
		}
		
	}
	
}
