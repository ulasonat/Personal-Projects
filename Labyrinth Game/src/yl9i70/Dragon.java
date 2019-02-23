package yl9i70;

import java.awt.*;
import java.util.Random;

/** The class to represent the 'door'.*/
public class Dragon extends MovingCharacter {

    private final int speed = 2;

    private int direction;
    /*
    1 -> up
    2 -> left
    3 -> down
    4 -> right
    */

    public Dragon(int width, int height, Image image, Level level) {
        super(0,0, width, height, image);
        setPosition(level);
        changeDirection();
    }

    /** Determines the starting positions of the dragon, while makes sure that it cannot start on a wall or the player.*/
    public void setPosition(Level level) {
        Random rand = new Random();
        do {
            int newX = rand.nextInt(600) + 50;
            int newY = rand.nextInt(550) + 50;
            x = newX;
            y = newY;
        } while (level.collides(this));
    }

    /** Makes to move for 'Dragon', changes the direction if it comes to the edge of the map.*/
    public void move() {
        switch (direction) {
            case 1:
                y -= speed;
                if(y < 0)
                    changeDirection();
                break;
            case 2:
                x -= speed;
                if(x < 0)
                    changeDirection();
                break;
            case 3:
                y += speed;
                if(y > MainFrame.HEIGHT - 50)
                    changeDirection();
                break;
            case 4:
                x += speed;
                if(x > MainFrame.WIDTH - 60)
                    changeDirection();
                break;
        }
    }

    /** Makes a move according to the direction and changes it. */
    public void changeDirection() {
        switch (direction) {
            case 1:
                y += speed;
                break;
            case 2:
                x += speed;
                break;
            case 3:
                y -= speed;
                break;
            case 4:
                x -= speed;
                break;
        }
        int oldDirection = direction;
        do {
            Random rand = new Random();
            direction = rand.nextInt(4) + 1;
        } while (direction == oldDirection);

    }
}
