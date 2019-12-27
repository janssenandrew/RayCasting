package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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

  public void setup(Stage primaryStage) {
//    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
//      player.handleMovement(key.getCode());
//    });
//    
  }

  public Scene buildScene() {
    player.updatePosition();
    GridPane grid = new GridPane();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Rectangle rect = new Rectangle();
        rect.setWidth(20);
        rect.setHeight(20);
        rect.setFill(mapObject.getColor(j, i));
        grid.add(rect, j, i);
      }
    }
    StackPane stack = new StackPane();
    Rectangle playerSprite = buildPlayer();
    Group group = new Group();
    playerSprite.setX(player.getPosition()[0] * 20);
    playerSprite.setY(player.getPosition()[1] * 20);
    Line line = new Line(0, 0, 500, 500);
    Line look = new Line(player.getPosition()[0] * 20, player.getPosition()[1] * 20,
        (player.getPosition()[0] + player.getDirection()[0]) * 20,
        (player.getPosition()[1] + player.getDirection()[1]) * 20);
    line.setStroke(Color.TRANSPARENT);
    look.setStroke(Color.YELLOW);
    group.getChildren().addAll(playerSprite, line, look);
    stack.getChildren().addAll(grid, group);
    return new Scene(stack, screenWidth, screenHeight);
  }

  private Rectangle buildPlayer() {
    Rectangle play = new Rectangle();
    play.setWidth(5);
    play.setHeight(5);
    play.setFill(Color.YELLOW);
    return play;
  }
}
