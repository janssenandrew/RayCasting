package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TwoDimensional implements Renderer{
  private int screenWidth;
  private int screenHeight;
  int width;
  int height;
  private Player player;
  private Map mapObject;
  private Things things;

  public TwoDimensional() {
    things = new Things();
    mapObject = things.getMap();
    player = things.getPlayer();
    width = mapObject.getWidth();
    height = mapObject.getHeight();
    screenWidth = 20 * width;
    screenHeight = 20 * height;
  }

  public void setup(Stage primaryStage) {
    Main.wireInput(primaryStage, player);
  }

  public Scene buildScene() {
    GridPane grid = new GridPane();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Rectangle rect = new Rectangle();
        rect.setWidth(20);
        rect.setHeight(20);
        rect.setFill(mapObject.getColor(i, j));
        grid.add(rect, j, i);
      }
    }
    StackPane stack = new StackPane();
    Group group = new Group();
    Line line = new Line(0, 0, 500, 500);
    Line look = new Line(player.getPosition()[0] * 20, player.getPosition()[1] * 20,
        (player.getPosition()[0] + player.getDirection()[0]) * 20,
        (player.getPosition()[1] + player.getDirection()[1]) * 20);
    line.setStroke(Color.TRANSPARENT);
    look.setStroke(Color.YELLOW);
    group.getChildren().addAll(line, look);
    stack.getChildren().addAll(grid, group);
    Scene scene = new Scene(stack, screenWidth, screenHeight);
    return scene;
  }
}
