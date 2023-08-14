package controller;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private view.BomberMan viewBm;
	private model.BomberMan modelBm;
	private static Map<String, Class<? extends model.Enemy>> modelEnemies = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
//		modelCharacters.put("bm", model.BomberMan.class);
		modelEnemies.put("he", model.Helix.class);
		modelEnemies.put("bug", model.Bug.class);
	}
	
	private static Map<String, Class<? extends view.Enemy>> viewEnemies = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
//		viewCharacters.put("bm", view.BomberMan.class);
		viewEnemies.put("he", view.Helix.class);
		viewEnemies.put("bug", view.Bug.class);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		
		String levelNumber = "1-2";
		
		Group root = new Group();
		
		
		/**
		 * initialise board
		 */
		
		model.Board modelBoard = new model.Board();
		view.Board viewBoard = new view.Board();
		modelBoard.addObserver(viewBoard);
		modelBoard.fillEmptyBoard(levelNumber);
		GridPane boardGridPane = viewBoard.getGridPane();
		// boardGridPane.setGridLinesVisible(true);
		root.getChildren().add(boardGridPane);
		
		
		/**
		 * initialise characters
		 */
		List<Object[]> characters = readCharactersFile(levelNumber);
		initialiseCharacters(characters, root);
		

		
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		scene.setOnKeyPressed(this::handleKeyPressed);
		
		/**
		 * define window properties
		 */
		
		Image icon = new Image("bm_icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("Bomberman");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private List<Object[]> readCharactersFile(String levelNumber) {
		String levelFilePath = "src/levels/" + levelNumber + "-characters.txt";
		String line;
		List<Object[]> result = new ArrayList<Object[]>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
			line = reader.readLine();
			while (line != null) {
	            String[] elements = line.split("\\s+");
	            int coordinateX = Integer.parseInt(elements[1]);
	            int coordinateY = Integer.parseInt(elements[2]);
	            double speed = Double.parseDouble(elements[4]);
	            
	            Object[] parsedElements = new Object[]{elements[0], coordinateX, coordinateY, elements[3], speed};
	            result.add(parsedElements);
				line = reader.readLine();
	        }
	        reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void initialiseCharacters(List<Object[]> characters, Group root) {
		modelBm = new model.BomberMan();
		viewBm = new view.BomberMan();
		modelBm.addObserver(viewBm);
		root.getChildren().add(viewBm);
		for (Object[] character : characters) {
			Class<? extends model.Character> modelCharacterClass = modelEnemies.get(character[0]);
			Class<? extends view.Character> viewCharacterClass = viewEnemies.get(character[0]);
			model.Character mc;
			view.Character vc;
			int coordinateX = (int) character[1];
			int coordinateY = (int) character[2];
			int[] position = {coordinateX, coordinateY};
			try {
				mc = modelCharacterClass.getDeclaredConstructor(int[].class, double.class).newInstance(position, character[4]);
				vc = viewCharacterClass.getDeclaredConstructor(int[].class, model.Direction.class).newInstance(position, model.Direction.E); // to improve
				mc.addObserver(vc);
				root.getChildren().add(vc);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void handleKeyPressed(KeyEvent event) {
		int[] prevPosition = modelBm.getPosition();
		int[] newPosition;
		switch (event.getCode()) {
		case DOWN:
			modelBm.setDirection(model.Direction.S);
			newPosition = new int[] {prevPosition[0] + 1, prevPosition[1]};
			break;
		case UP:
			modelBm.setDirection(model.Direction.N);
			newPosition = new int[] {prevPosition[0] - 1, prevPosition[1]};
			
			break;
		case LEFT:
			modelBm.setDirection(model.Direction.W);
			newPosition = new int[] {prevPosition[0], prevPosition[1] - 1};
			break;
		case RIGHT:
			modelBm.setDirection(model.Direction.E);
			newPosition = new int[] {prevPosition[0], prevPosition[1] + 1};
			break;
		default:
			newPosition = prevPosition;
			break;
		}
		modelBm.setPosition(prevPosition, newPosition);
//        	int[] currPosition = modelBm.getPosition();
//        	int[] newPosition = new int[] {currPosition[0] + 1, currPosition[1]};
        	
//        	modelBm.setPosition(newPosition);
    }
}
