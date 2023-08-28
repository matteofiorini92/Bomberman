package controller;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	
	private Group root = view.BaseGroup.getInstance();
	private Scene scene = view.BaseScene.getInstance();
	
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
		
		

	}

}
