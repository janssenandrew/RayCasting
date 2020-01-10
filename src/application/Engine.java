package application;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;

public class Engine {
  public class FramesPerSecond {
    private final long[] times;
    private int timesIndex;
    private boolean fullArray;

    private long previous;

    public FramesPerSecond() {
      times = new long[100];
      timesIndex = 0;
      fullArray = false;
      previous = 100000000000000L;
    }

    public double[] nextFrame(long now) {
      long prev = times[timesIndex];
      times[timesIndex] = now;
      long elapsed = now - prev;

      long delta = now - previous;
      previous = now;

      double fps = 0;
      timesIndex = (timesIndex + 1) % times.length;
      if (timesIndex == 0)
        fullArray = true;
      if (fullArray) {
        fps = (double) (1000000000 / (elapsed / times.length));
      }
      return new double[] {delta / 1000000, fps};
    }

    public double calculateElapsed(long now) {
      double elapsed = now - previous;
      previous = now;
      return 1000000000 / elapsed;
    }
  }

  private Renderer renderer;
  private Things things;
  Stage primaryStage;
  private static ArrayList<KeyCode> activeKeys;
  private FramesPerSecond fps;
  private double[] perf;

  public Engine(int dimension, Things things, Stage primaryStage) {
    switch (dimension) {
      case 2:
        renderer = new TwoDimensional(things);
        break;
      case 3:
        renderer = new RayCaster(things);
        break;
      default:
        break;
    }
    this.things = things;
    this.primaryStage = primaryStage;
    activeKeys = new ArrayList<KeyCode>();
    fps = new FramesPerSecond();
  }

  public Engine(int dimension, Stage primaryStage) {
    this(dimension, new Things(0), primaryStage);
  }

  protected void wireInput() {
    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      KeyCode code = key.getCode();
      if (!activeKeys.contains(code))
        activeKeys.add(code);
    });
    primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
      activeKeys.remove(key.getCode());
    });
  }

  public void start() {
    wireInput();
    primaryStage.show();
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        perf = fps.nextFrame(now);
        primaryStage.setTitle(perf[1] + " fps");
        // things.getPlayer().handleLook(MouseInfo.getPointerInfo().getLocation());
        for (KeyCode key : activeKeys)
          things.getPlayer().handleMovement(key, perf[0], primaryStage);
        primaryStage.setScene(renderer.buildScene());
      }
    }.start();
  }

  public void moveCursor(int screenX, int screenY) {
    Platform.runLater(() -> {
      try {
        Robot robot = new Robot();
        robot.mouseMove(screenX, screenY);
      } catch (Exception e) {
        // e.printStackTrace();
      }
    });
  }
}
