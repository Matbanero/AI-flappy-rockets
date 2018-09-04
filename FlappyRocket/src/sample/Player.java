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
    private TreeNode decisionTree;

    /* TODO add theta and rotation of a player (based on free fall) */

    public Player(int canvasHeight) {
        this.x = PLAYER_START_X;
        this.y = canvasHeight / 2;
        this.v = 0;
        this.canvHeight = canvasHeight;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.alive = true;
        //this.probability = 0.01; // TODO assing random and the use genetic algorithm

        Image texture = new Image("file:/Users/mateuszmeller/Desktop/programowanie/FlappyRocket/src/sample/spaceship 2/spaceship2.png");
        this.image = new ImagePattern(texture);

        this.decisionTree = new TreeNode(5);
        generateDecisionTree(decisionTree);
        System.out.println(decisionTree.inOrderTraversal(decisionTree));
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


    /**
     * Actually I can model this by assigning first four obstacles in the arrayList
     * as it is sorted naturally by the proximity to the player.
     *
     * @param obstacles - arrayList of all the obstacles
     * @return arrayList of closest obstacles.
     */
    public ArrayList<Obstacle> detectObstacles(ArrayList<Obstacle> obstacles) {
        ArrayList<Obstacle> closestObstacles = new ArrayList<>();
        /*for (int i = 0; i < closestObstacles.size(); i++) {
            if (!closestObstacles.contains(obstacles.get(i))) {
                closestObstacles.add(obstacles.get(i));
            } else {
                closestObstacles.remove(obstacles.get(i));
            }
        }*/
        closestObstacles.add(obstacles.get(0));
        return closestObstacles;
    }


    /**
     * Based on the agent parameters ,
     * distance to and height of the obstacle - act, that is call jump()
     *
     * @param closestObstacles arrayList of obstacles that is closest and should act on.
     */
    public void act(ArrayList<Obstacle> closestObstacles) {
        ArrayList<Integer> decisionSet = this.decisionTree.inOrderTraversal(this.decisionTree);
        for (Integer decision : decisionSet) {
            switch (decision) {
                case 0:
                    jump();
                    break;
                case 1:
                    waitForNext();
                    break;
                case 2:
                    tooLow();
                    break;
                case 3:
                    tooHigh();
                    break;
                case 4:
                    obstacleAhead(closestObstacles.get(0));
                    break;
                case 5:
                    setParams();
                    break;
                default:
                    waitForNext();
                    break;
            }
        }
    }


    /**
     * Method which waits for the next action - opposite of jump().
     */
    public void waitForNext() {
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }*/
    }


    /**
     * Test if the obstacle is close and should act
     *
     * @param obstacle obstacle on which method tests
     * @return true if agent is too close and should act, false otherwise.
     */
    public boolean isCloseToObstacle(Obstacle obstacle) {
        return obstacle.getX() - this.x < MIN_X;
    }


    /**
     * Sets parameters on which decision will be made.
     * Randomly assigns them - use for the first generation.
     */
    public void setParams() {
        Random rnd = new Random();
        this.MIN_X = rnd.nextDouble() * 100;
        this.MIN_Y = (int) rnd.nextDouble() * 150;
        this.MIN_TO_GROUND = (int) rnd.nextDouble() * 100 + 500;
        this.MIN_TO_ROOF = (int) rnd.nextDouble() * 50;
    }

    /* TODO mix 'genes' from the parents */
    /*public void setParams(Player parent) {

    }*/


    /**
     * Test whether obstacle is pointing from the top.
     *
     * @param obstacle to be tested.
     * @return true if obstacle points from above, false if from bottom.
     */
    public boolean obstacleAbove(Obstacle obstacle) {
        return obstacle.getY() == 0;
    }

    public boolean canPass(Obstacle obstacle) {
        return obstacle.getY() > this.y;
    }


    /**
     * If agent is too high (danger of upper out of bound) - wait.
     */
    public void tooHigh() {
        if (this.y < this.MIN_TO_ROOF) {
            waitForNext();
        }
    }


    /**
     * If agent is too close to the ground - jump, wait otherwise.
     */
    public void tooLow() {
        if (this.canvHeight - this.y < MIN_TO_GROUND) {
            jump();
        } else {
            waitForNext();
        }
    }


    /**
     * If obstacle is close enough and pointing from the bottom
     * jump 3 times.
     *
     * @param obstacle object which is closest and tested.
     */
    public void obstacleAhead(Obstacle obstacle) {
        if (!obstacleAbove(obstacle) && isCloseToObstacle(obstacle)) {
            while (!canPass(obstacle)) {
                jump();
            }
        }
    }


    public TreeNode generateDecisionTree(TreeNode root) {
        Random rnd = new Random();

        if (root.hasChildren()) {
            for (int i = 0; i < TreeNode.getMaxNumberOfNodes(); i++) {
                double tryDecision = rnd.nextDouble();
                if (tryDecision <= 0.4) {
                    int decisionType = rnd.nextInt(5);
                    root.addChild(new TreeNode(decisionType));
                }
            }

            for (TreeNode child : root.getChildren()) {
                generateDecisionTree(child);
            }
        }
        return root;
    }

}
