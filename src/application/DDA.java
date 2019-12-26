package application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DDA {
  private static final int WIDTH = 25;
  private static final int HEIGHT = 25;
  private static final int SCREEN_WIDTH = 1280;
  private static final int SCREEN_HEIGHT = 720;
  private double[] position;
  private double[] direction;
  private double[] plane;
  private final int[][] map =
      {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 2, 2, 0, 2, 2, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
          {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

  public DDA() {
    position = new double[] {10, 10};
    direction = new double[] {1, 0,};
    plane = new double[] {0, .66};
  }

  public Scene buildScene() {
    Group group = new Group();
    double xCamera;
    double xRayDir;
    double yRayDir;
    double sideDistX;
    double sideDistY;
    for (double column = 0; column < SCREEN_WIDTH; column++) {
      xCamera = ((2 * column) / SCREEN_WIDTH) - 1;
      xRayDir = direction[0] + plane[0] * xCamera;
      // System.out.println(direction[0] + " " + plane[0] + " " + xCamera);
      // System.out.println(xRayDir);
      yRayDir = direction[1] + plane[1] * xCamera;

      double deltaDistX = Math.abs(1 / xRayDir);
      double deltaDistY = Math.abs(1 / yRayDir);
      // System.out.println(deltaDistX);
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
        sideDistX = ((int) position[0] + 1.0 - xSquare) * deltaDistX;
      }
      if (yRayDir < 0) {
        stepY = -1;
        sideDistY = (position[1] - ySquare) * deltaDistY;
      } else {
        stepY = 1;
        sideDistY = ((int) position[1] + 1.0 - ySquare) * deltaDistY;
      }
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
        if (map[xSquare][ySquare] != 0)
          collision = true;
      }
      perpWallDist = (side == 0) ? (double) xSquare - position[0] + (1 - stepX / 2) / yRayDir
          : (double) ySquare - position[1] + (1 - stepY / 2) / yRayDir;
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
      System.out.println(drawStart);
      System.out.println(drawEnd);
      line.setStroke(Color.RED);
      group.getChildren().add(line);
    }
    position[0] += .01;
    position[1] += .01;
    return new Scene(group, SCREEN_WIDTH, SCREEN_HEIGHT);
  }
}
