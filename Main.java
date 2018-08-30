package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Collection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final World[] world = {new World(600, 600)};
        Group root = new Group();

        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(600, 600);
        root.getChildren().addAll(canvas, world[0].getText());

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.SPACE) {
                            world[0].getPlayer().jump();
                        }
                        if (!world[0].getPlayer().isAlive() && event.getCode() == KeyCode.R) {
                            world[0] = new World(600, 600);
                        }
                    }
                }
        );


        GraphicsContext gc = canvas.getGraphicsContext2D();

        final long startTimer = System.nanoTime();


        new AnimationTimer() {
            public void handle(long currentTimer) {
                double t = currentTimer - startTimer / 1000000000.0;

                if (world[0].getPlayer().isAlive()) {
                    world[0].update();
                    world[0].drawWorld(gc);
                }

            }
        }.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
