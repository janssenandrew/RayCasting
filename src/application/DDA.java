package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class DDA {
  private static final int SCREEN_WIDTH = 1280;
  private static final int SCREEN_HEIGHT = 720;
  private double[] position;
  private double[] direction;
  private double[] screen;
  private final int[][] map;

  private Player player;
  Map mapObject;

  public DDA() {
    mapObject = new Map();
    map = mapObject.getMap();
    player = new Player(map);

  }

  public void setup(Stage primaryStage) {
    Main.wireInput(primaryStage, player);
  }

  public Scene buildScene() {
    position = player.getPosition();
    direction = player.getDirection();
    screen = player.getScreen();
    Group group = new Group();
    double xCamera;
    double xRayDir;
    double yRayDir;
    double sideDistX;
    double sideDistY;
    for (double column = 0; column < SCREEN_WIDTH; column++) {
      xCamera = ((2 * column) / (double) SCREEN_WIDTH) - 1;
      xRayDir = direction[0] + screen[0] * xCamera;
      yRayDir = direction[1] + screen[1] * xCamera;

      double deltaDistX = Math.abs(1 / xRayDir);
      double deltaDistY = Math.abs(1 / yRayDir);
      double perpWallDist;


      // step direction
      int stepX;
      int stepY;
      int side = 0;

      int xSquare = (int) position[0];
      int ySquare = (int) position[1];
      if (xRayDir < 0) {
        stepX = -1;
        sideDistX = (position[0] - xSquare) * deltaDistX;
      } else {
        stepX = 1;
        sideDistX = (xSquare + 1.0 - position[0]) * deltaDistX;
      }
      if (yRayDir < 0) {
        stepY = -1;
        sideDistY = (position[1] - ySquare) * deltaDistY;
      } else {
        stepY = 1;
        sideDistY = (ySquare + 1.0 - position[1]) * deltaDistY;
      }
      // System.out.println(stepX + " " + stepY);
      boolean collision = false;
      while (!collision) {
        if (sideDistX < sideDistY) {
          sideDistX += deltaDistX;
          xSquare += stepX;
          side = 0;
        } else {
          sideDistY += deltaDistY;
          ySquare += stepY;
          side = 1;
        }
        // collision detection
        if (map[ySquare][xSquare] != 0)
          collision = true;
      }
      perpWallDist = (side == 0) ? ((double) xSquare - position[0] + ((1 - stepX) / 2)) / xRayDir
          : (double) (ySquare - position[1] + ((1 - stepY) / 2)) / yRayDir;
      // System.out.println(perpWallDist);
      // System.out.println(collision);
      int wallHeight = (int) (SCREEN_HEIGHT / perpWallDist);

      int drawStart = -wallHeight / 2 + SCREEN_HEIGHT / 2;
      if (drawStart < 0)
        drawStart = 0;
      int drawEnd = wallHeight / 2 + SCREEN_HEIGHT / 2;
      if (drawEnd >= SCREEN_HEIGHT)
        drawEnd = SCREEN_HEIGHT - 1;

      Line line = new Line(column, drawStart, column, drawEnd);
      // System.out.println(drawStart);
      // System.out.println(drawEnd);
      Color color = (side == 0) ? Color.RED : Color.ORANGERED;
      line.setStroke(color);
      group.getChildren().add(line);
    }
    return new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
  }
}
