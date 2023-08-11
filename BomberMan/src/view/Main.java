package view;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {

	Tile[][] board = new Tile[17][13];
	Map<String, String> imageFiles = new HashMap<>();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		
		imageFiles.put("tc", "01");
		imageFiles.put("ht", "02");
		imageFiles.put("htf", "02-flipped");
		imageFiles.put("t", "04");
		imageFiles.put("tcf", "01-flipped");
		imageFiles.put("l", "09");
		imageFiles.put("sl", "10");
		imageFiles.put("es", "12");
		imageFiles.put("e", "20");
		imageFiles.put("w", "11");
		imageFiles.put("slf", "10-flipped");
		imageFiles.put("lf", "09-flipped");
		imageFiles.put("bc", "17");
		imageFiles.put("sbl", "18");
		imageFiles.put("b", "19");
		imageFiles.put("sblf", "18-flipped");
		imageFiles.put("bcf", "17-flipped");
		
		
		
		Group root = new Group();
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		
		Image icon = new Image("bm_icon.png");
		
		String level = "src/levels/lv2-base.txt";
		String k;
		
        String[][] array2D = readTxtTo2DArray(level);
		
        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 17; y++) {
                k = array2D[x][y];
                Image i = new Image("tiles-64x64/" + imageFiles.get(k) + ".png");
                Tile tile = new Tile(i);

                // Calculate positions based on tile index and size
                double tileX = y * Tile.TILE_WIDTH;
                double tileY = x * Tile.TILE_HEIGHT;

                tile.setLayoutX(tileX);
                tile.setLayoutY(tileY);

                root.getChildren().add(tile);
            }
        }

		
		stage.getIcons().add(icon);
		stage.setTitle("Bomberman");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static String[][] readTxtTo2DArray(String filePath) throws IOException {
	    List<String[]> rows = new ArrayList<>();

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;

	        while ((line = reader.readLine()) != null) {
	            String[] elements = line.split("\\s+"); // Split by whitespace
	            rows.add(elements);
	        }
	    }

	    String[][] array2D = new String[rows.size()][];
	    for (int i = 0; i < rows.size(); i++) {
	        array2D[i] = rows.get(i);
	    }

	    return array2D;
	}
}