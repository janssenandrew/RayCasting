package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Textures {
  private class Texture {
    private BufferedImage tex;
    private int[] pixels;
    private String path;
    private int width;
    private int height;

    public Texture(String path) {
      this.path = path;
      try {
        tex = ImageIO.read(new File(path));
        width = tex.getWidth();
        height = tex.getHeight();
        pixels = tex.getRGB(0, 0, width, height, pixels, 0, width);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private ArrayList<Texture> textures;
  private final String[] paths = {"brick-wall.jpg"};

  public Textures() {
    createTextures();
  }

  private void createTextures() {
    for (String str : paths) {
      textures.add(new Texture(str));
    }
  }
}
