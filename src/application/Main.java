package application;

import java.util.Arrays;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws InterruptedException {
    primaryStage.setScene(startScreen(primaryStage));
    primaryStage.show();
    
    Textures textures = new Textures();
    System.out.println(textures.getTexture(0).getPixels().length);
    System.out.println(Arrays.toString(textures.getTexture(0).getPixels()));
  }

  private void play(Stage primaryStage, int dimension) {
    Engine engine = new Engine(dimension, primaryStage);
    engine.start();
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
      play(primaryStage, 3);
    });

    button2d.setOnAction(e -> {
      play(primaryStage, 2);
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

  public static void main(String[] args) {
    launch(args);
  }
}
