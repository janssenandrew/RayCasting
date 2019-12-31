package application;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Things {
  static public class Player {
    private double[] position;
    private double[] direction;
    private double[] plane;
    private double angle;
    private double[] speed;
    private int[][] map;

    public Player(int[][] map) {
      position = new double[] {12, 12};
      direction = new double[] {1, 0};
      plane = new double[] {0, .66};
      angle = 0;
      speed = new double[] {0, 0};
      this.map = map;
    }

    public double[] getPosition() {
      return position;
    }

    public void setPosition(double[] position) {
      this.position = position;
    }

    public double[] getDirection() {
      return direction;
    }
    
    public double[] getPlane() {
      return plane;
    }

    public void setDirection(double[] direction) {
      this.direction = direction;
    }

    public double[] getSpeed() {
      return speed;
    }

    public void setSpeed(double[] speed) {
      this.speed = speed;
    }

    public void incSpeed(double[] accel) {}

//    public void updatePosition() {
//      position[0] += speed[0];
//      position[1] += speed[1];
//    }

//    public void increasePos(double[] del) {
//      position[0] += del[0];
//      position[1] += del[1];
//    }

    public void move(double dir, double nabla) {
      double x = position[0] + direction[0] * nabla * dir;
      double y = position[1] + direction[1] * nabla * dir;
      position[0] = (checkCollide(x, position[1])) ? position[0] : x;
      position[1] = (checkCollide(position[0], y)) ? position[1] : y;
      //System.out.println(position[0] + " " + position[1]);
    }

    private boolean checkCollide(double x, double y) {
      return map[(int) y][(int) x] != 0;
    }

    public void rotate(double phi) {
      rotationMatrix(phi * Math.PI / 180);
      angle += phi;
      if (angle == 360)
        angle = 0;
      if (angle == -1)
        angle = 359;
      //System.out.println(angle);
    }

    private void rotationMatrix(double phi) {
      double x = direction[0] * Math.cos(phi) - direction[1] * Math.sin(phi);
      double y = direction[0] * Math.sin(phi) + direction[1] * Math.cos(phi);
      direction[0] = x;
      direction[1] = y;
    }

    public void handleMovement(KeyCode key) {
      switch (key) {
        case UP:
          move(1, .02);
          break;
        case DOWN:
          move(-1, .02);
          break;
        case LEFT:
          rotate(-1);
          break;
        case RIGHT:
          rotate(1);
          break;
        default:
          break;
      }
    }
  }
  static public class Map {
    private static final int WIDTH = 25;



    private static final int HEIGHT = 25;
    private static final int[][] map1 =
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
    private int[][] map;

    public Map() {
      map = map1;
    }

    public int[][] getMap() {
      return map;
    }

    public int getWidth() {
      return WIDTH;
    }

    public int getHeight() {
      return HEIGHT;
    }

    public Color getColor(int i, int j) {
      switch (map[i][j]) {
        case 1:
          return Color.WHITE;
        case 2:
          return Color.BLUE;
        case 3:
          return Color.RED;
        case 4:
          return Color.GREEN;
        case 5:
          return Color.MAGENTA;
        default:
          return Color.BLACK;
      }
    }

    public boolean isCollision(int i, int j) {
      return map[i][j] == 0;
    }
  }
}
