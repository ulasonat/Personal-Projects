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

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;

public class MainPanel extends JPanel {

    private Board board;
    private Area[][] areas;
    private JLabel turnLabel;
    private int currentTurn;
    private int maximumTurn;
    private boolean isSelected = false;
    private boolean isFugitive = true; // if the fugitive should play next.
    private MainFrame newGameStarter; // This reference will only be used to start a new game.


    // n = size of the board
    /** Initializes the fields, initializes the starting locations of the characters according to their position.
     * Also importantly, adds an AreaListener to each area object. Whenever an area is clicked, the actionPerformed method
     * will be called that belongs to AreaListener class which mainly decides how to refresh the screen with the updated information.
     */
    public MainPanel(int n) {
        board = new Board(n);
        areas = new Area[n][n];
        currentTurn = 0;
        maximumTurn = 4 * n; // requirement
        turnLabel = new JLabel("The fugitive starts the game. Good luck!");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new GridLayout(n, n)); // the layout will be NxN.

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Area area;
                if ((i == n - 1 && j == n - 1) || (i == 0 && j == 0) || (i == 0 && j == n - 1) || (i == n - 1 && j == 0)) {
                    area = new Area("H");
                } else if (i == (n / 2) && j == (n / 2)) {
                    area = new Area("F");
                } else {
                    area = new Area();
                }
                area.addActionListener(new AreaListener(i,j));
                area.setPreferredSize(new Dimension(80, 40));
                areas[i][j] = area;
                add(area);
            }
        }

    }

    /** @return the turnLabel object to be placed in the frame. */
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    /** this setted reference will call startNewGame method to start a new game. */
    public void addNewGameStarter(MainFrame newGameStarter) {
        this.newGameStarter = newGameStarter;
    }


    /** checks if the game is over, there are two scenarios for that to happen.
     * 1) There is no place for the fugitive to move anymore, so the hunter wins.
     * 2) The current turn is now equal to the maximum turn, so that the fugitive wins.
     * Displays messages and sounds according to the result of the game.
     * @return true if the game is over, false if the game still continues.
     * */
    public boolean isGameOver() {
            // the game can only be over if and only if the last move was played by the hunter,
            if(isFugitive) {
                for (int i=0; i<board.getBoardSize(); i++) {
                    for (int j=0; j<board.getBoardSize(); j++) {
                        Area search = areas[i][j];
                        if (search.getID().equals("F")) {
                            boolean cnd1 = true, cnd2 = true, cnd3 = true, cnd4 = true;
                            if(!(i+1 == board.getBoardSize()))
                                cnd1 = areas[i + 1][j].getID().equals("H");
                            if(!(i-1 == -1))
                                cnd2 = areas[i-1][j].getID().equals("H");
                            if(!(j+1 == board.getBoardSize()))
                                cnd3 = areas[i][j+1].getID().equals("H");
                            if(!(j-1 == -1))
                                cnd4 = areas[i][j-1].getID().equals("H");

                            if(cnd1 && cnd2 && cnd3 && cnd4) {
                                playSound("sounds/game_over.wav");
                                JOptionPane.showMessageDialog(MainPanel.this, "The Hunter has won!",
                                        "Congratulations!", JOptionPane.PLAIN_MESSAGE);
                                return true;
                            }
                        }
                    }
                }
            }

            if(currentTurn == maximumTurn) {
                playSound("sounds/game_over.wav");
                JOptionPane.showMessageDialog(MainPanel.this, "The fugitive has won!",
                        "Congratulations!", JOptionPane.PLAIN_MESSAGE);
                return true;
            }

            return false;

    }

    /** Plays a sound, this method will be called in several places.
     * @param soundPath: the path of the sound to be played. */
    public void playSound(String soundPath) {
        InputStream sound;
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(soundPath)));
            clip.start();
        }
        catch (Exception e)
        {
            System.out.println("Exception caught, which is: " + e);
        }
    }

    /** sets the fugitiveIcon, and does it inside try-catch block.
     * if the setting method returns false, displays a warning message.
     * (note that setting method only returns false if the file that is provided is not a type of png. */
    public void setFugitiveIcon(File file) {
        try {
            for (int i=0; i<board.getBoardSize(); i++) {
                for (int j = 0; j < board.getBoardSize(); j++) {
                    Area search = areas[i][j];
                    if(!search.setFugitivePath(file)) {
                        JOptionPane.showMessageDialog(this, "The file you provided is not a png!", "Warning!",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainPanel.this, "The file you have chosen cannot be an icon.");
        }
    }

    /** sets the hunterIcon, and does it inside try-catch block.
     * if the setting method returns false, displays a warning message.
     * (note that setting method only returns false if the file that is provided is not a type of png. */
    public void setHunterIcon(File file) {
        try {
            for (int i=0; i<board.getBoardSize(); i++) {
                for (int j = 0; j < board.getBoardSize(); j++) {
                    Area search = areas[i][j];
                    if(!search.setHunterPath(file)) {
                        JOptionPane.showMessageDialog(this, "The file you provided is not a png!", "Warning!",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainPanel.this, "The file you have chosen cannot be an icon.");
        }
    }

    /** Makes the move for the 'fugitive' character, if an inappropriate button is selected, displays a
     * warning message to the user.
     * @return true if the move successfully done, false if not.
     */
    public boolean fugitiveMove(Area area) {
        if (!((area.getID().equals("F")))) {
            playSound("sounds/warning.wav");
            JOptionPane.showMessageDialog(MainPanel.this, "The fugitive should play!", "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Now the fugitive is selected!
        playSound("sounds/button_pressed.wav");
        area.getModel().setPressed(true);
        area.setOn(true);
        isFugitive = false;
        return true;
    }

    /** Makes the move for the 'hunter' character, if an inappropriate button is selected, displays a
     * warning message to the user.
     * @return true if the move successfully done, false if not.
     */
    public boolean hunterMove(Area area) {
        if (!(area.getID().equals("H"))) {
            playSound("sounds/warning.wav");
            JOptionPane.showMessageDialog(MainPanel.this, "The hunter should play!", "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Now the hunter is selected!
        playSound("sounds/button_pressed.wav");
        area.getModel().setPressed(true);
        area.setOn(true);
        isFugitive = true;
        return true;
    }

    // Warning messages
    public void warning1(Area selectedArea) {
        playSound("sounds/warning.wav");
        JOptionPane.showMessageDialog(MainPanel.this,"You cannot stay in your position.");
        selectedArea.getModel().setPressed(true);
    }

    public void warning2(Area selectedArea) {
        playSound("sounds/warning.wav");
        JOptionPane.showMessageDialog(MainPanel.this, "You can only move to 4 main directions.");
        selectedArea.getModel().setPressed(true);
    }

    public void warning3(Area selectedArea) {
        playSound("sounds/warning.wav");
        JOptionPane.showMessageDialog(MainPanel.this, "You cannot walk in another character!");
        selectedArea.getModel().setPressed(true);
    }

    /** sets the ID of the area that is selected, and also does the cleaning which is to remove the ID from the old area. */
    public void makeMove(Area area, Area selectedArea) {
        area.setID(selectedArea.getID());
        selectedArea.setID("");
        playSound("sounds/button_pressed.wav");
    }

    /** Removes the pressing looking on the last selected area, increase the currentTurn.
     * This method will be called at nearly end of the overridden actionPerformed method. */
    public void increaseTour(Area selectedArea) {
        selectedArea.getModel().setPressed(false);
        selectedArea.setOn(false);
        isSelected = false;
        currentTurn++;
    }


    /** The AreaListener class that each area has as its action listener. */
    class AreaListener implements ActionListener {

        private int x;
        private int y;

        public AreaListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /** Firstly, creates an yl9i70.Area object with the source of the event, which is the button that is selected at the moment.
         * Then according to the boolean variables which are used to indicate what kind of move to expect, determines what to do.
         * (for example, if no button was selected before this move, the next move will be to move the character.)
         *
         * Displays warning messages in each case where the user selected an appropriate area.
         *
         * Mainly, makes the necessary updating (removes the old pressed area, calls functions to update the icons,
         * calculates the new positions of the characters, removes the old icons that the characters are no longer on
         *
         * At the end of the method, increases the variable that holds the turn value,
         *
         * Updates the new label information with the new turn value.
         *
         * Also checks if the game is over, in case it is;
         * displays a congratulations message and calls the method 'startNewGame' to start a new game.
         * */
        @Override
        public void actionPerformed(ActionEvent e) {

            Area area = (Area) e.getSource();

            // if nothing is selected yet,
            if (!isSelected) {

                if (isFugitive) {
                    // Then the fugitive should play,
                    if(!fugitiveMove(area))
                        return;
                } else {
                    if(!hunterMove(area))
                        return;
                }

                isSelected = true;
            }

            // if only the character is selected,
            else {

                Area selectedArea = null;
                int selectedX = 0;
                int selectedY = 0;

                for (int i=0; i<board.getBoardSize(); i++) {
                    for (int j=0; j<board.getBoardSize(); j++) {
                        if (areas[i][j].isOn()) {
                            selectedArea = areas[i][j];
                            selectedX = i;
                            selectedY = j;
                            break;
                        }
                    }
                }

                // If the same button is pressed,
                if(area == selectedArea) {
                    warning1(selectedArea);
                    return;
                }

                int xDif = selectedX - x;
                int yDif = selectedY - y;
                boolean condition1 = (xDif <= 1 && xDif >= -1 && yDif <= 1 && yDif >= -1);
                boolean condition2 = (xDif == 1 && yDif == 1) || (xDif == 1 && yDif == -1);
                boolean condition3 = (xDif == -1 && yDif == 1) || (xDif == -1 && yDif == -1);


                if (!condition1 || condition2 || condition3) {
                    warning2(selectedArea);
                    return;
                }


                // If one of the correct buttons is pressed,
                if(area.getID().equals("")) {
                    makeMove(area, selectedArea);
                } else {
                    warning3(selectedArea);
                    return;
                }

                increaseTour(selectedArea);

                turnLabel.setText("Turn: (" + currentTurn + "/" + maximumTurn + ")");
            }

            if(isGameOver()) {
                newGameStarter.startNewGame(newGameStarter.getINITIAL_BOARD_SIZE());
            }
        }

    }
}
