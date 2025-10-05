import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Panel extends JPanel implements ActionListener {
    World world = new World();
    Panel() {
        setBackground(Color.white);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W: {
                        World.player.move(Player.Direction.UP);
                        break;
                    }
                    case KeyEvent.VK_S: {
                        World.player.move(Player.Direction.DOWN);
                        break;
                    }
                    case KeyEvent.VK_A: {
                        World.player.move(Player.Direction.LEFT);
                        break;
                    }
                    case KeyEvent.VK_D: {
                        World.player.move(Player.Direction.RIGHT);
                        break;
                    }
                    case KeyEvent.VK_B: {
                        if (World.player.getBombCONTER() > 0) {
                            World.player.blowUp();
                        }
                        break;
                    }
                    //  default:
                }
                super.keyPressed(e);
            }
        });
        setFocusable(true);

        Timer timer = new Timer(10, this);
        timer.start();
        Timer timer1 = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (Enemy enemy : World.hSkeletons)
                    enemy.blowUp();
            }
        });
        timer1.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int hp=0; hp< world.player.getHP(); hp++)
            g.drawImage(world.hp, 15+(hp*50), 710, 50, 50, null);
        world.player.draw(g, null);
        world.drawWall(g);
        if (World.enemies.size() > 0)
            for (Enemy enemy : world.enemies) {
                enemy.draw(g, enemy.getIDLE());
                if (World.player.getBombs().size() > 0)
                    for (Bomb bomb : World.player.getBombs())
                        bomb.draw(g, null);

                for (Enemy hSkeleton : World.hSkeletons)
                    for (Bomb bomb1 : hSkeleton.getBombs())
                        bomb1.draw(g, null);
                for (LuckyBox luckyBox : World.luckyBoxes)
                    luckyBox.draw(g, luckyBox.getImage());
                repaint();
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        world.moveEnemies();
        world.player.AABB();
        if(world.player.getHP()==0 || world.enemies.size()==0)
            world.player.setGameOver(true);
    }


}
