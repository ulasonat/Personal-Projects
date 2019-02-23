package yl9i70;

import java.awt.*;

public abstract class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    /** Returns true if two sprite collides.
     * This method will be used to determine if dragon eats the player.*/
    public boolean collides(Sprite other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return rect.intersects(otherRect);
    }

    /** Returns true if a sprite collides with another sprite. */
    public boolean collides(Sprite other, int i) {
        Rectangle rect = null;
        switch (i) {
            case 1:
                rect = new Rectangle(x, y-3, width, height);
                break;
            case 2:
                rect = new Rectangle(x-3, y, width, height);
                break;
            case 3:
                rect = new Rectangle(x, y+3, width, height);
                break;
            case 4:
                rect = new Rectangle(x+3, y, width, height);

        }
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return rect.intersects(otherRect);
    }

    // Getters and Setters


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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
