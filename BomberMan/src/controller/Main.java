package controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import model.Position;
import view.Board;
import view.BomberMan;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	private view.BomberMan viewBm;
	private model.BomberMan modelBm;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		
		
		Group root = new Group();
		GridPane boardGridPane = new Board("1-1").getGridPane();
		// boardGridPane.setGridLinesVisible(true);
		
		
		viewBm = new view.BomberMan();
		modelBm = new model.BomberMan();
		
		modelBm.addObserver(viewBm);
		
//		GridPane.setRowSpan(bm, 2);
//		int[] coordinates = bm.getPosition().getCoordinates();
//		boardGridPane.add(bm, coordinates[1], coordinates[0]-1);
		
		
		
		root.getChildren().addAll(boardGridPane, viewBm);
		Scene scene = new Scene(root, 1088, 832, Color.BLACK);
		
		scene.setOnKeyPressed(this::handleKeyPressed);
		
		Image icon = new Image("bm_icon.png");

		
		stage.getIcons().add(icon);
		stage.setTitle("Bomberman");
		stage.setResizable(false);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN)) {
        	int[] currPosition = modelBm.getPosition().getCoordinates();
        	Position newPosition = new Position(currPosition[0] + 1, currPosition[1]);
        	
        	modelBm.setPosition(newPosition);
        }
    }
}
