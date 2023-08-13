package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class Board {
	
	private static Map<String, String> imageFiles = new HashMap<>();
	static {
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
	}
	
	private Item[][] tiles = new Item[13][17];
	private GridPane gridPane = new GridPane();

	public Board(String levelNumber)
	{		
		fillBoard(levelNumber);
	}
	
	public GridPane getGridPane() { return gridPane; }

	private void fillBoard(String levelNumber) {
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
	            	Item item;
	            	
	            	if (elements[j].equals("sw") || elements[j].equals("sws")) {
	            		item = new SoftWall(imageFiles.get(desc));
	            	}        	
	            	else {	            		
	            		Image image = new Image("tiles-64x64/" + imageFiles.get(desc) + ".png");
	            		item = new Tile(desc, image);
	            	}
	            	
	            	tiles[i][j] = item;
	            	
//	            	item.setLayoutX(j * Item.ITEM_HEIGHT);
//	            	item.setLayoutY(i * Item.ITEM_WIDTH);
	            	
	            	gridPane.add(item, j, i);
	            }
	        }
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
