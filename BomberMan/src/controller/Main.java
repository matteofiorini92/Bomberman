package controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import view.Board;
import view.BomberMan;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		
		
		Group root = new Group();
		GridPane boardGridPane = new Board("1-1").getGridPane();
		// boardGridPane.setGridLinesVisible(true);
		
		
		BomberMan bm = new BomberMan();
		GridPane.setRowSpan(bm, 2);
		int[] coordinates = bm.getPosition().getCoordinates();
		boardGridPane.add(bm, coordinates[1], coordinates[0]-1);
		
		root.getChildren().add(boardGridPane);
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		
		Image icon = new Image("bm_icon.png");

		
		stage.getIcons().add(icon);
		stage.setTitle("Bomberman");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
}
