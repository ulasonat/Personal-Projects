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
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class MainFrame extends JFrame {

    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private final int INITIAL_BOARD_SIZE = 5; // initially, the game will start in 5x5 format.
    private JFileChooser fileChooser;

    /** Creates the screen, sets the layout, initializes the 'mainPanel' and 'fileChooser' objects.
     * Adds the mainPanel and the turnLabel to its layout, initializes the 'menuBar' object, and sets it to be the menubar.
       Adds action listeners, mnemonics and accelerators to the menu items. */
    public MainFrame() {
        super("Hunting Game");
        setLayout(new BorderLayout());
        mainPanel = new MainPanel(INITIAL_BOARD_SIZE);
        fileChooser = new JFileChooser("icons\\");

        add(mainPanel, BorderLayout.CENTER);
        add(mainPanel.getTurnLabel(), BorderLayout.SOUTH);
        mainPanel.addNewGameStarter(this);

        menuBar = createJMenuBar();
        setJMenuBar(menuBar);

        addActionListenersToMenuItems();
        setMnemonicsAndAccelerators();

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    /** Creates a menuBar and adds menus to it, and adds menuItems to menus.
     * @return the menuBar object that will be the menuBar of the screen.
     */
    public JMenuBar createJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");

        JMenu newGameMenu = new JMenu("New Game");
        menu.add(newGameMenu);

        JMenu uploadMenu = new JMenu("Upload Icon");
        menu.add(uploadMenu);

        JMenuItem fugitiveMenuItem = new JMenuItem("Fugitive...");
        JMenuItem hunterMenuItem = new JMenuItem("Hunter...");
        uploadMenu.add(fugitiveMenuItem);
        uploadMenu.add(hunterMenuItem);

        JMenuItem exit = new JMenuItem("Exit");
        menu.add(exit);


        jMenuBar.add(menu);

        return jMenuBar;
    }


        /** Adds mnemonics and accelerators to the menu items. */
        public void setMnemonicsAndAccelerators() {
        JMenu newGameMenu = (JMenu) menuBar.getMenu(0).getMenuComponent(0);
        JMenu uploadMenu = (JMenu) menuBar.getMenu(0).getMenuComponent(1);
        JMenuItem exitItem = menuBar.getMenu(0).getItem(2);

        newGameMenu.setMnemonic(KeyEvent.VK_N);
        uploadMenu.setMnemonic(KeyEvent.VK_U);

        newGameMenu.getItem(0).setMnemonic(KeyEvent.VK_3);
        newGameMenu.getItem(1).setMnemonic(KeyEvent.VK_5);
        newGameMenu.getItem(2).setMnemonic(KeyEvent.VK_7);

        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

    }

        /** 1) Adds the menuItems with name of 3x3, 5x5, 7x7 to the newGameMenu, and also adds an action listener
         * to each of them which calls the 'startNewGame' method.
         *
         *  2) Adds a file filter to 'fileChooser' object which provides that only the files with the extension 'png'
         *  can be selected from the menu to change the icons of the characters, and adds an action listeners to the menuItems
         *  to actually change the icon of the characters by calling setFugitiveIcon and setHunterIcon methods.
         *
         *  3) Finally adds an action listener to exit button, when the user clicks that button or reaches it with the help of
         *  a mnemonics or an accelerator, a confirmation message will be displayed, in case the user confirms it,
         *  the game will end.
         * */
    public void addActionListenersToMenuItems() {
        int[] sizes = new int[]{3,5,7};
        JMenu newGameMenu = (JMenu)menuBar.getMenu(0).getMenuComponent(0);

        // New Game
        for (int n : sizes) {
            JMenuItem sizeItem = new JMenuItem(n + "x" + n);
            newGameMenu.add(sizeItem);
            sizeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startNewGame(n);
                }
            });
        }

        // Upload Icon
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {

                if(f.isDirectory())
                    return true;

               String name = f.getName();
               String extension = "";

                int i = name.lastIndexOf('.');
                if(i>0) {
                    extension = name.substring(i+1);
                }

                if(extension.equals("png"))
                    return true;
                return false;
            }

            @Override
            public String getDescription() {
                return "Icons to represent the characters (*.png)";
            }
        });

        JMenu uploadMenu = (JMenu) menuBar.getMenu(0).getMenuComponent(1);
        JMenuItem fugitive = uploadMenu.getItem(0);
        JMenuItem hunter = uploadMenu.getItem(1);

        fugitive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    File newIcon = fileChooser.getSelectedFile();
                    mainPanel.setFugitiveIcon(newIcon);
                }
            }
        });

        hunter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    File newIcon = fileChooser.getSelectedFile();
                    mainPanel.setHunterIcon(newIcon);
                }
            }
        });



        // Exit
        JMenuItem exit = menuBar.getMenu(0).getItem(2);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to quit?",
                        "Confirmation Message", JOptionPane.YES_NO_OPTION);
                if(action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

    }

    /** Starts a new game, this method can be called on Menu->NewGame
     * or it will be called when a game ends. (to automatically start a new game -requirement of the assignment- */
    public void startNewGame(int n) {
        remove(mainPanel);
        remove(mainPanel.getTurnLabel());
        mainPanel = new MainPanel(n);
        add(mainPanel, BorderLayout.CENTER);
        add(mainPanel.getTurnLabel(), BorderLayout.SOUTH);
        mainPanel.addNewGameStarter(this);
        pack();
    }

    /** Returns the INITIAL_BOARD_SIZE variable. */
    public int getINITIAL_BOARD_SIZE() {
        return INITIAL_BOARD_SIZE;
    }
}
