package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.image.Image;

public class Board {
	
	private Item[][] tiles = new Item[13][17];
	private static Map<String, String> imageFiles = new HashMap<>();
	private Group root = new Group();

	public Board(String levelNumber)
	{
		imageFiles.put("tc", "01");
		imageFiles.put("ht", "02");
		imageFiles.put("htf", "02-flipped");
		imageFiles.put("t", "04");
		imageFiles.put("tcf", "01-flipped");
		imageFiles.put("l", "09");
		imageFiles.put("sl", "10");
		imageFiles.put("ess", "12");
		imageFiles.put("ers", "21");
		imageFiles.put("e", "20");
		imageFiles.put("w", "11");
		imageFiles.put("slf", "10-flipped");
		imageFiles.put("lf", "09-flipped");
		imageFiles.put("bc", "17");
		imageFiles.put("sbl", "18");
		imageFiles.put("b", "19");
		imageFiles.put("sblf", "18-flipped");
		imageFiles.put("bcf", "17-flipped");
		imageFiles.put("sw", "05 06 07 08");
		imageFiles.put("sws", "13 14 15 16");
		
		fillBaseBoard(levelNumber);

	}
	
	public Group getRoot() { return root; }

	private void fillBaseBoard(String levelNumber) {
		String desc;
		String levelFilePath = "src/levels/" + levelNumber + ".txt";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
		
	        for (int i = 0; i < 13; i++) {
	        	line = reader.readLine();
				String[] elements = line.split("\\s+");
	            for (int j = 0; j < 17; j++) {
	            	
	            	
	            	if (elements[j].equals("sw") || elements[j].equals("sws")) {
	            		desc = elements[j];
	            		SoftWall sw = new SoftWall(imageFiles.get(desc));
	            		tiles[i][j] = sw;
	            		
		                int swX = j * Tile.ITEM_HEIGHT;
		                int swY = i * Tile.ITEM_WIDTH;
	            		
	        	        sw.setLayoutX(swX);
	        	        sw.setLayoutY(swY);
	        	        root.getChildren().add(sw);
	            	}
	            	
	            	
	            	else {	            		
	            		desc = elements[j];
	            		Image image = new Image("tiles-64x64/" + imageFiles.get(desc) + ".png");
	            		Tile tile = new Tile(desc, image);
	            		
	            		tiles[i][j] = tile;
	            		
	            		int tileX = j * Tile.ITEM_HEIGHT;
	            		int tileY = i * Tile.ITEM_WIDTH;
	            		
	            		tile.setLayoutX(tileX);
	            		tile.setLayoutY(tileY);
	            		
	            		root.getChildren().add(tile);
	            	}
	            }
	        }
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
