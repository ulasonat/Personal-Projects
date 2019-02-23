// Ulaş Onat Alakent
//
// yl9i70
//
// Hunting Game (Task 3)
//
// 2018/11/18 22:56:06
//
// This solution was submitted and prepared by Ulaş Onat Alakent, yl9i70 for the
// Hunting Game (Task 3) assignment of the Practical software engineering I. course.
//
// I declare that this solution is my own work.
//
// I have not copied or used third party solutions.
//
// I have not passed my solution to my classmates, neither  made it public.
//
// Students’ regulation of Eötvös Loránd University (ELTE Regulations
// Vol. II. 74/C. § ) states that as long as a student presents another
// student’s work - or at least the significant part of it - as his/her own
// performance, it will count as a disciplinary fault. The most serious
// consequence of a disciplinary fault can be dismissal of the student from
// the University.

package yl9i70;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/** Instead of using JButton objects to represent the buttons directly, here a class named 'yl9i70.Area' was used, to increase
 * the capability of doing this rather than using just a single JButton object. */

public class Area extends JButton { // will be changed to Icon later on !!
    private String id; // Each area will have a ID.
    // "F": fugitive
    // "H": hunter
    // "" : empty

    // this variable will only be true when a button is in a position of 'pressed'. The reason is that if the user
    // clicks a wrong button that he/she should not have clicked, 'pressed' position will be lost, but with the help of
    // this boolean, we can make that button looked 'pressed' as soon as its 'unpressed'.
    private boolean isOn;
    private String fugitivePath;
    private String hunterPath;

    /** Constructs a yl9i70.Area object that initializes the fields and sets the background color. */
    public Area() {
        fugitivePath = "icons/fugitive.png";
        hunterPath = "icons/hunter.png";
        isOn = false;
        setBackground(new Color(146, 252, 138));
        id = "";
    }

    /** Constructors a yl9i70.Area object that initializes the fields
     * Also checks if the ID is either "F" or "H", loads the icons to that areas. */
    public Area(String id) {
        super(id);
        fugitivePath = "icons/fugitive.png";
        hunterPath = "icons/hunter.png";
        this.id = id;
        isOn = false;
        setBackground(new Color(146, 252, 138));
        if(id.equals("F")) {
            setText("");
            setIcon(new ImageIcon(fugitivePath));
        } else if(id.equals("H")) {
            setText("");
            setIcon(new ImageIcon(hunterPath));
        }

    }

    // Getters and Setters
    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getID() {
        return id;
    }

    /** While setting an ID, according to the new ID, the icon will be changed immediately. */
    public void setID(String id) {
        this.id = id;
        if(id.equals("F")) {
            setText("");
            setIcon(new ImageIcon(fugitivePath));
        } else if(id.equals("H")) {
            setText("");
            setIcon(new ImageIcon(hunterPath));
        } else if(id.equals("")) {
            setIcon(null);
        }
    }

    /** Sets the fugitive path with checking if it is a png file, if not then does not set it.*/
    public boolean setFugitivePath(File fugitiveFile) {
        if(isItPng(fugitiveFile)) {
            this.fugitivePath = fugitiveFile.getPath();
            if(id.equals("F"))
                setIcon(new ImageIcon(fugitivePath));
            return true;
        } else {
            return false;
        }
    }

    /** Sets the hunter path with checking if it is a png file, if not then does not set it.*/
    public boolean setHunterPath(File hunterFile) {
        if(isItPng(hunterFile)) {
            this.hunterPath = hunterFile.getPath();
            if (id.equals("H"))
                setIcon(new ImageIcon(hunterPath));
            return true;
        } else {
            return false;
        }
    }

    /** Checks if a specific file is a type of file of png,
     *
     * @param file: the file to be checked.
     * @return true if it is, false if not.
     */
    public boolean isItPng(File file) {
        String name = file.getName();
        String extension = "";

        int i = name.lastIndexOf('.');
        if(i>0)
            extension = name.substring(i+1);

        if(extension.equals("png"))
            return true;

        return false;
    }
}
