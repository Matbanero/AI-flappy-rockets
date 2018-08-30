package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class World {
    private int width;
    private int height;
    private int score;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Circle> stars;
    private Player player;
    private Text text;


    public World(int x, int y) {
        this.width = x;
        this.height = y;
        this.score = 0;
        player = new Player(y);
        stars = new ArrayList<>();
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(this.width, this.height));
        for (int i = 0; i < 50; i++) {
            Random rnd = new Random();
            int starX = (int) (rnd.nextDouble() * this.width + 4);
            int starY = (int) (rnd.nextDouble() * this.height + 19);
            Circle star = new Circle(starX, starY, 3);
            stars.add(star);
        }
        this.text = new Text();
        this.text.setX(x - 100);
        this.text.setY(40);
        this.text.setFill(Color.WHITE);
    }

//  /Users/mateuszmeller/Desktop/programowanie/src/sample/Controller.java

    public void drawWorld(GraphicsContext gc) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, width, height);
        for (Circle star : stars) {
            gc.setFill(Color.WHITE);
            gc.fillOval(star.getCenterX(), star.getCenterY(), star.getRadius(), star.getRadius());
        }
        for (Obstacle obstacle : obstacles) {
            gc.setFill(Color.BROWN);
            gc.fillRect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }
        if (player.isAlive()) {
            gc.setFill(Color.GREEN);
            gc.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }
        this.text.setText("Score: " + Integer.toString(score));

    }

    private void moveStars() {
        for (Circle star: stars) {
            if (starInBound(star)) {
                star.setCenterX(star.getCenterX() - 2);
            } else {
                star.setCenterX(width);
            }
        }
    }

    private boolean starInBound(Circle star) {
        return star.getCenterX() + star.getRadius() >= 0;
    }

    public void update() {
        if (obstacles.get(obstacles.size() - 1).getX() < (this.width - 80)) {
            obstacles.add(new Obstacle(this.width, this.height));
        }

        /* Using iterator to avoid ConcurrentModificationException
         * because here we modify arrayList while iterating through it. */
        Iterator<Obstacle> iter = obstacles.iterator();

        /* Iterate through whole arrayList (while there is next element) */
        while (iter.hasNext()) {
            Obstacle obstacle = iter.next();
            /* If the obstacle is on the screen "move" it
             * else delete it from arrayList - so it doesn't clog memory. */
            if (obstacle.inBounds()) {
                obstacle.move();
            } else {
                iter.remove();
            }

            /* If there is player collision with any object end game,
            * else if player successfully passes obstacle increment score. */
            if (player.isCollision(obstacle)) {
                player.kill();
            } else if (obstacle.getX() + obstacle.getWidth() == player.getX()) {
                this.score++;
            }
        }
        moveStars();
        this.player.move();
    }

    public Player getPlayer() {
        return player;
    }

    public Text getText() {
        return text;
    }
}
