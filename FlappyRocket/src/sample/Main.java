package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


    /*
    *  TODO: Reduce sizes of the trees - add constraint
    *  TODO: Improve mixing of a population
    *  TODO: Add highest score
    *  */


public class Main extends Application implements Commons {


    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Create new world, group, stage, canvas etc. */
        final World[] world = {new World(Commons.GAME_WIDTH, Commons.GAME_HEIGHT)};
        Group root = new Group();

        primaryStage.setTitle("AI Flappy Rockets");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().addAll(canvas, world[0].getText());


        /* Create grafic context for canvas */
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /* Main game loop */
        final long startTimer = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentTimer) {
                double t = currentTimer - startTimer / 1000000000.0;

                /* As long player is alive, update and draw the world. */
                if (!world[0].allDead()) {
                    world[0].update();
                    world[0].drawWorld(gc);
                } else if (world[0].allDead() && World.getGeneration() < MAX_GENERATION) {
                    root.getChildren().remove(world[0].getText());
                    world[0] = new World(Commons.GAME_WIDTH, Commons.GAME_HEIGHT);
                    root.getChildren().add(world[0].getText());
                }

            }
        }.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
