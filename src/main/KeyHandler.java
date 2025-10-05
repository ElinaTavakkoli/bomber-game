package main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean setBomb;

    GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //game input
        if (gamePanel.gameState == gamePanel.playState || gamePanel.gameState == gamePanel.pauseState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_ENTER) {
                setBomb = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gamePanel.gameState == gamePanel.pauseState) {
                    gamePanel.gameState = gamePanel.playState;
                } else if (gamePanel.gameState == gamePanel.playState) {
                    gamePanel.gameState = gamePanel.pauseState;
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.titleState;
            }
        }
        //menuInput
        if (gamePanel.gameState == gamePanel.titleState) {
            code = e.getKeyCode();
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gamePanel.menu.input--;
                if (gamePanel.menu.input < 0) {
                    gamePanel.menu.input = 2;
                }
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gamePanel.menu.input++;
                if (gamePanel.menu.input > 2) {
                    gamePanel.menu.input = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.menu.input == 0) {
                    startNewGame();
                }
                if (gamePanel.menu.input == 1) {
                    gamePanel.gameState = gamePanel.settingState;
                }
                if (gamePanel.menu.input == 2) {
                    System.exit(0);
                }
            }

        }
        //setting input
        if (gamePanel.gameState == gamePanel.settingState) {
            getSettingInput(code);
        }

        //win input/lose input
        if (gamePanel.gameState == gamePanel.loseState||gamePanel.gameState == gamePanel.winState) {
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_ENTER||code == KeyEvent.VK_SPACE) {
                gamePanel.gameState= gamePanel.titleState;
                GamePanel newGamePanel = new GamePanel();
                Main.myFrame.dispose();
                Main.myFrame = new MyFrame(newGamePanel);
            }

        }
    }


    public void startNewGame() {
        GamePanel newGamePanel = new GamePanel();
        setNewGameVariables(newGamePanel);
        Main.myFrame.dispose();
        Main.myFrame = new MyFrame(newGamePanel);
    }

    public void setNewGameVariables(GamePanel gPanel) {
        gPanel.ghostsNum = gamePanel.ghostsNum;
        gPanel.simpleSkeletonsNum = gamePanel.simpleSkeletonsNum;
        gPanel.enemiesNum = gamePanel.enemiesNum;
        gPanel.gameState = gamePanel.playState;
        gPanel.hoodedSkeletonsNum = gamePanel.hoodedSkeletonsNum;
        gPanel.player.setHp(gamePanel.player.getHp());
        gPanel.player.radiusBooster = gamePanel.player.radiusBooster;
        gPanel.player.powerBooster = gamePanel.player.powerBooster;
    }

    public void getSettingInput(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.titleState;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gamePanel.menu.settingInput--;
            if (gamePanel.menu.settingInput < 0) {
                gamePanel.menu.settingInput = 5;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gamePanel.menu.settingInput++;
            if (gamePanel.menu.settingInput > 5) {
                gamePanel.menu.settingInput = 0;
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_PLUS) {
            checkSettingInputIncrease();
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_MINUS) {
            checkSettingInputDecrease();
        }
    }

    public void checkSettingInputIncrease() {
        if (gamePanel.menu.settingInput == 0) {
            gamePanel.simpleSkeletonsNum++;
            gamePanel.enemiesNum++;
        }
        if (gamePanel.menu.settingInput == 1) {
            gamePanel.hoodedSkeletonsNum++;
            gamePanel.enemiesNum++;
        }
        if (gamePanel.menu.settingInput == 2) {
            gamePanel.ghostsNum++;
            gamePanel.enemiesNum++;
        }
        if (gamePanel.menu.settingInput == 3) {
            gamePanel.player.powerBooster++;
        }
        if (gamePanel.menu.settingInput == 4) {
            gamePanel.player.radiusBooster++;
        }
        if (gamePanel.menu.settingInput == 5) {
            if (gamePanel.player.getHp() < gamePanel.player.getMaxHp())
                gamePanel.player.setHp(gamePanel.player.getHp() + 1);
        }
    }

    public void checkSettingInputDecrease() {
        if (gamePanel.menu.settingInput == 0) {
            if (gamePanel.simpleSkeletonsNum > 2) {
                gamePanel.simpleSkeletonsNum--;
                gamePanel.enemiesNum--;
            }
        }
        if (gamePanel.menu.settingInput == 1) {
            if (gamePanel.simpleSkeletonsNum > 1) {
                gamePanel.hoodedSkeletonsNum--;
                gamePanel.enemiesNum--;
            }
        }
        if (gamePanel.menu.settingInput == 2) {
            if (gamePanel.simpleSkeletonsNum > 3) {
                gamePanel.ghostsNum--;
                gamePanel.enemiesNum--;
            }
        }
        if (gamePanel.menu.settingInput == 3) {
            if (gamePanel.player.powerBooster > 1) {
                gamePanel.player.powerBooster--;
            }
        }
        if (gamePanel.menu.settingInput == 4) {
            if (gamePanel.player.radiusBooster > 1) {
                gamePanel.player.radiusBooster--;
            }
        }
        if (gamePanel.menu.settingInput == 5) {
            if (gamePanel.player.getHp() > 1)
                gamePanel.player.setHp(gamePanel.player.getHp() - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        if (gamePanel.gameState == gamePanel.playState || gamePanel.gameState == gamePanel.pauseState) {
            if (code == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = false;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = false;
            }
        }
    }


    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isBombed() {
        return setBomb;
    }


}
