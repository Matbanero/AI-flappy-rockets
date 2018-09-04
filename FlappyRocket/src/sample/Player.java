package sample;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Random;


public class Player extends Sprite implements Commons {
    private double y;
    private double v;
    private int canvHeight;
    private boolean alive;
    private double probability;
    /* Each player should have score (rather than one score for the world)
     * and it should be incremented by passing the obstacles */
    private int score;

    /* Params of the player which helps in making the decisions
     * should be final, but need to assign them out by the method
     * (could do it in constructor but it will be cleaner in the method)
     * Lets treat them as a CONSTANTS */
    private double MIN_X;
    private int MIN_Y;
    private int MIN_TO_ROOF;
    private int MIN_TO_GROUND;

    /* TODO add theta and rotation of a player (based on free fall) */

    public Player(int canvasHeight) {
        this.x = PLAYER_START_X;
        this.y = canvasHeight / 2;
        this.v = 0;
        this.canvHeight = canvasHeight;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.alive = true;
        this.probability = 0.01; // TODO assing random and the use genetic algorithm

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


    /* @param obstacles - arrayList of all the obstacles
     * Actually I can model this by assigning first four obstacles in the arrayList
     * as it is sorted naturally by the proximity to the player. */
    public ArrayList<Obstacle> detectObstacles(ArrayList<Obstacle> obstacles) {
        ArrayList<Obstacle> closestObstacles = new ArrayList<>();
        for (int i = 0; i < closestObstacles.size(); i++) {
            if (!closestObstacles.contains(obstacles.get(i))) {
                closestObstacles.add(obstacles.get(i));
            } else {
                closestObstacles.remove(obstacles.get(i));
            }
        }
        return closestObstacles;
    }

    /* Based on the agent parameters (probability to act),
     * distance to and height of the obstacle - act, that is call jump() */
    public void act(ArrayList<Obstacle> closestObstacles) {
        Random rnd = new Random();
        double effect = rnd.nextDouble();

        if (effect <= this.probability) {
            jump();
        }
    }

    public boolean isCloseToObstacle(Obstacle obstacle) {
        return obstacle.getX() - this.x < MIN_X;
    }

    public void setParams() {
        Random rnd = new Random();
        this.MIN_X = rnd.nextDouble() * 100;
        this.MIN_Y = (int)rnd.nextDouble() * 150;
        this.MIN_TO_GROUND = (int)rnd.nextDouble() * 100 + 500;
        this.MIN_TO_ROOF = (int)rnd.nextDouble() * 50;
    }

    /* TODO mix 'genes' from the parents */
    public void setParams(Player parent) {

    }

}
