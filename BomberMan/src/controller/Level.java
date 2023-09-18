package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import model.Element;
import view.BaseScene;

@SuppressWarnings("deprecation")
public class Level implements Observer {
	
	private static String level;
	private model.SoftWall[] softWalls;
	private List<model.Enemy> enemies;
	
	
//	public void load (String level) {
//		
//		
//	}
	
	public Level(String level) {
		
		this.level = level;
		
		softWalls = GameBoard.load(level);
		enemies = Characters.load(level);
		
		// collect list of Elements that can hide powerUps (enemies and softwalls)
		
		hideElements(softWalls, enemies);
		
		
		enemies.stream().forEach(enemy -> enemy.startMoving());
		// for (model.Enemy enemy : enemies) { enemy.startMoving(); }
		Timer.load();

		
		// key listeners
		
//		LoadKeyListeners keyListeners = new LoadKeyListeners();
		LoadKeyListeners keyListeners = LoadKeyListeners.getInstance();
		BaseScene baseScene = BaseScene.getInstance();
		
		baseScene.setOnKeyPressed(keyListeners::handleKeyPressed);
		baseScene.setOnKeyReleased(keyListeners::handleKeyReleased);
		
	}
	
	private void hideElements (model.SoftWall[] softWalls, List<model.Enemy> enemies) {
		
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
		model.HidePowerUp sotWall = remaniningSoftWalls.get(r.nextInt(max));
		sotWall.setHiddenPowerUp(exit);
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

	@Override
	public void update(Observable o, Object arg)
	{
		model.BomberMan modelBm = model.BomberMan.getInstance();
		view.BomberMan viewBm = view.BomberMan.getInstance();
		model.BoardGame modelBoard = model.BoardGame.getInstance();
		
		ObservableList<Node> baseGroupChildren = view.BaseGroup.getInstance().getChildren();
		baseGroupChildren.removeAll(baseGroupChildren);
		
		view.GameBody.getInstance().getChildren().remove(viewBm);
		
//		model.BomberMan.getInstance().startNewLevel();
		modelBm = model.BomberMan.getInstance();
		modelBm.setPosition(model.BomberMan.INITIAL_POSITION);
		modelBoard.setCell(modelBm, model.BomberMan.INITIAL_POSITION);
//		Level.load("1-2");
		new Level("1-2");
	}
}
