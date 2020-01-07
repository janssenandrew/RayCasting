package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RayCaster implements Renderer {
  private static final String path = "file:assets/brick-wall.jpg";

  private static final int SCREEN_WIDTH = 1024;
  private static final int SCREEN_HEIGHT = 576;
  private double[] position;
  private double[] direction;
  private double[] screen;
  private final int[][] map;
  private Things things;

  private int texWidth = 64;
  private int texHeight = 64;

  private Player player;
  Map mapObject;

  public RayCaster(Things things) {
    this.things = things;
    mapObject = this.things.getMap();
    map = mapObject.getMap();
    player = this.things.getPlayer();
  }

  public Scene buildScene() {
    position = player.getPosition();
    direction = player.getDirection();
    screen = player.getScreen();
    Group group = new Group();
    StackPane stack = new StackPane();



    // Image img = new Image(path);
    // WritableImage wi = new WritableImage(SCREEN_WIDTH, SCREEN_HEIGHT);
    // ImageView iv = new ImageView();
    // iv.setImage(img);
    // stack.getChildren().add(iv);

    double cameraPosition;
    double xRayDirection;
    double yRayDirection;
    double xSideDist;
    double ySideDist;
    for (double column = 0; column < SCREEN_WIDTH; column++) {
      cameraPosition = ((2 * column) / (double) SCREEN_WIDTH) - 1;
      xRayDirection = direction[0] + screen[0] * cameraPosition;
      yRayDirection = direction[1] + screen[1] * cameraPosition;

      double xDelta = Math.abs(1 / xRayDirection);
      double yDelta = Math.abs(1 / yRayDirection);
      double distance;


      // step direction
      int xStep;
      int yStep;
      int side = 0;

      int xSquare = (int) position[0];
      int ySquare = (int) position[1];
      if (xRayDirection < 0) {
        xStep = -1;
        xSideDist = (position[0] - xSquare) * xDelta;
      } else {
        xStep = 1;
        xSideDist = (xSquare + 1.0 - position[0]) * xDelta;
      }
      if (yRayDirection < 0) {
        yStep = -1;
        ySideDist = (position[1] - ySquare) * yDelta;
      } else {
        yStep = 1;
        ySideDist = (ySquare + 1.0 - position[1]) * yDelta;
      }
      boolean collision = false;
      while (!collision) {
        if (xSideDist < ySideDist) {
          xSideDist += xDelta;
          xSquare += xStep;
          side = 0;
        } else {
          ySideDist += yDelta;
          ySquare += yStep;
          side = 1;
        }
        // collision detection
        if (map[ySquare][xSquare] != 0)
          collision = true;
      }
      distance = (side == 0) ? ((double) xSquare - position[0] + ((1 - xStep) / 2)) / xRayDirection
          : (double) (ySquare - position[1] + ((1 - yStep) / 2)) / yRayDirection;
      int wallHeight = (int) (SCREEN_HEIGHT / distance);

      Line line = drawLine(column, wallHeight);
      Color color = mapObject.getColor(ySquare, xSquare);

      int texture = map[ySquare][xSquare];
      double wall = (side == 0) ? position[1] + distance * yRayDirection
          : position[0] + distance * xRayDirection;
      wall -= Math.floor((wall));

      if (side != 0)
        color = color.darker();
      line.setStroke(color);
      group.getChildren().add(line);
    }
    stack.getChildren().add(group);
    return new Scene(stack, SCREEN_WIDTH, SCREEN_HEIGHT);
  }

  private Line drawLine(double column, int wallHeight) {
    int lineStart = -wallHeight / 2 + SCREEN_HEIGHT / 2;
    if (lineStart < 0)
      lineStart = 0;
    int lineEnd = wallHeight / 2 + SCREEN_HEIGHT / 2;
    if (lineEnd >= SCREEN_HEIGHT)
      lineEnd = SCREEN_HEIGHT - 1;
    return new Line(column, lineStart, column, lineEnd);
  }

  private void drawTexture(double column, int wallHeight) {

  }
}
