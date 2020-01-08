package application;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Things {
  Player player;
  Map map;

  public Things() {
    map = new Map();
    player = new Player(map);
  }

  public Map getMap() {
    return map;
  }

  public Player getPlayer() {
    return player;
  }

  public class Player {
    private double[] position;
    private double[] direction;
    private double[] screen;
    private double[] speed;
    private Map map;

    public Player(Map map) {
      position = new double[] {12, 12};
      direction = new double[] {1, 0};
      screen = new double[] {0, .67};
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

    public double[] getScreen() {
      return screen;
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

    public void move(double dir, double nabla) {
      double x = position[0] + direction[0] * nabla * dir;
      double y = position[1] + direction[1] * nabla * dir;
      position[0] = (checkCollide(x, position[1])) ? position[0] : x;
      position[1] = (checkCollide(position[0], y)) ? position[1] : y;
    }

    private boolean checkCollide(double x, double y) {
      return map.getMap()[(int) y][(int) x] != 0;
    }

    public void rotate(double phi) {
      rotateDirection(phi * Math.PI / 180);
      rotateScreen(phi * Math.PI / 180);
    }

    private void rotateDirection(double phi) {
      double x = direction[0] * Math.cos(phi) - direction[1] * Math.sin(phi);
      double y = direction[0] * Math.sin(phi) + direction[1] * Math.cos(phi);
      direction[0] = x;
      direction[1] = y;
    }

    private void rotateScreen(double phi) {
      double x = screen[0] * Math.cos(phi) - screen[1] * Math.sin(phi);
      double y = screen[0] * Math.sin(phi) + screen[1] * Math.cos(phi);
      screen[0] = x;
      screen[1] = y;
    }

    public void handleMovement(KeyCode key, double elapsed) {
      double ratio = elapsed / 10;
      switch (key) {
        case UP:
          move(1, .02 * ratio);
          break;
        case DOWN:
          move(-1, .02 * ratio);
          break;
        case LEFT:
          rotate(-1 * ratio);
          break;
        case RIGHT:
          rotate(1 * ratio);
          break;
        default:
          break;
      }
    }
  }
  public class Map {
    private static final int WIDTH = 25;



    private static final int HEIGHT = 25;
    private final int[][] map1 =
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
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 6, 0, 1},
            {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 0, 6, 0, 1},
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 1},
            {1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 7, 0, 0, 7, 0, 0, 0, 6, 6, 6, 6, 6, 6, 0, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
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
          return Color.GREY;
        case 2:
          return Color.BLUE;
        case 3:
          return Color.RED;
        case 4:
          return Color.GREEN;
        case 5:
          return Color.MAGENTA;
        case 6:
          return Color.AZURE;
        case 7:
          return Color.DARKGOLDENROD;
        default:
          return Color.BLACK;
      }
    }

    public int[][] getTexture(int i, int j) {
      return null;
    }

    public boolean isCollision(int i, int j) {
      return map[i][j] == 0;
    }
  }
}
