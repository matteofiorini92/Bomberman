package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Direction;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private model.BomberMan modelBm;
	private view.BomberMan viewBm;
	private model.Board modelBoard;
	private view.Board viewBoard;
	private Group root = new Group();
	private int level = 1;
	private static String currLevel;
	
	private static Map<String, Class<? extends model.Enemy>> modelEnemies = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
		modelEnemies.put("he", model.Helix.class);
		modelEnemies.put("bug", model.Bug.class);
	}
	
	private static Map<String, Class<? extends view.Character>> viewCharacters = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	static {
		viewCharacters.put("he", view.Helix.class);
		viewCharacters.put("bug", view.Bug.class);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		Properties levelsProperties = new Properties();
        try (FileInputStream input = new FileInputStream("resources/levels.properties")) {
            levelsProperties.load(input);
            currLevel = (String)levelsProperties.get(Integer.toString(level));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		/**
		 * initialise board
		 */
		
		modelBoard = model.Board.getInstance();
		viewBoard = view.Board.getInstance();
		modelBoard.addObserver(viewBoard);
		modelBoard.fillEmptyBoard(currLevel);
		GridPane boardGridPane = viewBoard.getGridPane();
		
		model.Element[][] cells = modelBoard.getCells();
		view.Item[][] tiles = viewBoard.getTiles();
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j].addObserver(tiles[i][j]);
			}
		}
		
		
		
		// boardGridPane.setGridLinesVisible(true);
		root.getChildren().add(boardGridPane);
		
		
		/**
		 * initialise characters
		 */
		List<Object[]> characters = readCharactersFile(currLevel);
		List<model.Enemy> enemies = initialiseCharacters(characters, root);
		

		
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		scene.setOnKeyPressed(this::handleKeyPressed);
		scene.setOnKeyReleased(this::handleKeyReleased);
		
		/**
		 * define window properties
		 */
		
		Image icon = new Image("bm_icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("BomberMan");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
		for (model.Enemy enemy : enemies) {
			enemy.startMoving();
		}
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
	
	private List<model.Enemy> initialiseCharacters(List<Object[]> characters, Group root) {
		List<model.Enemy> enemies = new ArrayList<model.Enemy>();
		for (Object[] character : characters) {
			Class<? extends model.Character> modelCharacterClass = modelEnemies.get(character[0]);
			Class<? extends view.Character> viewCharacterClass = viewCharacters.get(character[0]);
			model.Character modelCharacter;
			view.Character viewCharacter;
			int coordinateX = (int) character[1];
			int coordinateY = (int) character[2];
			int[] position = {coordinateX, coordinateY};
			try {
				modelCharacter = modelCharacterClass.getDeclaredConstructor(int[].class).newInstance(position);
				viewCharacter = viewCharacterClass.getDeclaredConstructor(int[].class).newInstance(position); // to improve SHOULD POSITION BE USED TO CONSTRUCT VIEW CHARACTERS??
				modelCharacter.addObserver(viewCharacter);
				enemies.add((model.Enemy)modelCharacter);
				modelBoard.setCell(modelCharacter, position);
				root.getChildren().add(viewCharacter);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		modelBm = model.BomberMan.getInstance();
		viewBm = new view.BomberMan();
		modelBm.addObserver(viewBm);
		
		modelBoard.setCell(modelBm, modelBm.INITIAL_POSITION);
		root.getChildren().add(viewBm);
		return enemies;
	}
	
	private long lastKeyPressTime = 0;
	private static final long THROTTLE_DELAY = 375; // Milliseconds
	private boolean keyHeld = false;

	private void handleKeyPressed(KeyEvent event) {
	    if (!keyHeld) {
	        keyHeld = true;
	        processKeyPress(event);
	    }

    }

	private void handleKeyReleased(KeyEvent event) {
	    keyHeld = false;
	}

	private void processKeyPress(KeyEvent event) {
	    long currentTime = System.currentTimeMillis();

	    if (currentTime - lastKeyPressTime >= THROTTLE_DELAY) {
	        lastKeyPressTime = currentTime;
	        
	        
	        
	        if (event.getCode() == KeyCode.SPACE && modelBm.getBombs() > 0) {
	        	int currBombs = modelBm.getBombs();
	        	model.Bomb modelBomb = new model.Bomb(currBombs, modelBm.getPosition());
	        	view.Bomb viewBomb = new view.Bomb();
	        	modelBomb.addObserver(viewBomb);
	        	modelBm.setBombs(--currBombs);
	        	List<Node> nodes = root.getChildren();
	        	// get viewBm stackPane index in order to add the bomb behind it !!!!!!!!!!!!!!!!!!!!!!
	        	int viewBmStackPaneIndex = root.getChildren().indexOf(viewBoard.getGridPane());
	        	
	        	root.getChildren().add(viewBmStackPaneIndex + 1, viewBomb);
	        	modelBomb.trigger();	        	
	        } else {	        	
	        	Direction direction = null;
	        	try {	        	
	        		switch (event.getCode()) {
	        		case DOWN:
	        			direction = Direction.DOWN;
	        			break;
	        		case RIGHT:
	        			direction = Direction.RIGHT;
	        			break;
	        		case UP:
	        			direction = Direction.UP;
	        			break;
	        		case LEFT:
	        			direction = Direction.LEFT;
	        			break;
	        		default:
	        			break;	
	        		}
	        		modelBm.move(direction);
	        		
	        	} catch (NullPointerException e) {
	        		System.out.println("Invalid command");
	        	}
	        }
	    }

	    if (keyHeld) {
	    	Platform.runLater(() -> processKeyPress(event));
	    }
	}

	public static String getCurrLevel()
	{
		return currLevel;
	}
}
