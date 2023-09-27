package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Element;
import view.BaseScene;

/**
 * instantiates a level with characters, board, timer, keyListeners etc.
 * @author Matteo
 *
 */
@SuppressWarnings("deprecation")
public class Level implements Observer {
	
	private static String level;
	private model.SoftWall[] softWalls;
	private List<model.Enemy> enemies;
	private static MediaPlayer mediaPlayer;
	
	
	/**
	 * constructor. used when the player would lose a game but decides to continue
	 * @param level the level to load
	 * @param score the score to assign to the bomberman
	 */
	public Level(String level, int score) {
		new Level(level, true);
		model.BomberMan.getInstance().setScore(score);
	}
	
	/**
	 * constuctor. creates a level
	 * @param level the level to create
	 * @param needsReset true if this is a new game (including continue after defeat)
	 * 					 false if it's the next level of an existing game
	 */
	public Level(String level, boolean needsReset) {
		Level.level = level;
		
		
		// when starting a new game, as opposed to loading the next level in an existing game
		if (needsReset) {
			model.BomberMan.getInstance().reset();
			view.BomberMan.getInstance().reset();
			model.Enemy.getAliveEnemies()
				.stream()
				.filter(enemy -> enemy != null)
				.forEach(enemy -> enemy.removeFromBoard());
			model.Enemy.getAliveEnemies().clear();
		}
		
		view.GameBoard.loadImageFiles();
		view.Bomb.loadImageFiles();
		view.SoftWall.loadImageFiles();
		
		softWalls = GameBoard.load(level);
		enemies = Characters.load(level);
		
		// collect list of Elements that can hide powerUps (enemies and softwalls)
		
		hideElements(softWalls, enemies);
		
		
		enemies.stream().forEach(enemy -> enemy.startMoving());
		
		Timer.load(); 												// when the timer is loaded, it's also reset
		model.Timer modelTimer = model.Timer.getInstance();
		modelTimer.addObserver(this);
		
		model.BomberMan.getInstance().addObserver(this);
		playBackgroundMusic();
		
		// key listeners
		BaseScene baseScene = BaseScene.getInstance();
		
		baseScene.setOnKeyPressed(KeyListeners::handleKeyPressed);
		baseScene.setOnKeyReleased(KeyListeners::handleKeyReleased);
	}
	
	private void hideElements (model.SoftWall[] softWalls, List<model.Enemy> enemies) {
		
		List<model.Hiding> hidingElements;
		
		hidingElements = enemies.stream()							
				.map(enemy -> (model.Hiding) enemy )				//convert Enemies to Hiding
				.collect(Collectors.toList());
		
		hidingElements.addAll(Arrays.stream(softWalls).toList());	//convert array of softwalls to list
		
		
		List<model.PowerUp> powerUps = readPowerUpsFile(level);
		
		powerUps.stream().forEach(powerUp -> {
			int max = hidingElements.size();
			
			Random r = new Random();
			model.Hiding hidingElement = hidingElements.get(r.nextInt(max));
			hidingElement.setHiddenHidable(powerUp);
			powerUp.setPosition(((Element) hidingElement).getPosition());
			hidingElements.remove(hidingElement);
		});
		
		// hide exit
		
		List<model.SoftWall> remaniningSoftWalls = hidingElements
			.stream()
			.filter(hidingElement -> hidingElement instanceof model.SoftWall)
			.map(hidingElement -> (model.SoftWall)hidingElement)
			.toList();
		
		model.Exit exit = new model.Exit();
		exit.addObserver(this);
		
		
		int max = remaniningSoftWalls.size();
		Random r = new Random();
		model.Hiding sotWall = remaniningSoftWalls.get(r.nextInt(max));
		sotWall.setHiddenHidable(exit);
		exit.setPosition(((model.SoftWall) sotWall).getPosition());
	}

	
	
	private static List<model.PowerUp> readPowerUpsFile(String level) {
		String levelFilePath = "src/levels/" + level + "-power-ups.txt";
		String line;
		List<model.PowerUp> result = new ArrayList<>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(levelFilePath));
			line = reader.readLine();
			while (line != null) {
				@SuppressWarnings("unchecked")
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
	
	

	public static String getCurrLevel() { return level; }
	
	public static void playBackgroundMusic() {
		String fileName = "resources/musics/backgroundTune.mp3";
		File backgroundMusicFile = new File (fileName);
		Media media = new Media(backgroundMusicFile.toURI().toString());
		if (mediaPlayer != null) mediaPlayer.stop();
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	public static void pauseBackgroundMusic() {
		mediaPlayer.pause();
	}

	
	/**
	 * called when timer hits 0, bomberman dies or exits a level
	 */
	@Override
	public void update(Observable o, Object arg)
	{
		
		Object[] args = (Object[]) arg;
		
		// update when time hits 0 or when bomberman dies
		if (
				(args[0].equals(model.ChangeType.TIME_DECREASE) && (int)args[1] == 0 )|| 
				(args[0].equals(model.ChangeType.DIE) && o instanceof model.BomberMan)
			) {
			Runnable loadContinue = () -> {
				Platform.runLater(() -> { // to have UI related operations all run on the JavaFX thread 				
					End.load(EndOptions.CONTINUE);
				});
			};
			
			Executors.newSingleThreadScheduledExecutor().schedule(loadContinue, view.Character.TIME_FOR_DEATH, TimeUnit.MILLISECONDS);
		}
		
	
		// update when exit executes
		if (args[0].equals(model.ChangeType.GET_OUT)) {
			int currLevel = Game.levelConverter(level);
			model.BomberMan modelBm = model.BomberMan.getInstance();
			view.BomberMan viewBm = view.BomberMan.getInstance();
			model.GameBoard modelBoard = model.GameBoard.getInstance();
			
			ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
			baseGroupChildren.removeAll(baseGroupChildren);
			
			view.GameBoard.getInstance().getChildren().remove(viewBm);
			
			modelBm = model.BomberMan.getInstance();
			modelBm.setPosition(model.BomberMan.INITIAL_POSITION);
			modelBoard.setCell(modelBm, model.BomberMan.INITIAL_POSITION);
			if (currLevel < Game.FINAL_LEVEL) {
				new Level(Game.levelConverter(++currLevel), false);
			} 
			else {
				End.load(EndOptions.VICTORY);
			}
			
		}
	}
	
}
