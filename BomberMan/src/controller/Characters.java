package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * manage all characters
 * @author Matteo
 *
 */
public abstract class Characters {
	
	private static Map<String, Class<? extends model.Enemy>> modelEnemies = new HashMap<>();
	private static Map<String, Class<? extends view.Character>> viewCharacters = new HashMap<>();
	
	// this needs to be updated every time a new enemy class is implemented
	static {
		modelEnemies.put("he", model.Helix.class);
		modelEnemies.put("bug", model.Bug.class);
		viewCharacters.put("he", view.Helix.class);
		viewCharacters.put("bug", view.Bug.class);
	}

	
	/**
	 * loads the characters for a specific level
	 * @param level the level of the characters to be loaded
	 * @return the list of enemies of the level (used to hide power ups)
	 */
	@SuppressWarnings("deprecation")
	public static List<model.Enemy> load(String level) {
		model.GameBoard modelBoard = model.GameBoard.getInstance();
		List<Object[]> fullInfoEnemies = readEnemiesFile(level);
		
		StackPane itemsPane = view.GameBoard.getInstance().getItemsPane();
		model.BomberMan modelBm = model.BomberMan.getInstance();
		view.BomberMan viewBm = view.BomberMan.getInstance();
		modelBm.addObserver(viewBm);
		modelBm.addObserver(view.GameHeader.getInstance());
		
		modelBoard.setCell(modelBm, model.BomberMan.INITIAL_POSITION);
		
		ObservableList<Node> itemsPaneChildren = itemsPane.getChildren();
		itemsPaneChildren.removeAll(itemsPaneChildren);
		itemsPaneChildren.add(viewBm);
		
		List<model.Enemy> enemies = new ArrayList<model.Enemy>();
				
		for (Object[] fullInfoEnemy : fullInfoEnemies) {
			Class<? extends model.Character> modelCharacterClass = modelEnemies.get(fullInfoEnemy[0]);
			Class<? extends view.Character> viewCharacterClass = viewCharacters.get(fullInfoEnemy[0]);
			model.Character modelCharacter;
			view.Character viewCharacter;
			int coordinateX = (int) fullInfoEnemy[1];
			int coordinateY = (int) fullInfoEnemy[2];
			int[] position = {coordinateX, coordinateY};
			try {
				modelCharacter = modelCharacterClass.getDeclaredConstructor(int[].class).newInstance(position);
				viewCharacter = viewCharacterClass.getDeclaredConstructor(int[].class).newInstance(position);
				modelCharacter.addObserver(viewCharacter);
				enemies.add((model.Enemy)modelCharacter);
				modelBoard.setCell(modelCharacter, position);
				itemsPane.getChildren().add(viewCharacter);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		modelBm.becomeInvincible();
		return enemies;
	}
	
	private static List<Object[]> readEnemiesFile(String level) {
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
}
