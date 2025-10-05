package main;

import objects.MagicalBox;

public class CollisionHandler {
    GamePanel gamePanel;

    public CollisionHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int leftX = entity.getX() + entity.collisionArea.x;
        int rightX = entity.getX() + entity.collisionArea.x + entity.collisionArea.width;
        int topY = entity.getY() + entity.collisionArea.y;
        int bottomY = entity.getY() + entity.collisionArea.y + entity.collisionArea.height;
        int leftCol = leftX / 48;
        int rightCol = rightX / 48;
        int topRow = topY / 48;
        int bottomRow = bottomY / 48;
        int tile1;
        int tile2;
        switch (entity.getCurrentState()) {
            case UP:
                topRow = (topY - entity.speed) /48;
                tile1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tile2 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
                if (gamePanel.tileManager.tileTypes[tile1].collision || gamePanel.tileManager.tileTypes[tile2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case DOWN:
                bottomRow = (bottomY + entity.speed) / 48;
                tile1 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
                tile2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];
                if (gamePanel.tileManager.tileTypes[tile1].collision || gamePanel.tileManager.tileTypes[tile2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case RIGHT:
                rightCol = (rightX + entity.speed) / 48;
                tile1 = gamePanel.tileManager.mapTileNum[rightCol][topRow];
                tile2 = gamePanel.tileManager.mapTileNum[rightCol][bottomRow];
                if (gamePanel.tileManager.tileTypes[tile1].collision || gamePanel.tileManager.tileTypes[tile2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case LEFT:
                leftCol = (leftX - entity.speed) / 48;
                tile1 = gamePanel.tileManager.mapTileNum[leftCol][topRow];
                tile2 = gamePanel.tileManager.mapTileNum[leftCol][bottomRow];
                if (gamePanel.tileManager.tileTypes[tile1].collision || gamePanel.tileManager.tileTypes[tile2].collision) {

                    entity.collisionOn = true;
                }
                break;
            case IDLE:
                break;
        }
    }

    //check if player is hitting any enemy and return the index of the enemy to process the reaction
    public int checkEnemyCollision(Entity entity, Enemy[] enemy) {
        int index = 100;
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                entity.collisionArea.x=entity.getX()+entity.collisionArea.x;
                entity.collisionArea.y=entity.getY()+entity.collisionArea.y;

                enemy[i].collisionArea.x=enemy[i].getX()+enemy[i].collisionArea.x;
                enemy[i].collisionArea.y=enemy[i].getY()+enemy[i].collisionArea.y;
                switch (entity.getCurrentState()) {
                    case UP:
                        entity.collisionArea.y -= entity.speed;
                        if (entity.collisionArea.intersects(enemy[i].collisionArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case DOWN:
                        entity.collisionArea.y += entity.speed;
                        if (entity.collisionArea.intersects(enemy[i].collisionArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case LEFT:
                        entity.collisionArea.x -= entity.speed;
                        if (entity.collisionArea.intersects(enemy[i].collisionArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case RIGHT:
                        entity.collisionArea.x += entity.speed;
                        if (entity.collisionArea.intersects(enemy[i].collisionArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }
                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                enemy[i].collisionArea.x = enemy[i].collisionAreaDefaultX;
                enemy[i].collisionArea.y = enemy[i].collisionAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayerCollision(Entity entity){
        entity.collisionArea.x=entity.getX()+entity.collisionArea.x;
        entity.collisionArea.y=entity.getY()+entity.collisionArea.y;

        gamePanel.player.collisionArea.x=gamePanel.player.getX()+gamePanel.player.collisionArea.x;
        gamePanel.player.collisionArea.y=gamePanel.player.getY()+gamePanel.player.collisionArea.y;
        switch (entity.getCurrentState()) {
            case UP:
                entity.collisionArea.y -= entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collisionOn = true;
                    gamePanel.player.setHp(gamePanel.player.getHp()-1);
                    entity.setHp(0);
                }
                break;
            case DOWN:
                entity.collisionArea.y += entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collisionOn = true;
                    gamePanel.player.setHp(gamePanel.player.getHp()-1);
                    entity.setHp(0);
                }
                break;
            case LEFT:
                entity.collisionArea.x -= entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collisionOn = true;
                    gamePanel.player.setHp(gamePanel.player.getHp()-1);
                    entity.setHp(0);
                }
                break;
            case RIGHT:
                entity.collisionArea.x += entity.speed;
                if (entity.collisionArea.intersects(gamePanel.player.collisionArea)) {
                    entity.collisionOn = true;
                    gamePanel.player.setHp(gamePanel.player.getHp()-1);
                    System.out.println(gamePanel.player.getHp());
                    entity.setHp(0);
                }
                break;
        }
        entity.collisionArea.x = entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.collisionAreaDefaultY;
        gamePanel.player.collisionArea.x = gamePanel.player.collisionAreaDefaultX;
        gamePanel.player.collisionArea.y = gamePanel.player.collisionAreaDefaultY;
    }

    public int checkObjectCollision(Player player,MagicalBox[] magicalBox){
        int index=100;
        for(int i=0;i<magicalBox.length;i++){
            if(magicalBox[i]!=null){
                //get player collision area position
                player.collisionArea.x=player.getX()+player.collisionArea.x;
                player.collisionArea.y=player.getY()+player.collisionArea.y;
                //get object collision area position
                magicalBox[i].collisionArea.x=magicalBox[i].getX()+magicalBox[i].collisionArea.x;
                magicalBox[i].collisionArea.y=magicalBox[i].getY()+magicalBox[i].collisionArea.y;

                switch (player.getCurrentState()){
                    case UP:
                        player.collisionArea.y -= player.speed;
                        if(player.collisionArea.intersects(magicalBox[i].collisionArea)){
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case DOWN:
                        player.collisionArea.y += player.speed;
                        if(player.collisionArea.intersects(magicalBox[i].collisionArea)){
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case RIGHT:
                        player.collisionArea.x += player.speed;
                        if(player.collisionArea.intersects(magicalBox[i].collisionArea)){
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                    case LEFT:
                        player.collisionArea.x -= player.speed;
                        if(player.collisionArea.intersects(magicalBox[i].collisionArea)){
                            player.collisionOn = true;
                            index = i;
                        }
                        break;
                }

                player.collisionArea.x=player.collisionAreaDefaultX;
                player.collisionArea.y=player.collisionAreaDefaultY;

                magicalBox[i].collisionArea.x=0;
                magicalBox[i].collisionArea.y=0;
            }
        }

        return index;
    }
    
}
