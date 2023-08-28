package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Direction;
import model.Element;
import view.BoardProfileLookUp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private model.BomberMan modelBm;
	private view.BomberMan viewBm;
	private model.Board modelBoard;
	private view.Board viewBoard;
	
	private Group root = view.BaseGroup.getInstance();
	private Scene scene = view.BaseScene.getInstance();
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
		/**
		 * define window properties
		 */
		
		Image icon = new Image("bm_icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("BomberMan");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
		
		
		view.BoardWelcome wBoard = new view.BoardWelcome();
		root.getChildren().add(wBoard);
		
//		Button newPlayerButton = (Button)scene.lookup("#NEW_PLAYER");
//		newPlayerButton.setOnMouseClicked(event -> loadNewPlayerScreen(wBoard));
//		
//		Button existingPlayerButton = (Button)scene.lookup("#EXISTING_PLAYER");
//		if (existingPlayerButton != null) {		
//			existingPlayerButton.setOnMouseClicked(event -> loadSearchPlayerScreen(wBoard));
//		}
		

	}
	
//	private void loadSearchPlayerScreen(view.BoardWelcome wBoard) {
//		root.getChildren().remove(wBoard);
//		view.BoardProfileLookUp profileLookUpBoard = new view.BoardProfileLookUp();
//		root.getChildren().add(profileLookUpBoard);
//		
//		scene.lookup("#SEARCH").setOnMouseClicked(event -> loadExistingPlayerScreen(profileLookUpBoard));
//		
//		
//	}
	
	
//	private void loadExistingPlayerScreen(BoardProfileLookUp profileLookUpBoard)
//	{
//		// get nickname from profileLookUpBoard
//		String nickname = ((TextField) scene.lookup("#NICKNAME_LOOKUP")).getText().toLowerCase();
//		if (!nickname.equals("")) {
//			Path[] existing = {};
//
//			// check existing files and filter by nickname selected by user
//			try (Stream<Path> stream = Files.list(Path.of("resources/playerProfiles"))) {
//				existing = stream
//						.filter(file -> file.getFileName().toString().equals(nickname + ".txt"))
//						.toArray(Path[]::new);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			// if nickname doesn't exist >> ERROR
//			if (existing.length == 0) {
//				scene.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(true);
//			}
//			
//			// else load player's information
//			else {
//				try {
//					scene.lookup("#ERR_NICKNAME_DOES_NOT_EXIST").setVisible(false);
//					String[] playerStats = Files.readString(existing[0]).split("\\s+");
//					// 0 > avatar color
//					// 1 > wins
//					// 2 > losses
//					// 3 > total score
//					loadPlayerScreen(profileLookUpBoard, nickname, playerStats);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
//	private void loadPlayerScreen(view.BoardProfileLookUp profileLookUpBoard, String nickname, String[] args) {
//		root.getChildren().remove(profileLookUpBoard);
//		view.BoardExistingProfile newProfileBoard = new view.BoardExistingProfile(
//				Integer.parseInt(args[1]),
//				Integer.parseInt(args[2]),
//				Integer.parseInt(args[3]),
//				nickname,
//				model.Avatar.valueOf(args[0])
//				);
//		root.getChildren().add(newProfileBoard);
//		TextField nicknameTextField = (TextField) scene.lookup("#NICKNAME");
//		nicknameTextField.setText(nickname);
//		nicknameTextField.setDisable(true);
//		
//		
//		scene.lookup("#WHITE").setOpacity(0.5);
//		scene.lookup("#"+args[0]).setOpacity(1);
//		
//		scene.lookup("#NEW_GAME").setDisable(false);
//		
//		// TODO add effects to buttons!!!!!!
//		
//		
//	}

