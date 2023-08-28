package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Direction;
import model.Element;

public class LoadLevel {
	
	private model.BomberMan modelBm;
	private view.BomberMan viewBm;
	private model.BoardGame modelBoard;
	private view.BoardGame viewBoard;
	private static String level;
	
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

	public LoadLevel(String level) {
		
		/**
		 * initialise board
		 */
		this.level = level;
		view.BaseScene baseScene = view.BaseScene.getInstance();
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		modelBoard = model.BoardGame.getInstance();
		viewBoard = view.BoardGame.getInstance();
		modelBoard.addObserver(viewBoard);
		modelBoard.fillEmptyBoard(level);
		GridPane boardGridPane = viewBoard.getGridPane();
		StackPane boardItemsPane = viewBoard.getItemsPane();
		
		model.Element[][] cells = modelBoard.getCells();
		model.SoftWall[] softWalls;
		
		Stream<model.Element[]> streamOfArrays = Arrays.stream(cells);						// convert cells to a stream of arrays
		
		softWalls = streamOfArrays.flatMap(array -> Arrays.stream(array)					// flatten the stream (concatenate the arrays of the stream)
								  .filter(element -> element instanceof model.SoftWall))	// filter the flattened stream looking only for softWalls
								  .toArray(model.SoftWall[]::new);
		
		view.Item[][] tiles = viewBoard.getTiles();
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j].addObserver(tiles[i][j]);
			}
		}
		
		baseGroup.getChildren().add(boardGridPane);
		baseGroup.getChildren().add(boardItemsPane);
		
		/**
		 * initialise characters
		 */
		List<Object[]> characters = readCharactersFile(level);
		List<model.Enemy> enemies = initialiseCharacters(characters, baseGroup);
		
		
		// collect list of Elements that can hide powerUps (enemies and softwalls)
		
		List<model.HidePowerUp> hidingElements;
		
		hidingElements = enemies.stream()							//convert Enemies to Elements
				.map(enemy -> (model.HidePowerUp)enemy)
				.collect(Collectors.toList());
		
		hidingElements.addAll(Arrays.stream(softWalls).toList());	//convert array of softwalls to list
		
		
		List<model.PowerUp> powerUps = readPowerUpsFile(level);
		
		powerUps.stream().forEach(powerUp -> {
			int max = hidingElements.size();
			
			Random r = new Random();
			model.HidePowerUp hidingElement = hidingElements.get(r.nextInt(max));
			hidingElement.setHiddenPowerUp(powerUp);
			powerUp.setPosition(((Element) hidingElement).getPosition());
			hidingElements.remove(hidingElement);
		});
		
		
		// key listeners
		
		baseScene.setOnKeyPressed(this::handleKeyPressed);
		baseScene.setOnKeyReleased(this::handleKeyReleased);
		
		for (model.Enemy enemy : enemies) {
			enemy.startMoving();
		}
		
	}
	
	private List<Object[]> readCharactersFile(String level) {
		String levelFilePath = "src/levels/" + level + "-characters.txt";
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
	
	private List<model.PowerUp> readPowerUpsFile(String level) {
		String levelFilePath = "src/levels/" + level + "-power-ups.txt";
		String line;
		List<model.PowerUp> result = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
			line = reader.readLine();
			while (line != null) {
				Class<? extends model.PowerUp> powerUp = (Class<? extends model.PowerUp>) Class.forName("model."+line);		// get class from name of the class
				model.PowerUp powerUpInstance = (model.PowerUp) powerUp.getDeclaredConstructor().newInstance();				// instantiate a new object of that class
	            result.add(powerUpInstance);
				line = reader.readLine();
	        }
	        reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
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
		
		modelBoard.setCell(modelBm, model.BomberMan.INITIAL_POSITION);
		root.getChildren().add(viewBm);
		modelBm.setInvincible(true);
		return enemies;
	}
	
	private long lastKeyPressTime = 0;
	private static Double throttleDelay = model.Character.INITIAL_TIME_FOR_MOVEMENT / model.BomberMan.getInstance().getSpeed();
	private boolean keyHeld = false;
	
	public static void updateThrottleDelay() {
		throttleDelay = model.Character.INITIAL_TIME_FOR_MOVEMENT / model.BomberMan.getInstance().getSpeed();
	}

	private void handleKeyPressed(KeyEvent event) {
	    if (!keyHeld) {
	        keyHeld = true;
	        processKeyPress(event);
	    }

    }

	private void handleKeyReleased(KeyEvent event) {
	    keyHeld = false;
	}

	@SuppressWarnings("deprecation")
	private void processKeyPress(KeyEvent event) {
		
		
		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
	    long currentTime = System.currentTimeMillis();

	    if (currentTime - lastKeyPressTime >= throttleDelay) {
	    	lastKeyPressTime = currentTime;
		    if (event.getCode() == KeyCode.SPACE && modelBm.getBombs() > 0 && !(model.BoardGame.getInstance().getCell(model.BomberMan.getInstance().getPosition()) instanceof model.Bomb)) {
		    	int currBombs = modelBm.getBombs();
		    	model.Bomb modelBomb = new model.Bomb(currBombs, modelBm.getPosition());
		    	view.Bomb viewBomb = new view.Bomb();
		    	modelBomb.addObserver(viewBomb);
		    	modelBm.decBombs();
		    	// get viewBm stackPane index in order to add the bomb behind it
		    	int viewBmStackPaneIndex = baseGroup.getChildren().indexOf(viewBoard.getGridPane());
		    	
		    	baseGroup.getChildren().add(viewBmStackPaneIndex + 1, viewBomb);
		    	modelBomb.trigger();	        	
		    }
		    else {	        	
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
		return level;
	}
}
