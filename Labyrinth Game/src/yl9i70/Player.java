package yl9i70;

import java.awt.*;

public class Player extends MovingCharacter {

    private int speed = 5;

    public Player(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    /** @param i : direction
     * Makes the move for the player.*/
    public void move(int i) {
        switch (i) {
            case 1:
                    y -= speed;
                break;
            case 2:
                    x -= speed;
                break;
            case 3:
                    y += speed;
                break;
            case 4:
                    x += speed;
                break;
        }
    }
}
