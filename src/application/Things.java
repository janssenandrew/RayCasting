package application;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Things {
  Player player;
  Map map;

  public Things() {
    map = new Map();
    player = new Player(map, map.getStart());
  }

  public Things(int mapIndex) {
    map = new Map(mapIndex);
    player = new Player(map, map.getStart());
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

    public Player(Map map, int[] start) {
      position = new double[] {start[0], start[1]};
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

    public void handleMovement(KeyCode key, double elapsed, Stage primaryStage) {
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
        case ESCAPE:
          primaryStage.close();
          break;
        default:
          break;
      }
    }

    public void handleLook(Point p) {
      double deltaX = p.getX() - 960;
      rotate(deltaX / 10);
    }
  }
  public static class Map {
    private static final String[] mapPaths = {"map2.txt", "template.txt"};
    private static final int[][] startCoords = {{2, 2}, {20, 20}};
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
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 6, 0, 1},
            {1, 4, 0, 0, 0, 0, 5, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 0, 6, 0, 1},
            {1, 4, 0, 4, 0, 0, 0, 0, 4, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 1},
            {1, 4, 0, 4, 4, 4, 4, 4, 4, 0, 7, 0, 0, 7, 0, 0, 0, 6, 6, 6, 6, 6, 6, 0, 1},
            {1, 4, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 7, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    private int[][] currentMap;
    private int[] start;
    private int mapWidth;
    private int mapHeight;

    public Map() {
      currentMap = map1;
      start = new int[] {12, 12};
      int[] dim = calculateDimensions(currentMap);
      mapWidth = dim[0];
      mapHeight = dim[1];
    }

    public Map(int index) {
      currentMap = importMap(mapPaths[index]);
      start = startCoords[index];
      int[] dim = calculateDimensions(currentMap);
      mapWidth = dim[0];
      mapHeight = dim[1];
    }

    private int[][] importMap(String path) {
      ArrayList<int[]> map = new ArrayList<int[]>();
      int[] line;
      try {
        Scanner sc = new Scanner(new File("assets/maps/" + path));
        while (sc.hasNextLine()) {
          line = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
          map.add(line);
        }
        sc.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return null;
      } finally {
      }
      return map.toArray(new int[map.size()][]);
    }

    private int[] calculateDimensions(int[][] map) {
      int maxWidth = 0;
      for (int i = 0; i < map.length; i++)
        maxWidth = (map[i].length > maxWidth) ? map[i].length : maxWidth;
      return new int[] {maxWidth, map.length};
    }

    public int[][] getMap() {
      return currentMap;
    }

    public int getWidth() {
      return mapWidth;
    }

    public int getHeight() {
      return mapHeight;
    }

    public int[] getStart() {
      return start;
    }

    public Color getColor(int i, int j) {
      switch (currentMap[i][j]) {
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
      return currentMap[i][j] == 0;
    }
  }
}
