package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class World implements Commons {
    private static int generation = 0;
    private int width;
    private int height;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Circle> stars;
    private Text text;
    private static final int STAR_RAD = 3;
    private ArrayList<Player> population;
    private static ArrayList<Player> fittestAncestors = new ArrayList<>();


    public World(int x, int y) {
        this.width = x;
        this.height = y;
        generation++;
        stars = new ArrayList<>();
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(this.width, this.height));

        population = new ArrayList<>();
        if (generation < 2 || (fittestAncestors.get(fittestAncestors.size() - 1).getFitness() < MIN_FITNESS)) {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                population.add(new Player(y));
            }
        } else {
            int j = POPULATION_SIZE / 2 - 1;
            for (int i = 0; i < POPULATION_SIZE / 2; i++) {
                population.add(new Player(y, fittestAncestors.get(i), fittestAncestors.get(j)));
                population.add(new Player(y));
                j--;
            }
            fittestAncestors.clear();
        }


        /* Create stars */
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            Random rnd = new Random();
            /* Creating random positions of stars - with 'magical' offsets */
            int starX = (int) (rnd.nextDouble() * this.width + 4);
            int starY = (int) (rnd.nextDouble() * this.height + 19);
            Circle star = new Circle(starX, starY, STAR_RAD);
            stars.add(star);
        }

        /* Create text which shows score of the player */
        this.text = new Text();
        this.text.setX(x - TEXT_X_OFF);
        this.text.setY(TEXT_Y_POS);
        this.text.setFill(Color.WHITE);
        this.text.setText("Generation: " + Integer.toString(generation));
    }


    /**
     * Draws the world, that is - background, stars, obstacles, player and score
     *
     * @param gc graphics context of the canvas on which the world will be drawn.
     */
    public void drawWorld(GraphicsContext gc) {

        /* Draw background */
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, width, height);

        /* Draw stars */
        for (Circle star : stars) {
            gc.setFill(Color.WHITE);
            gc.fillOval(star.getCenterX(), star.getCenterY(), star.getRadius(), star.getRadius());
        }

        /* Draw obstacles */
        for (Obstacle obstacle : obstacles) {
            gc.setFill(obstacle.getImage());
            gc.fillRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }

        /* If player is alive draw it */
        for (Player player : population) {
            if (player.isAlive()) {
                gc.setFill(player.getImage());
                gc.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            }
        }
    }


    /**
     * Moves all stars in the left direction - Imitate spacecraft movement.
     */
    private void moveStars() {
        for (Circle star : stars) {
            /* If the star goes out of bounds (we passed it)
            * generate it again on the rightmost part of the board. */
            if (starInBound(star)) {
                star.setCenterX(star.getCenterX() - WORLD_SPEED);
            } else {
                star.setCenterX(width);
            }
        }
    }


    /**
     * Checks if the star is on the screen.
     *
     * @param star star which will be tested.
     * @return true if the star is still on screen, false otherwise.
     */
    private boolean starInBound(Circle star) {
        return star.getCenterX() + star.getRadius() >= 0;
    }


    /**
     * Updates the state of the world.
     */
    public void update() {

        /* If the last obstacle has moved far enough from the
        * right side of the screen, generate new one.
        * */
        if (obstacles.get(obstacles.size() - 1).getX() <= (this.width - OBSTACLE_GENERATE_OFFSET)) {

            /* Create new instance of a obstacle until there is gap big enough
             * between them.
              * */
            Obstacle newObstacle;
            do {
                newObstacle = new Obstacle(this.width, this.height);
            } while (Math.abs(obstacles.get(obstacles.size() - 1).getHeight() -
                    newObstacle.getHeight()) < OBSTACLES_MINIMAL_DIFFERENCE);
            obstacles.add(newObstacle);
        }

        /* Using iterator to avoid ConcurrentModificationException
         * because here we modify arrayList while iterating through it.
         * */
        Iterator<Obstacle> iter = obstacles.iterator();

        /* Iterate through whole arrayList (while there is next element)
         *  */
        while (iter.hasNext()) {
            Obstacle obstacle = iter.next();
            /* If the obstacle is on the screen "move" it
             * else delete it from arrayList - so it doesn't clog memory.
             * */
            if (obstacle.inBounds()) {
                obstacle.move();
            } else {
                iter.remove();
            }


            /* Using iterator for player as well - to easily remove
             * if there is collision.
             * */
            Iterator<Player> playerIter = population.iterator();
            while (playerIter.hasNext()) {
                Player player = playerIter.next();
                /* If there is collision kill the player, otherwise give score to the player. */
                if (!player.isInOfBounds() || player.isCollision(obstacle)) {
                    player.kill();
                    playerIter.remove();

                    /* Add the better half of population to fittest ancestors. */
                    if (population.size() < POPULATION_SIZE / 2) {
                        fittestAncestors.add(player);
                    }
                } else if (obstacle.getX() + obstacle.getWidth() == player.getX()) {
                    player.setScore(player.getScore() + 1);
                }
            }
        }

        /* For every player in the population, find closet obstacle,
        * move and execute decision tree.
        * */
        for (Player player : population) {
            player.setClosestObstacle(obstacles);
            player.move();
            player.execTree();
        }
        moveStars();

    }


    /**
     * Gets the text (score).
     *
     * @return text which shows the score.
     */
    public Text getText() {
        return text;
    }


    /**
     * Checks if the whole population is dead.
     *
     * @return true if population is empty i.e. dead, false otherwise.
     */
    public boolean allDead() {
        return population.isEmpty();
    }

    /**
     * Gets the current generation number.
     *
     * @return generation number as integer.
     */
    public static int getGeneration() {
        return generation;
    }
}
