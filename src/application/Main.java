package application;

import java.util.ArrayList;
import application.Things.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
  private static ArrayList<KeyCode> activeKeys = new ArrayList<KeyCode>();
  private static Player player;

  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    primaryStage.setScene(startScreen(primaryStage));
    primaryStage.show();
  }

  private void play(Stage primaryStage, boolean ray) {
    if (ray) {
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

  private Scene startScreen(Stage primaryStage) {
    GridPane grid = new GridPane();
    Button button3d = new Button("3D");
    Button button2d = new Button("2D");
    Button options = new Button("options");

    button3d.setStyle(
        "-fx-text-fill: white; -fx-background-color: dodgerblue;-fx-background-radius: 10");
    button3d.setPrefSize(150, 150);
    button2d.setStyle(
        "-fx-text-fill: white; -fx-background-color: dodgerblue;-fx-background-radius: 10");
    button2d.setPrefSize(150, 150);

    button3d.setOnAction(e -> {
      play(primaryStage, true);
    });

    button2d.setOnAction(e -> {
      play(primaryStage, false);
    });

    grid.add(button3d, 0, 0);
    grid.add(button2d, 1, 0);
    grid.add(options, 0, 1, 2, 1);
    grid.setHgap(50);
    grid.setVgap(25);
    grid.setPadding(new Insets(50, 50, 50, 50));
    GridPane.setHalignment(button3d, HPos.CENTER);
    GridPane.setHalignment(button2d, HPos.CENTER);
    GridPane.setHalignment(options, HPos.CENTER);
    return new Scene(grid, 450, 300);
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
