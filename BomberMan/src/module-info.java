module BomberMan {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	
	opens controller to javafx.graphics, javafx.fxml;
}
