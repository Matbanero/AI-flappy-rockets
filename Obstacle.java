package sample;

import java.util.Random;

public class Obstacle extends Sprite {

    private int canvasWidth;

    public Obstacle(int canvWidth, int canvHeight) {
        this.canvasWidth = canvWidth;
        this.x = canvWidth;
        this.width = 40;
        Random rnd = new Random();
        this.height = (int) (rnd.nextDouble() * canvHeight / 2) - 30;
        if (this.height < 100) {
            this.height = 180;
        }

        int dir = rnd.nextInt(2);

        if (dir == 1) {
            this.y = canvHeight - this.height;
        } else {
            this.y = 0;
        }
    }

    public boolean inBounds() {
        return this.x + this.width >= 0;
    }


    public void move() {
        this.x -= 2;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
