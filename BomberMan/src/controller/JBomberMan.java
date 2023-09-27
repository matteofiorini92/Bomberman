package controller;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * 
 * @author Matteo
 *	entry point of the application
 */
public class JBomberMan extends Application {
	
	/**
	 * initialise the group and scene used by javaFX
	 */
	private Group root = view.BaseGroup.getInstance();
	private Scene scene = view.BaseScene.getInstance();
	
	/**
	 * load different types of enemies and characters
	 */
	private static Map<String, Class<? extends model.Enemy>> modelEnemies = new HashMap<>();
	static {
		modelEnemies.put("he", model.Helix.class);
		modelEnemies.put("bug", model.Bug.class);
	}
	
	private static Map<String, Class<? extends view.Character>> viewCharacters = new HashMap<>();
	static {
		viewCharacters.put("he", view.Helix.class);
		viewCharacters.put("bug", view.Bug.class);
	}
	
	/**
	 * launch the javaFX application
	 * @param args empty array
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * start the application
	 */
	@Override
	public void start(Stage stage) throws Exception
	{
		
		Image icon = new Image("bm_icon.png");
		stage.getIcons().add(icon);
		stage.setTitle("BomberMan");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
		
		
		/**
		 * intantiate the welcome board
		 */
		view.boards.Welcome wBoard = new view.boards.Welcome();
		root.getChildren().add(wBoard);
		
		

	}

}