//	private void loadNewPlayerScreen(view.BoardWelcome wBoard) {
//		root.getChildren().remove(wBoard);
//		view.BoardNewProfile newProfileBoard = new view.BoardNewProfile();
//		root.getChildren().add(newProfileBoard);
//		
//		Button saveProfile = (Button)scene.lookup("#SAVE_PROFILE");
//		saveProfile.setOnMouseClicked(event -> {
//			saveNewProfile(newProfileBoard);
//		});
//		
//		Button newGame = (Button)scene.lookup("#NEW_GAME");
//		newGame.setOnMouseClicked(event -> loadLevel(newProfileBoard, 1));
//		
//	}
	
	
//	private void saveNewProfile(view.BoardNewProfile newProfileBoard) {
//		
//		// get nickname from newProfileBoard
//		String nickname = ((TextField) scene.lookup("#NICKNAME")).getText().toLowerCase();
//		
//		if (!nickname.equals("")) {
//			Object[] alreadyExisting = {};
//
//			// check existing files and filter by nickname selected by user
//			try (Stream<Path> stream = Files.list(Path.of("resources/playerProfiles"))) {
//				alreadyExisting = stream
//						.filter(file -> file.getFileName().toString().equals(nickname + ".txt"))
//						.toArray();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			
//			// if nickname already exists >> ERROR
//			if (alreadyExisting.length > 0) {
//				scene.lookup("#ERR_NICKNAME_TAKEN").setVisible(true);
//			}
//			
//			// else create new player instance and new playerProfile file and activate new game button
//			else {
//				scene.lookup("#ERR_NICKNAME_TAKEN").setVisible(false);
//				scene.lookup("#NEW_GAME").setDisable(false);
//				model.Player player = model.Player.getInstance();
//				player.setName(nickname);
//				player.setAvatar(newProfileBoard.getSelectedAvatar());
//				
//				
//				
//				File playerProfile = new File("resources/playerProfiles/" + nickname + ".txt");
//				try {
//					playerProfile.createNewFile();
//					FileWriter fileWriter = new FileWriter(playerProfile);
//					fileWriter.write(
//							player.getAvatar() + " " +
//									player.getWins() + " " +
//									player.getLosses() + " " +
//									player.getScore()
//							);
//					fileWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	
	
	@SuppressWarnings("deprecation")
	private void loadLevel(view.BoardNewProfile newProfileBoard, int level) {
		
		root.getChildren().remove(newProfileBoard);
		
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
		StackPane boardItemsPane = viewBoard.getItemsPane();
		
		model.Element[][] cells = modelBoard.getCells();
		model.SoftWall[] softWalls;
		
		Stream<model.Element[]> streamOfArrays = Arrays.stream(cells);										// convert cells to a stream of arrays
//		Stream<model.Element> flattenedStream = streamOfArrays.flatMap(array -> Arrays.stream(array));		// flatten the stream (concatenate the arrays of the stream)
//		softWalls = flattenedStream
//				.filter(element -> element instanceof model.SoftWall)										// filter the flattened stream looking only for softWalls
//				.toArray(model.SoftWall[]::new);	
		
		// same thing, more chained functions, less readable?
		
		softWalls = streamOfArrays.flatMap(array -> Arrays.stream(array)					// flatten the stream (concatenate the arrays of the stream)
								  .filter(element -> element instanceof model.SoftWall))	// filter the flattened stream looking only for softWalls
								  .toArray(model.SoftWall[]::new);
		
		view.Item[][] tiles = viewBoard.getTiles();
		
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j].addObserver(tiles[i][j]);
			}
		}
		
		root.getChildren().add(boardGridPane);
		root.getChildren().add(boardItemsPane);
		
		/**
		 * initialise characters
		 */
		List<Object[]> characters = readCharactersFile(currLevel);
		List<model.Enemy> enemies = initialiseCharacters(characters, root);
		
		
		// collect list of Elements that can hide powerUps (enemies and softwalls)
		
		List<model.HidePowerUp> hidingElements;
		
		hidingElements = enemies.stream()							//convert Enemies to Elements
				.map(enemy -> (model.HidePowerUp)enemy)
				.collect(Collectors.toList());
		
		hidingElements.addAll(Arrays.stream(softWalls).toList());	//convert array of softwalls to list
		
		
		List<model.PowerUp> powerUps = readPowerUpsFile(currLevel);
		
		powerUps.stream().forEach(powerUp -> {
			int max = hidingElements.size();
			
			Random r = new Random();
			model.HidePowerUp hidingElement = hidingElements.get(r.nextInt(max));
			hidingElement.setHiddenPowerUp(powerUp);
			powerUp.setPosition(((Element) hidingElement).getPosition());
			hidingElements.remove(hidingElement);
		});
		
		
		// key listeners
		
		scene.setOnKeyPressed(this::handleKeyPressed);
		scene.setOnKeyReleased(this::handleKeyReleased);
		
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
	
	private List<model.PowerUp> readPowerUpsFile(String levelNumber) {
		String levelFilePath = "src/levels/" + levelNumber + "-power-ups.txt";
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
	private static final Double THROTTLE_DELAY = model.Character.INITIAL_TIME_FOR_MOVEMENT / model.BomberMan.getInstance().getSpeed();
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

	@SuppressWarnings("deprecation")
	private void processKeyPress(KeyEvent event) {
	    long currentTime = System.currentTimeMillis();

	    if (currentTime - lastKeyPressTime >= THROTTLE_DELAY) {
	    	lastKeyPressTime = currentTime;
		    if (event.getCode() == KeyCode.SPACE && modelBm.getBombs() > 0 && !(model.Board.getInstance().getCell(model.BomberMan.getInstance().getPosition()) instanceof model.Bomb)) {
		    	int currBombs = modelBm.getBombs();
		    	model.Bomb modelBomb = new model.Bomb(currBombs, modelBm.getPosition());
		    	view.Bomb viewBomb = new view.Bomb();
		    	modelBomb.addObserver(viewBomb);
		    	modelBm.decBombs();
		    	// get viewBm stackPane index in order to add the bomb behind it
		    	int viewBmStackPaneIndex = root.getChildren().indexOf(viewBoard.getGridPane());
		    	
		    	root.getChildren().add(viewBmStackPaneIndex + 1, viewBomb);
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
		return currLevel;
	}
}
