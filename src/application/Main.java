package application;

import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    DDA game = new DDA();
    primaryStage.show();
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        primaryStage.setScene(game.buildScene());
      }
    }.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
