package application;

import application.Things.Map;
import application.Things.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class TwoDimensional implements Renderer {
  private int screenWidth;
  private int screenHeight;
  int width;
  int height;
  private Player player;
  private Map mapObject;
  private int[][] map;
  private int scale;

  private Things things;
  // private Textures textures;
  private int[] pixels;

  public TwoDimensional(Things things) {
    this.things = things;
    mapObject = this.things.getMap();
    map = mapObject.getMap();
    player = this.things.getPlayer();
    width = mapObject.getWidth();
    height = mapObject.getHeight();
    scale = getScale();
    screenWidth = scale * width;
    screenHeight = scale * height;
    pixels = new int[screenWidth * screenHeight];
  }

  private int getScale() {
    int x = 1920 / width / 2;
    int y = 1080 / height / 2;
    return Math.min(x, y);
  }

  public Scene buildScene() {
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < map[row].length; column++) {
        buildSquare(row, column);
      }
    }
    Line line = buildPlayer(player.getPosition(), player.getDirection());
    WritableImage image = new WritableImage(screenWidth, screenHeight);
    PixelWriter pw = image.getPixelWriter();
    pw.setPixels(0, 0, screenWidth, screenHeight, PixelFormat.getIntArgbInstance(), pixels, 0,
        screenWidth);
    ImageView img = new ImageView();
    Group gr = new Group();
    gr.getChildren().addAll(img, line);
    img.setImage(image);
    return new Scene(gr, screenWidth, screenHeight);
  }

  private void buildSquare(int row, int column) {
    Color color = mapObject.getColor(row, column);
    int startX = column * scale;
    int startY = row * scale;
    int endX = (column + 1) * scale;
    int endY = (row + 1) * scale;
    for (int x = startX; x < endX; x++)
      for (int y = startY; y < endY; y++)
        pixels[y * screenWidth + x] =
            (255 & 0xff) << 24 | (((int) (255 * color.getRed())) & 0xff) << 16
                | (((int) (255 * color.getGreen())) & 0xff) << 8
                | (((int) (255 * color.getBlue())) & 0xff);
  }

  private Line buildPlayer(double[] position, double[] direction) {
    int startX = (int) (position[0] * scale);
    int startY = (int) (position[1] * scale);
    int endX = startX + (int) (direction[0] * (scale));
    int endY = startY + (int) (direction[1] * (scale));
    Line line = new Line(startX, startY, endX, endY);
    line.setStroke(Color.YELLOW);
    return line;
  }
}
