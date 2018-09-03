package sample;

import javafx.scene.paint.ImagePattern;

public class Sprite {
    protected int x;
    protected double y;
    protected int height;
    protected int width;
    protected ImagePattern image;

    /**
     * Gets the texture of a sprite.
     *
     * @return texture of a sprite.
     */
    public ImagePattern getImage() {
        return image;
    }


    /**
     * Gets height of the obstacle.
     *
     * @return height of the obstacle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height of the obstacle.
     *
     * @param height to be set.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets width of the obstacle.
     *
     * @return width of the obstacle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width of the obstacle.
     *
     * @param width to be set.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets x position of the obstacle.
     *
     * @return x position of the obstacle.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x position of the obstacle.
     *
     * @param x to be set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y position the obstacle.
     *
     * @return y position of the obstacle.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y position of the obstacle.
     *
     * @param y to be set.
     */
    public void setY(int y) {
        this.y = y;
    }
}
