package sample;


public class Player extends Sprite{
    private double y;
    private double acc;
    private double theta;
    private int canvHeight;
    private boolean alive;

    public Player(int canvasHeight) {
        this.x = 50;
        this.y = canvasHeight / 2;
        this.acc = 0;
        this.theta = 0;
        this.canvHeight = canvasHeight;
        this.width = 60;
        this.height = 30;
        this.alive = true;
    }

    /* Imitates free fall */
    public void move() {
        if (this.acc < 15) {
            this.acc += 0.06;
        }
        this.y += this.acc;
    }


    public boolean isCollision(Obstacle obstacle) {
        return this.y + this.height > this.canvHeight ||
                !(obstacle.getX() > this.x + this.width ||
                obstacle.getX() + obstacle.getWidth() < this.x ||
                obstacle.getY() > this.y + this.height ||
                obstacle.getY() + obstacle.getHeight() < this.y);
    }

    public int getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void kill() {
        this.alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void jump() {
        this.acc = 0;
        this.y -= 50;
    }

}
