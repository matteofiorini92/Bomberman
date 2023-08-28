package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import view.BaseGroup;

public class LoadNewGame {
	
	public static final int INITIAL_LEVEL = 1;
	
	public LoadNewGame() {
		BaseGroup baseGroup = view.BaseGroup.getInstance();
		baseGroup.getChildren().removeAll(baseGroup.getChildren());
		Properties levelsProperties = new Properties();

		try (FileInputStream input = new FileInputStream("resources/levels.properties")) {
			levelsProperties.load(input);
			String currLevel = (String)levelsProperties.get(Integer.toString(INITIAL_LEVEL));
			
			new LoadLevel(currLevel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
