import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {
    static JFrame window;

    public Main(JFrame window, JPanel panel) {
        window.setPreferredSize(new Dimension(1120, 800));
        window.setVisible(false);
        window.setContentPane(panel);
        window.setLayout(null);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        //Panel gamePanel = new Panel();
        //window.add(gamePanel);
        window = new JFrame("Bomber Game");
        entryPanel();
        World.setHardWall();
        World.setAmazingWall();
        World.setNormalWall();
        World.setEnemies();
    }

    static void entryPanel() {
        //display first panel
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(null);
        entryPanel.setBackground(Color.lightGray);

        JLabel label = new JLabel("Bomber Friend");
        label.setBounds(380, 160, 500, 80);
        label.setFont(new Font("Serif", Font.BOLD, 60));
        label.setLayout(null);

        JButton startGame = new JButton("Start Game");
        JButton setting = new JButton("Setting");
        startGame.setBackground(Color.pink);
        setting.setBackground(Color.pink);
        startGame.setBounds(450, 330, 200, 45);
        setting.setBounds(450, 380, 200, 45);
        entryPanel.add(label);
        entryPanel.add(startGame);
        entryPanel.add(setting);
        new Main(window, entryPanel);

        startGame.addActionListener(e -> {
            Panel gamePanel = new Panel();
            new Main(window, gamePanel);
            entryPanel.setVisible(false);

        });

        setting.addActionListener(e -> { //sign up
            entryPanel.setVisible(false);
            try {
                setting();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    static void setting() throws IOException {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);

        BufferedImage pic = ImageIO.read((new File("C:\\Users\\Lenovo\\Downloads\\assets\\assets\\images\\bomb3.png")));

        JLabel picLabel = new JLabel(new ImageIcon(pic));
        picLabel.setBounds(600, 250, 500, 500);
        picLabel.setLayout(null);
        panel.add(picLabel);

        JLabel setting = new JLabel("Setting");
        setting.setBounds(180, 65, 150, 60);
        setting.setFont(new Font("Serif", Font.BOLD, 40));
        setting.setLayout(null);
        panel.add(setting);

        JLabel power = new JLabel("Bomb Power:");
        power.setBounds(110, 137, 150, 40);
        power.setFont(new Font("Serif", Font.BOLD, 15));
        power.setLayout(null);
        panel.add(power);

        JTextField tf1 = new JTextField(String.valueOf(World.player.getBombsPower()));
        tf1.setBounds(215, 145, 145, 30);
        panel.add(tf1);

        JLabel radius = new JLabel("Bomb Radius:");
        radius.setBounds(110, 190, 150, 40);
        radius.setFont(new Font("Serif", Font.BOLD, 15));
        radius.setLayout(null);
        panel.add(radius);

        JTextField tf2 = new JTextField(String.valueOf(World.player.getBombRadius()));
        tf2.setBounds(215, 198, 145, 30);
        panel.add(tf2);

        JLabel hp = new JLabel("HP:");
        hp.setBounds(110, 243, 150, 40);
        hp.setFont(new Font("Serif", Font.BOLD, 15));
        hp.setLayout(null);
        panel.add(hp);

        JTextField tf3 = new JTextField(String.valueOf(World.player.getHP()));
        tf3.setBounds(215, 251, 145, 30);
        panel.add(tf3);

        JLabel soul = new JLabel("Soul:");
        soul.setBounds(110, 296, 150, 40);
        soul.setFont(new Font("Serif", Font.BOLD, 15));
        soul.setLayout(null);
        panel.add(soul);

        JTextField tf4 = new JTextField(String.valueOf(World.getSoulCounter()));
        tf4.setBounds(215, 303, 145, 30);
        panel.add(tf4);

        JLabel skeleton = new JLabel("Skeleton:");
        skeleton.setBounds(110, 349, 150, 40);
        skeleton.setFont(new Font("Serif", Font.BOLD, 15));
        skeleton.setLayout(null);
        panel.add(skeleton);

        JTextField tf5 = new JTextField(String.valueOf(World.getSkeletonCounter()));
        tf5.setBounds(215, 356, 145, 30);
        panel.add(tf5);

        JLabel hskeleton = new JLabel("Hat Skeleton:");
        hskeleton.setBounds(110, 402, 150, 40);
        hskeleton.setFont(new Font("Serif", Font.BOLD, 15));
        hskeleton.setLayout(null);
        panel.add(hskeleton);

        JTextField tf6 = new JTextField(String.valueOf(World.gethSkeletonCounter()));
        tf6.setBounds(215, 409, 145, 30);
        panel.add(tf6);

        JButton submit = new JButton("Submit");
        submit.setBackground(Color.pink);
        submit.setBounds(130, 500, 200, 45);
        panel.add(submit);

        JButton back = new JButton("back");
        back.setBackground(Color.pink);
        back.setBounds(130, 560, 200, 45);
        panel.add(back);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                World.player.setBombsPower(Integer.valueOf(tf1.getText()));
                World.player.setBombRadius(Integer.valueOf(tf2.getText()));
                World.player.setHP(Integer.valueOf(tf3.getText()));
                World.setSoulCounter(Integer.valueOf(tf4.getText()));
                World.sethSkeletonCounter(Integer.valueOf(tf5.getText()));
                World.sethSkeletonCounter(Integer.valueOf(tf6.getText()));
                try {
                    setting();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entryPanel();
            }
        });
        new Main(window, panel);

    }
}
