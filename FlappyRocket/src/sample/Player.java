package sample;


import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;


public class Player extends Sprite implements Commons {
    private double y;
    private double v;
    private int canvHeight;
    private boolean alive;
    /* Each player has fitness on which it's performance will be judged */
    private int fitness;
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
    private Obstacle closestObstacle;

    /* TODO add theta and rotation of a player (based on free fall) */

    public Player(int canvasHeight) {
        this.x = PLAYER_START_X;
        this.y = canvasHeight / 2;
        this.v = 0;
        this.canvHeight = canvasHeight;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.alive = true;
        this.fitness = 0;
        this.score = 0;

        Image texture = new Image("file:/Users/mateuszmeller/Desktop/programowanie/FlappyRocket/src/sample/spaceship 2/spaceship2.png");
        this.image = new ImagePattern(texture);

        this.decisionTree = new TreeNode(2);
        setParams();
        generateDecisionTree(decisionTree);
        //System.out.println(decisionTree.inOrderTraversal(decisionTree));
    }

    public Player(int canvasHeight, Player parent1, Player parent2) {
        this.x = PLAYER_START_X;
        this.y = canvasHeight / 2;
        this.v = 0;
        this.canvHeight = canvasHeight;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.alive = true;
        this.fitness = 0;
        this.score = 0;

        Image texture = new Image("file:/Users/mateuszmeller/Desktop/programowanie/FlappyRocket/src/sample/spaceship 2/spaceship2.png");
        this.image = new ImagePattern(texture);
        inheritParams(parent1, parent2);
        born(parent1, parent2);
        mutate();
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
     * Imitates free fall
     */
    public void move() {
        /* If the the speed of the player is smaller
        * than max speed - accelerate, and displace. */
        if (this.v < MAXSPEED) {
            this.v += ACC;
        }
        this.y += this.v;
        this.fitness++;
    }


    /**
     * Checks if player collides with obstacle or goes out of bounds.
     *
     * @param obstacle obstacle with which player can collide.
     * @return true if there is collision, false otherwise.
     */
    public boolean isCollision(Obstacle obstacle) {
        return !(obstacle.getX() >= this.x + this.width ||
                obstacle.getX() + obstacle.getWidth() <= this.x ||
                obstacle.getY() >= this.y + this.height ||
                obstacle.getY() + obstacle.getHeight() <= this.y);
    }

    public boolean isInOfBounds() {
        return (this.y > 0) && (this.y + this.height < this.canvHeight);
    }

    /* TODO Solve major problem with collision detection - game doesn't stop after collision. */


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
     * Method which waits for the next action - opposite of jump().
     */
    public void waitForNext() {
        /* DO NOTHING */
    }


    /**
     * This method executes 2 instructions (methods ultimately) in order.
     *
     * @param instrNode1 first instruction to be executed.
     * @param instrNode2 second instruction to be executed.
     */
    public void do2(TreeNode instrNode1, TreeNode instrNode2) {
        exeInstr(instrNode1);
        exeInstr(instrNode2);
    }


    /**
     * This method executes 3 instructions (methods ultimately) in order.
     *
     * @param instrNode1 first instruction to be executed.
     * @param instrNode2 second instruction to be executed.
     * @param instrNode3 third instruction to be executed.
     */
    public void do3(TreeNode instrNode1, TreeNode instrNode2, TreeNode instrNode3) {
        exeInstr(instrNode1);
        exeInstr(instrNode2);
        exeInstr(instrNode3);
    }


    public void obstacleAhead(TreeNode instrNode1, TreeNode instrNode2, TreeNode instrNode3) {
        if (isCloseToObstacle(this.closestObstacle) && !obstacleAbove(this.closestObstacle) && canPassLower(this.closestObstacle)) {
            exeInstr(instrNode1);
        } else if (isCloseToObstacle(this.closestObstacle) && obstacleAbove(this.closestObstacle) && canPassUpper(this.closestObstacle)) {
            exeInstr(instrNode2);
        } else {
            exeInstr(instrNode3);
        }
    }


    public void tooHigh(TreeNode instrNode1, TreeNode instrNode2) {
        if (this.y <= this.MIN_TO_ROOF) {
            exeInstr(instrNode1);
        } else {
            exeInstr(instrNode2);
        }
    }


    public void tooLow(TreeNode instrNode1, TreeNode instrNode2) {
        if (this.y >= MIN_TO_GROUND) {
            exeInstr(instrNode1);
        } else {
            exeInstr(instrNode2);
        }
    }


    public void exeInstr(TreeNode instructionNode) {
        switch (instructionNode.getDecision()) {
            case 0:
                jump();
                break;
            case 1:
                waitForNext();
                break;
            case 2:
                obstacleAhead(instructionNode.getChild(0), instructionNode.getChild(1), instructionNode.getChild(2));
                break;
            case 3:
                tooHigh(instructionNode.getChild(0), instructionNode.getChild(1));
                break;
            case 4:
                tooLow(instructionNode.getChild(0), instructionNode.getChild(1));
                break;
            case 5:
                do2(instructionNode.getChild(0), instructionNode.getChild(1));
                break;
            case 6:
                do3(instructionNode.getChild(0), instructionNode.getChild(1), instructionNode.getChild(2));
                break;
            default:
                waitForNext();
                break;
        }
    }


    /**
     * Sets parameters on which decision will be made.
     * Randomly assigns them - use for the first generation.
     */
    public void setParams() {
        Random rnd = new Random();
        this.MIN_X = rnd.nextDouble() * GAME_WIDTH;
        this.MIN_Y = (int) (rnd.nextDouble() * GAME_HEIGHT / 2);
        this.MIN_TO_GROUND = (int) (rnd.nextDouble() * GAME_HEIGHT / 2 + GAME_HEIGHT / 2);
        this.MIN_TO_ROOF = (int) (rnd.nextDouble() * GAME_HEIGHT / 4);
    }


    /* Wierd not sure why I can access parents parameters - as they are private... */
    private void inheritParams(Player parent1, Player parent2) {
        this.MIN_X = parent1.MIN_X;
        this.MIN_TO_ROOF = parent2.MIN_TO_ROOF;
        this.MIN_TO_GROUND = parent2.MIN_TO_GROUND;
        this.MIN_Y = parent1.MIN_Y;
    }


    /**
     * Test if the obstacle is close and should act
     *
     * @param obstacle obstacle on which method tests
     * @return true if agent is too close and should act, false otherwise.
     */
    public boolean isCloseToObstacle(Obstacle obstacle) {
        return obstacle.getX() - this.x <= MIN_X &&
                ((obstacle.getY() < this.y + MIN_Y / 2 &&
                        obstacle.getY() > this.y) ||
                obstacle.getY() + obstacle.getHeight() > this.y - MIN_Y / 2 &&
                obstacle.getY() + obstacle.getHeight() < this.y);
    }


    /**
     * Test whether obstacle is pointing from the top.
     *
     * @param obstacle to be tested.
     * @return true if obstacle points from above, false if from bottom.
     */
    public boolean obstacleAbove(Obstacle obstacle) {
        return obstacle.getY() == 0;
    }

    public boolean canPassUpper(Obstacle obstacle) {
        return (obstacle.getY() + obstacle.getHeight() < this.y);

    }

    public boolean canPassLower(Obstacle obstacle) {
        return this.y < obstacle.getY();
    }


    public void generateDecisionTree(TreeNode root) {
        root.addChild();
        for (TreeNode child : root.getChildren()) {
            generateDecisionTree(child);
        }


    }


    public void setClosestObstacle(ArrayList<Obstacle> obstacles) {
        double distance = this.x + MIN_X;
        if (this.x + MIN_X / 4 < obstacles.get(0).getX()) {
            this.closestObstacle = obstacles.get(0);
        } else if (distance < obstacles.get(1).getX()){
            this.closestObstacle = obstacles.get(1);
        } else {
            this.closestObstacle = obstacles.get(2);
        }
    }

    public void execTree() {
        exeInstr(this.decisionTree);
    }

    public int getFitness() {
        return fitness;
    }


    public void born(Player parent1, Player parent2) {
        this.decisionTree = parent2.decisionTree;
        TreeNode temp = this.decisionTree;
        Random rnd = new Random();
        int penetK = rnd.nextInt(5);
        for (int i = 0; i < penetK - 1; i++) {
            if (!temp.getChildren().isEmpty()) {
                int chooseChild = rnd.nextInt(temp.getNumberOfChildren() - 1);
                if (chooseChild < 0) {
                    chooseChild = 0;
                } else if (chooseChild > 3) {
                    chooseChild = 3;
                }
                temp = temp.getChild(chooseChild);
            }
        }
        for (int i = 0; i < penetK; i++) {
            if (parent1.decisionTree.getChildren().isEmpty()) {
                temp = parent1.decisionTree;
            } else {
                temp = parent1.decisionTree.getChild(parent1.decisionTree.getNumberOfChildren() - 1);
            }
        }
        this.decisionTree = temp;
        //System.out.println("done");
        // decisionTree.geneticX(decisionTree, parent1.decisionTree.geneticY(parent1.decisionTree));
    }

    private void mutate() {
        Random rnd = new Random();
        double mutCoof = rnd.nextDouble();

        if (mutCoof < 0.1) {
            generateDecisionTree(this.decisionTree);
            setParams();
        }
    }

    public void setAlive() {
        this.alive = true;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
