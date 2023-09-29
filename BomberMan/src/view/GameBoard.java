package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * the view for the board of the game. observes the relevant model class
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class GameBoard extends StackPane implements Observer {
	
	private static GameBoard gameBoard;
	/**
	 * Loading k:v pairs from resources/tiles/ currLevel .properties into imageFiles
	 */
	private static Map<String, String> imageFiles = new HashMap<>();
	
	private int height = model.GameBoard.HEIGHT;
	private int width = model.GameBoard.WIDTH;
	private Item[][] tiles = new Item[height][width];
	private GridPane gridPane = new GridPane();
	private Rectangle rectangle = new Rectangle(Item.ITEM_WIDTH * model.GameBoard.WIDTH, Item.ITEM_HEIGHT * model.GameBoard.HEIGHT, Color.TRANSPARENT);
	private StackPane itemsPane = new StackPane(rectangle);
	private static String tilesPath;
	
	
	private GameBoard() {
		this.setPrefHeight(Item.ITEM_HEIGHT * model.GameBoard.HEIGHT);
		itemsPane.setPrefHeight(this.getPrefHeight());
		itemsPane.setAlignment(Pos.TOP_LEFT);
		this.getChildren().add(gridPane);
		this.getChildren().add(itemsPane);		
	}
	
	/**
	 * singleton pattern
	 * @return only existing instance of board (creates one if it doesn't already exist)
	 */
	public static GameBoard getInstance(){
		if (gameBoard == null) {
			gameBoard = new GameBoard();
		}
		return gameBoard;
	}
	
	public GridPane getGridPane() { return gridPane; }
	public StackPane getItemsPane()	{ return itemsPane; }
	public Item[][] getTiles() { return tiles; }

	public void setTile(Item item, int[] position) { 
		gridPane.add(item, position[1], position[0]);
		tiles[position[0]][position[1]] = item;
	}
	
	/**
	 * OO pattern
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		model.Element[][] cells = ((model.GameBoard) o).getCells();
		for (int i=0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				String desc = ((model.Tile)cells[i][j]).getLabel();
            	Item item;
            	
            	if (desc.equals("sw") || desc.equals("sws")) { // needs a separate assignment because of animation
            		item = new SoftWall(desc);
            	}        	
            	else {
            		Image image = new Image(tilesPath + imageFiles.get(desc) + ".png");
            		item = new Tile(desc, image);
            	}
            	
            	tiles[i][j] = item;
            	gridPane.add(item, j, i);
			}
		}
	}
	
	public static void loadImageFiles() {
		tilesPath = "images/-tiles/" + controller.Level.getCurrLevel() + "/";
		imageFiles.clear();
		String currLevel = controller.Level.getCurrLevel();
		utilities.LoadProperties.loadStringStringProperties(imageFiles, "resources/tiles/" + currLevel + ".properties");
	}

}
