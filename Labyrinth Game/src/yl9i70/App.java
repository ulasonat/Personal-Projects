package yl9i70;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            /** This method creates a MainFrame object which creates the application. */
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}