package application;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Engine {
  private Renderer renderer;
  private Things things;
  Stage primaryStage;
  private static ArrayList<KeyCode> activeKeys;

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
        for (KeyCode key : activeKeys)
          things.getPlayer().handleMovement(key);
        primaryStage.setScene(renderer.buildScene());
      }
    }.start();
  }
}
