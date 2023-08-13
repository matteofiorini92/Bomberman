//package view;
//
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.StackPane;
//
//public class CharactersBoard {
//	
//	private StackPane stackPane;
//	
//	public CharactersBoard(String levelNumber)
//	{	
//		stackPane = new StackPane();
//		fillCharactersBoard(levelNumber);
//	}
//	
//	public StackPane getStackPane() { return stackPane; }
//	
//	private void fillCharactersBoard(String levelNumber) {
//		BomberMan bm = new BomberMan();
//		int[] coordinates = bm.getPosition().getCoordinates();
//		System.out.println(coordinates[0] + " " + coordinates[1]);
//    	bm.setLayoutX(coordinates[1] * Item.ITEM_WIDTH);
//    	bm.setLayoutY(coordinates[0] * Item.ITEM_HEIGHT);
//		stackPane.getChildren().add(bm);
//	}
//
//
//}
