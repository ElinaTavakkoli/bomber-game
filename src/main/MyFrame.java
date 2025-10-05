package main;

import javax.swing.*;

public class MyFrame extends JFrame {
    public GamePanel panel;
    public MyFrame(GamePanel gamePanel){
        this.setTitle("Bomber Friends");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel=gamePanel;
        panel.start();
        this.setContentPane(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
