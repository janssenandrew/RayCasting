package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Textures {
  protected class Texture {
    private BufferedImage tex;
    private int[] pixels;
    private int width;
    private int height;

    public Texture(String path) {
      try {
        tex = ImageIO.read(new File(path));
        width = tex.getWidth();
        height = tex.getHeight();
        pixels = tex.getRGB(0, 0, width, height, pixels, 0, width);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public int[] getPixels() {
      return pixels;
    }

    public BufferedImage getTexture() {
      return tex;
    }

    public int getWidth() {
      return width;
    }

    public int getHeight() {
      return height;
    }
  }

  private ArrayList<Texture> textures;
  private ArrayList<Texture> floorCeiling;
  private final String[] paths = {"bricks.png", "chestsides.png", "slate.png", "walkstone.png",
      "bookshelf.png", "redbricks.png", "stone.png"};
  private final String[] paths2 = {"stonewall.jpg", "wood.jpg", "squarebrick.jpg", "cobble.jpg",
      "box.jpg", "redbricks.jpg", "darkwood.jpg"};

  public Textures(boolean highRes) {
    textures = new ArrayList<Texture>();
    floorCeiling = new ArrayList<Texture>();
    if (highRes)
      createTextures(paths2, "highres/");
    else
      createTextures(paths, "");
    createFloor();
  }

  private void createTextures(String[] pathList, String prefix) {
    for (String str : pathList) {
      textures.add(new Texture("assets/textures/" + prefix + str));
    }
  }
  
  private void createFloor() {
    floorCeiling.add(new Texture("assets/textures/floor.png"));
    //floorCeiling.add(new Texture("assets/textures/ceiling.png"));
  }

  public Texture getTexture(int index) {
    return textures.get(index);
  }

  public Texture getFloor() {
    return floorCeiling.get(0);
  }

  public Texture getCeiling() {
    return floorCeiling.get(1);
  }
}
