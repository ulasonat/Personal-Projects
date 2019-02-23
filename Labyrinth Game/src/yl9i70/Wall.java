package yl9i70;

import java.awt.*;

public class Wall extends Sprite {

    private boolean isHorizontal;

    /** Constructor that sets the fields of the Wall according to the information that is passed as parameters.*/
    public Wall(int x, int y, Image image, boolean isHorizontal) {
        super(x, y, 0,0, image);
        this.isHorizontal = isHorizontal;
        if(isHorizontal) {
            super.width = 60;
            super.height = 5;
        } else {
            super.width = 5;
            super.height = 60;
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
