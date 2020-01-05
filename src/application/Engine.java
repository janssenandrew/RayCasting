package application;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Engine {
  public class FramesPerSecond {
    private final long[] times;
    private int timesIndex;
    private boolean fullArray;

    public FramesPerSecond() {
      times = new long[100];
      timesIndex = 0;
      fullArray = false;
    }

    public double nextFrame(long now) {
      long prev = times[timesIndex];
      times[timesIndex] = now;
      timesIndex = (timesIndex + 1) % times.length;
      if (timesIndex == 0)
        fullArray = true;
      if (fullArray) {
        long elapsed = now - prev;
        return (double) (1000000000 / (elapsed / times.length));
      }
      return 0;
      // long oldFrameTime = frameTimes[frameTimeIndex] ;
      // frameTimes[frameTimeIndex] = now ;
      // frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
      // if (frameTimeIndex == 0) {
      // arrayFilled = true ;
      // }
      // if (arrayFilled) {
      // long elapsedNanos = now - oldFrameTime ;
      // long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
      // double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
      // label.setText(String.format("Current frame rate: %.3f", frameRate));
    }
  }

  private Renderer renderer;
  private Things things;
  Stage primaryStage;
  private static ArrayList<KeyCode> activeKeys;
  private FramesPerSecond fps;
  private double perf;

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
    this(dimension, new Things(), primaryStage);
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
        primaryStage.setTitle(perf + " fps");
        for (KeyCode key : activeKeys)
          things.getPlayer().handleMovement(key);
        primaryStage.setScene(renderer.buildScene());
      }
    }.start();
  }
}
