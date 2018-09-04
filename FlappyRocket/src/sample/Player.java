package sample;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


public class Player extends Sprite implements Commons {
    private double y;
    private double v;
    private int canvHeight;
    private boolean alive;

    /* TODO add theta and rotation of a player (based on free fall) */

    public Player(int canvasHeight) {
        this.x = PLAYER_START_X;
        this.y = canvasHeight / 2;
        this.v = 0;
        this.canvHeight = canvasHeight;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.alive = true;

        Image texture = new Image("file:/Users/mateuszmeller/Desktop/programowanie/FlappyRocket/src/sample/spaceship 2/spaceship2.png");
        this.image = new ImagePattern(texture);
    }


    /**
     * Imitates free fall
     */
    public void move() {
        /* If the the speed of the player is smaller
        * than max speed - accelerate, and displace. */
        if (this.v < MAXSPEED) {
            this.v += ACC;
        }
        this.y += this.v;
    }


    /**
     * Checks if player collides with obstacle or goes out of bounds.
     *
     * @param obstacle obstacle with which player can collide.
     * @return true if there is collision, false otherwise.
     */
    public boolean isCollision(Obstacle obstacle) {
        return this.y + this.height > this.canvHeight || this.y < 0 ||
                !(obstacle.getX() > this.x + this.width ||
                        obstacle.getX() + obstacle.getWidth() < this.x ||
                        obstacle.getY() > this.y + this.height ||
                        obstacle.getY() + obstacle.getHeight() < this.y);
    }


    /**
     * Kills the player and sets it parameter alive to false.
     */
    public void kill() {
        this.alive = false;
    }


    /**
     * Checks if player is alive.
     *
     * @return true if player is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }


    /**
     * Changes velocity of the player, so it goes up.
     */
    public void jump() {
        this.v = DRAG;
    }


    /**
     * Gets y position the player.
     *
     * @return y position of the player.
     */
    public double getY() {
        return y;
    }

}
