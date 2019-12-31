package application;

import java.util.ArrayList;
import application.Things.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application {
  private static ArrayList<KeyCode> activeKeys = new ArrayList<KeyCode>();
  private static Player player;

  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    boolean yes = true;
    if (yes) {
      DDA RayCaster = new DDA();
      RayCaster.setup(primaryStage);
      primaryStage.show();
      new AnimationTimer() {
        @Override
        public void handle(long now) {
          for (KeyCode key : activeKeys)
            player.handleMovement(key);
          primaryStage.setScene(RayCaster.buildScene());
        }
      }.start();
    } else {
      TwoDimensional TD = new TwoDimensional();
      TD.setup(primaryStage);
      primaryStage.show();
      new AnimationTimer() {
        @Override
        public void handle(long now) {
          for (KeyCode key : activeKeys)
            player.handleMovement(key);
          primaryStage.setScene(TD.buildScene());
        }
      }.start();
    }
  }

  protected static void wireInput(Stage primaryStage, Player player) {
    primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      KeyCode code = key.getCode();
      if (!activeKeys.contains(code))
        activeKeys.add(code);
    });
    primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
      activeKeys.remove(key.getCode());
    });
    Main.player = player;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
