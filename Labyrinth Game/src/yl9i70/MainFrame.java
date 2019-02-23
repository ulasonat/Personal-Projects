package yl9i70;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    private MainPanel mainPanel;

    public final static int WIDTH;
    public final static int HEIGHT;

    // Initializing the variables in the initialization static block.
    static {
        WIDTH = 680;
        HEIGHT = 680;
    }

    /** Creates the frame object, sets the necessary features.*/
    public MainFrame() {
        super("Labyrinth Game");
        setLayout(new BorderLayout());

        // Initializing the menu bar and adding it.
        setJMenuBar(createJMenuBar());

        // Initializing the mainPanel and adding it.
        mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        add(mainPanel.getTimeLabel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setSize(WIDTH,HEIGHT);
        setVisible(true);
    }

    /** Creates a JMenuBar and returns it.*/
    private JMenuBar createJMenuBar() {
        JMenuBar tmpMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        tmpMenuBar.add(menu);
        JMenuItem highScoreTable = new JMenuItem("High Score Table");
        JMenuItem restartGame = new JMenuItem("Restart Game");
        menu.add(highScoreTable);
        menu.add(restartGame);

        highScoreTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK));

        highScoreTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //mainPanel.outputData();
                JOptionPane.showMessageDialog(MainFrame.this, "Since there's no connection to a database, " +
                        "the high scores cannot be displayed.");
            }
        });

        restartGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));

        restartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.startNewGame();
            }
        });

        return tmpMenuBar;
    }
}
