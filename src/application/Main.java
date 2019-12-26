package application;

import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    DDA game = new DDA();
    boolean yes = false;
    if (yes) {
      primaryStage.show();
      new AnimationTimer() {
        @Override
        public void handle(long now) {
          primaryStage.setScene(game.buildScene());
        }
      }.start();
    } else {
      TwoDimensional TD = new TwoDimensional();
      TD.setup(primaryStage);
      primaryStage.show();
      new AnimationTimer() {
        @Override
        public void handle(long now) {
          primaryStage.setScene(TD.buildScene());
        }
      }.start();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
