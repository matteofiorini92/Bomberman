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

public abstract class Characters {
	
	private static Map<String, Class<? extends model.Enemy>> modelEnemies = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	private static Map<String, Class<? extends view.Character>> viewCharacters = new HashMap<>(); // using generics as every cell could be a number of different subclasses of Element
	
	static {
		modelEnemies.put("he", model.Helix.class);
		modelEnemies.put("bug", model.Bug.class);
		viewCharacters.put("he", view.Helix.class);
		viewCharacters.put("bug", view.Bug.class);
	}

	@SuppressWarnings("deprecation")
	public static List<model.Enemy> load(String level) {
		model.BoardGame modelBoard = model.BoardGame.getInstance();
		List<Object[]> characters = readCharactersFile(level);
		
		StackPane itemsPane = view.GameBody.getInstance().getItemsPane();
		model.BomberMan modelBm = model.BomberMan.getInstance();
		view.BomberMan viewBm = view.BomberMan.getInstance();
		modelBm.addObserver(viewBm);
		modelBm.addObserver(view.GameHeader.getInstance());
		
		modelBoard.setCell(modelBm, model.BomberMan.INITIAL_POSITION);
//		modelBm.setPosition(model.BomberMan.INITIAL_POSITION);
		System.out.println("Resetting bm");
		ObservableList<Node> itemsPaneChildren = itemsPane.getChildren();
		itemsPaneChildren.removeAll(itemsPaneChildren);
		itemsPaneChildren.add(viewBm);
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
				itemsPane.getChildren().add(viewCharacter);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		modelBm.becomeInvincible();
		return enemies;
	}
	
	private static List<Object[]> readCharactersFile(String level) {
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
