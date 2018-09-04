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
     * Gets height of the sprite.
     *
     * @return height of the sprite.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height of the sprite.
     *
     * @param height to be set.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets width of the sprite.
     *
     * @return width of the sprite.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width of the sprite.
     *
     * @param width to be set.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets x position of the sprite.
     *
     * @return x position of the sprite.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x position of the sprite.
     *
     * @param x to be set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y position the sprite.
     *
     * @return y position of the sprite.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y position of the sprite.
     *
     * @param y to be set.
     */
    public void setY(int y) {
        this.y = y;
    }
}
