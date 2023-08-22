module BomberMan {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens controller to javafx.graphics, javafx.fxml;
}
