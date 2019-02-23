package yl9i70;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.util.logging.Logger;

public class MainPanel extends JPanel {

    private Image background;
    private int levelNum = 1;
    private Level level;
    private Player player;
    private Dragon dragon;
    private Door door;
    private Timer frameTimer;
    private boolean paused;
    private int FPS = 144;
    private int PLAYER_WIDTH = 50;
    private int PLAYER_HEIGHT = 50;
    private int DRAGON_WIDTH = 50;
    private int DRAGON_HEIGHT = 50;
    private int DOOR_WIDTH = 35;
    private int DOOR_HEIGHT = 35;
    private Database database;
    private JLabel timeLabel;
    private Timer timer;
    private long startTime;

    /** Initializes the fields, puts input actions. */
    public MainPanel() {

        // database = new Database();
        // Since I had used MySQL, I have commented out the part where the database is included.

        timeLabel = new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText("Level " + levelNum + " | " + elapsedTime() + " ms");
            }
        });

        startTime = System.currentTimeMillis();
        timer.start();

        paused = false;
        background = new ImageIcon("data/images/background.jpg").getImage();

        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed up");
        this.getActionMap().put("pressed up", new PlayerListener(1));

        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed left");
        this.getActionMap().put("pressed left", new PlayerListener(2));

        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed down");
        this.getActionMap().put("pressed down", new PlayerListener(3));

        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed right");
        this.getActionMap().put("pressed right", new PlayerListener(4));

        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });

        restart();
        frameTimer = new Timer(1000 / FPS, new FrameListener());
        frameTimer.start();
    }

    /** Restarts the level. */
    public void restart() {

        startTime = System.currentTimeMillis();

        try {
            level = new Level("data/files/file" + levelNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image playerImage = new ImageIcon("data/images/player.png").getImage();
        player = new Player(5, MainFrame.HEIGHT-130, PLAYER_WIDTH, PLAYER_HEIGHT, playerImage);
        Image dragonImage = new ImageIcon("data/images/dragon.png").getImage();
        dragon = new Dragon(DRAGON_WIDTH, DRAGON_HEIGHT, dragonImage, level);
        Image doorImage = new ImageIcon("data/images/door.jpg").getImage();
        door = new Door(625, 10, DOOR_WIDTH, DOOR_HEIGHT, doorImage);


    }

    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /** Draws the objects, sets the map lighting and adds anti aliasing.*/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, MainFrame.WIDTH, MainFrame.HEIGHT, null);
        level.draw(g);
        player.draw(g);
        dragon.draw(g);
        door.draw(g);
        //setMapLighting(g);
        addAntiAliasing(g);
    }

    /** Adds anti alisaing to make game look better, however since we only use images in this game,
     * it is useless. In case we want to draw some shapes, this will be useful. */
    public void addAntiAliasing(Graphics g) {
        Graphics2D antiAliasing = (Graphics2D) g;
        antiAliasing.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /** Sets the map lighting.*/
    public void setMapLighting(Graphics g) {
        g.setColor(Color.BLACK);
        for(int x=0; x<MainFrame.WIDTH; x+=5) {
            for(int y=0; y<MainFrame.HEIGHT; y+=5) {
                if(x - player.getX() > 120 || x - player.getX() < - 120)
                    g.fillRect(x,y,10,10);
                if(y - player.getY() > 120 || y - player.getY() < - 120)
                    g.fillRect(x,y,10,10);
            }
        }
    }

    /** Starts a new game.*/
    public void startNewGame() {
        levelNum = 1;
        restart();
    }

    /** Outputs the data.*/
    public void outputData() {
        database.loadData();
            int counter = 1;
            String textToDisplay = "";
            for(String n : database.getHighscores().keySet()) {
                if(counter <= 10)
                textToDisplay += counter + ") " + n + ": " + database.getHighscores().get(n) + "\n";
                counter++;
            }
            JOptionPane.showMessageDialog(this, textToDisplay, "Highscores", JOptionPane.INFORMATION_MESSAGE);
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

    /** PlayerListener is passed as a parameter of the input actions to move the player. */
    class PlayerListener extends AbstractAction {

        private int i;

        public PlayerListener(int i) {
            this.i = i;
        }

        private boolean sound = true;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!paused && !level.collides(player, i)) {
                player.move(i);
                if(sound) {
                    playSound("data/sounds/move.wav");
                    sound = false;
                }
                else
                    sound = true;
            }
        }
    }


    class FrameListener implements ActionListener {

        /** If the game is not paused and the next move the dragon will make does not collide with any of the walls on the map,
         * the dragon moves, otherwise if level collides with dragons, dragon changes its direction then moves.
         *
         * If player completes the level successfully, levelNum variable increases and new level gets loaded.
         *
         * Else if, dragon eats the player, the game is over and an input dialog is displayed and gets the name of the
         * user and saves it to the database.
         *
         * Finally, calls the 'repaint' method.
         */
        @Override
        public void actionPerformed(ActionEvent ae) {

            if (!paused && !level.collides(dragon)) {
                dragon.move();
            } else if(level.collides(dragon)) {
                dragon.changeDirection();
                dragon.move();
            }


            if (level.isOver(player, door)) {
                levelNum++;
                restart();
            } else if(dragon.collides(player)) {
                String nameToSave = JOptionPane.showInputDialog("Your score is: " + (levelNum-1) + "\nType your name: ");
                JOptionPane.showMessageDialog(MainPanel.this, "Since there's no connection to a database, " +
                        "we cannot save your name.");
                /*
                if(nameToSave != null)
                database.insertData(nameToSave, levelNum-1);*/
                levelNum = 0;
                startNewGame();
            }
            repaint();
        }

    }
}
