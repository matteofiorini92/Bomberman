module BomberMan {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens controller to javafx.graphics, javafx.fxml;
}
