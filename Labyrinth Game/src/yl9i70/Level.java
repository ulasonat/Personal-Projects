package yl9i70;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/** The class that contains the information of the levels.*/
public class Level {

    private ArrayList<Wall> walls;
    private Scanner fileReader;

    public Level(String path) throws IOException {
        walls = new ArrayList<>();
        loadLevel(path);
    }

    /** Opens the file, adds walls to the array list object that this class has.*/
    public void loadLevel(String path) {

        openFile(path);
        int x, y;
        String c;
        Image wallImage = new ImageIcon("data/images/wall.png").getImage();
        try {
            while (fileReader.hasNext()) {
                x = fileReader.nextInt();
                y = fileReader.nextInt();
                c = fileReader.next();
                walls.add(new Wall(x*60,y*60, wallImage, c.equals("H")));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /** Opens the file.*/
    public boolean openFile(String fileName) {
        try {
            fileReader = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File could not been opened.");
            return false;
        }
        return true;
    }

    /** @return true if any wall of the level collides with a MovingCharacter object.*/
    public boolean collides(MovingCharacter movingCharacter) {
        Wall collidedWith = null;
        for (Wall wall : walls) {
            if (movingCharacter.collides(wall)) {
                collidedWith = wall;
                break;
            }
        }
        return (collidedWith != null);
    }

    /** @param i : this parameter represents the direction.
     * @return true if any wall of the level collides with a MovingCharacter object with calling the overloaded collides
     * method of the movingCharacter class has.
     */
    public boolean collides(MovingCharacter movingCharacter, int i) {
        Wall collidedWith = null;
        for (Wall wall : walls) {
            if (movingCharacter.collides(wall, i)) {
                collidedWith = wall;
                break;
            }
        }
        return (collidedWith != null);
    }

    /** Draws graphics. */
    public void draw(Graphics g) {
        for (Wall wall : walls) {
            wall.draw(g);
        }
    }

    /** @return true if player collides with doors, which means level is completed successfully.*/
    public boolean isOver(Player player, Door door) {
        return (player.collides(door));
    }

}