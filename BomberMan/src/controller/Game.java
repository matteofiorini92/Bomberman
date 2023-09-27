package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import view.BaseGroup;

/**
 * new game controller class to load and manage a game
 * @author Matteo
 *
 */
public abstract class Game {
	
	public static final int INITIAL_LEVEL = 2;
	public static final int FINAL_LEVEL = 3; // to be changed when a new level is implemented
	
	public static void load() {
		clearBoard();
		new Level(levelConverter(INITIAL_LEVEL), true);
	}
	
	public static void load(String level, int score) {
		clearBoard();
		new Level(level, score);
	}
	
	private static void clearBoard() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		ObservableList<Node> baseGroupChildren = baseGroup.getChildren();
		baseGroupChildren.removeAll(baseGroupChildren);
	}
	
	/**
	 * convert level from int to string (e.g. from 3 to "1-3")
	 * @param level e.g. 3
	 * @return e.g. "1-3"
	 */
	public static String levelConverter(int level) {
		Properties levelsProperties = new Properties();
		try (FileInputStream input = new FileInputStream("resources/levels.properties")) {
			levelsProperties.load(input);
			String currLevel = (String)levelsProperties.get(Integer.toString(level));
			
			return currLevel;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * convert level from string to int (e.g. from "1-3" to 3)
	 * @param level e.g. "1-3"
	 * @return e.g. 3
	 */
	public static int levelConverter(String level) {
		Properties levelsProperties = new Properties();
		try (FileInputStream input = new FileInputStream("resources/levels.properties")) {
			levelsProperties.load(input);
			Set<Entry<Object, Object>> entries = levelsProperties.entrySet();
			for (Entry<Object, Object> entry : entries) {
				if (((String)entry.getValue()).equals(level)) return Integer.parseInt((String)entry.getKey());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
