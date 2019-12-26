package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TwoDimensional {
  private int screenWidth;
  private int screenHeight;
  int width;
  int height;
  private Player player;
  Map mapObject;
  private int[][] map;

  public TwoDimensional() {
    player = new Player();
    mapObject = new Map();
    map = mapObject.getMap();
    width = mapObject.getWidth();
    height = mapObject.getHeight();
    screenWidth = 20 * width;
    screenHeight = 20 * height;
  }

  public Scene buildScene() {
    GridPane grid = new GridPane();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (map[j][i] > 0) {
          Rectangle rect = new Rectangle();
          rect.setWidth(20);
          rect.setHeight(20);
          rect.setFill(Color.RED);
          grid.add(rect, j, i);
        }
      }
    }
    return new Scene(grid, screenWidth, screenHeight);
  }
}
