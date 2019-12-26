package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

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
    screenWidth = 10 *  width;
    screenHeight = 10 * height;
  }

  public Scene buildScene() {
    GridPane grid = new GridPane();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        
      }
    }
    return null;
  }
}
