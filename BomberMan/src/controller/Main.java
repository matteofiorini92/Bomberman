package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Board;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		
		Group root = new Board("1-2").getRoot();
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		
		Image icon = new Image("bm_icon.png");

		
		stage.getIcons().add(icon);
		stage.setTitle("Bomberman");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
}
