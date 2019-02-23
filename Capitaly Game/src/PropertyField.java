// Ulaş Onat Alakent
//
// yl9i70
//
// Capitaly Game
//
// 2018/10/15 03:55:00
//
// This solution was submitted and prepared by Ulaş Onat Alakent, yl9i70 for the
// Capitaly Game assignment of the Practical software engineering I. course.
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

import java.util.ArrayList;

public class PropertyField extends Field {

    // As a difference to Lucky and Service fields, Property fields will have an owner, but at the start of the game
    // of course there will not be an owner.
    private Player owner;

    // Stores the players who have visited the field at least once.
    /** When we will determine if a player has the right to build a house on a property (for that, he must have been there before
     * we will check if he contains in the following array list, because once a player move to a property, he will be added
     * to the array list. */
    private ArrayList<Player> playersWhoVisited;

    /** To store if there is a house on this property. */
    private boolean isThereHouse = false;

    public PropertyField(String name) {
        // Property fields will have city names (such as Budapest, Istanbul, London etc.)
        super(name, 1000);
        owner = null;
        playersWhoVisited = new ArrayList<>();
    }

    public void setOwner(Player player) {
        owner = player;
    }

    /** sets the value of isThereHouse variable to true, will be used when a player purchases a house on this property. */
    public void buildHouse() {
        isThereHouse = true;
    }

    /** sets the value of isThereHouse variable to false, will be used when a player that has this house quits the game. */
    public void destroyHouse() {
        isThereHouse = false;
    }

    // Getters
    public Player getOwner() {
        return owner;
    }

    public boolean isThereHouse() {
        return isThereHouse;
    }

    public ArrayList<Player> getPlayersWhoVisited() {
        return playersWhoVisited;
    }

    @Override
    public String getClassName() {
        return "PropertyField";
    }
}
