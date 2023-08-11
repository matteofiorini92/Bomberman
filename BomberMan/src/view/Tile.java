package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {

    public static final int TILE_HEIGHT = 64;
    public static final int TILE_WIDTH = 64;

    private ImageView imageView;

    public Tile(Image image) {
        imageView = new ImageView(image);
        imageView.setFitWidth(TILE_WIDTH);
        imageView.setFitHeight(TILE_HEIGHT);

        Rectangle rectangle = new Rectangle(TILE_WIDTH, TILE_HEIGHT, Color.TRANSPARENT);
        getChildren().addAll(rectangle, imageView);
    }
}
