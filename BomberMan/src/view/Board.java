package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

@SuppressWarnings("deprecation")
public class Board implements Observer {
	
	private static view.Board board;
	/**
	 * Loading k:v pairs from resources/tiles/ currLevel .properties into imageFiles
	 */
	private static Map<String, String> imageFiles = new HashMap<>();
	static {
		String currLevel = controller.Main.getCurrLevel();
		Properties tilesProperties = new Properties();
        try (FileInputStream input = new FileInputStream("resources/tiles/" + currLevel + ".properties")) {
            tilesProperties.load(input);
            Set<Object> keys = tilesProperties.keySet();
            for (Object key : keys) {
            	imageFiles.put((String) key, (String)tilesProperties.get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

	public void setTile(Item item, int[] position) { 
		gridPane.add(item, position[1], position[0]);
		tiles[position[0]][position[1]] = item;
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		model.Element[][] cells = ((model.Board) o).getCells();
		for (int i=0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				String desc = ((model.Tile)cells[i][j]).getLabel();
            	Item item;
            	
            	if (desc.equals("sw") || desc.equals("sws")) { // needs a separate assignement because of animation
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
