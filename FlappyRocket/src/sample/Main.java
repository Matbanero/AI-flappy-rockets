package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class Main extends Application implements Commons {


    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Create new world, group, stage, canvas etc. */
        final World[] world = {new World(Commons.GAME_WIDTH, Commons.GAME_HEIGHT)};
        Group root = new Group();

        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().addAll(canvas, world[0].getText());

        /*TreeNode rootN = new TreeNode(2);
        rootN.addChild(new TreeNode(3));
        rootN.addChild(new TreeNode(5));
        rootN.addChild(new TreeNode(7));
        rootN.addChild(new TreeNode(9));
        rootN.getChild(3).addChild(new TreeNode(12));
        rootN.getChild(3).addChild(new TreeNode(15));

        System.out.println(rootN.inOrderTraversal(rootN));
*/


        /* Handling user input */
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {

                        /* If player presses space - player jumps. */
                        if (event.getCode() == KeyCode.SPACE) {
                            world[0].getPlayer().jump();
                        }

                        /* If player is dead and presses R - restart game. */
                        if (!world[0].getPlayer().isAlive() && event.getCode() == KeyCode.R) {
                            root.getChildren().remove(world[0].getText());
                            world[0] = new World(Commons.GAME_WIDTH, Commons.GAME_HEIGHT);
                            root.getChildren().add(world[0].getText());
                        }
                    }
                }
        );

        /* Create grafic context for canvas */
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /* Main game loop */
        final long startTimer = System.nanoTime();
        new AnimationTimer() {
            public void handle(long currentTimer) {
                double t = currentTimer - startTimer / 1000000000.0;

                /* As long player is alive, update and draw the world. */
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
