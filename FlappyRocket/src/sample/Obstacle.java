package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.Random;

public class Obstacle extends Sprite implements Commons {

    private final static int MINIMAL_HEIGHT = 100;
    private static final int AUTO_HEIGHT = 180;

    public Obstacle(int canvWidth, int canvHeight) {
        this.x = canvWidth;
        this.width = OBSTACLE_WIDTH;
        Random rnd = new Random();

        /* Assigns random height of the obstacle. If it happens to be smaller
        * than certain threshold - assign fixed value. */
        this.height = (int) (rnd.nextDouble() * canvHeight / 2) + 50;
        if (this.height < MINIMAL_HEIGHT) {
            this.height = AUTO_HEIGHT;
        }

        /* Randomly picks what direction obstacle will face.
        * If 1 then the obstacle points upwards, downwards otherwise. */
        int dir = rnd.nextInt(2);

        if (dir == 1) {
            this.y = canvHeight - this.height;
        } else {
            this.y = 0;
        }

        /* Set proportional to true, so it "destroys" the texture
        * as a result all obstacles are in different 'smooth' color, based on
        * it's height. */
        Image texture = new Image("file:/Users/mateuszmeller/Desktop/programowanie/FlappyRocket/src/sample/spaceship 2/brick.png");
        this.image = new ImagePattern(texture, this.x, this.y, this.width, this.height, true);
    }

    /**
     * Checks if obstacle is on the screen.
     *
     * @return true if the obstacle is on the screen, false otherwise.
     */
    public boolean inBounds() {
        return this.x + this.width >= 0;
    }

    /**
     * Moves the obstacle in left direction.
     */
    public void move() {
        this.x -= WORLD_SPEED;
    }
}
