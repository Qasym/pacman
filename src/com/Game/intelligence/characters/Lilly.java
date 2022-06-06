package com.Game.intelligence.characters;

import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.intelligence.MonsterBrain;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

/*
* Lilly is the Pinky from the original game
* She tries not to follow Pacman, but to ambush him
* by targeting the tile that is in front of pacman
*
* Specifically, Lilly focuses on a tile that is
* 4 tiles in front of pacman
* */
public class Lilly extends MonsterBrain {
    // variables needed to track time properly
    private long lilly_lastTime;
    private double lilly_delta;

    private boolean isFirstTime = true; // flag to properly initialize Lilly's states

    public Lilly(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
        lilly_lastTime = System.currentTimeMillis();
        setName("Lilly");
    }

    // Lilly starts after 2 seconds, so that she doesn't collide with Billy
    @Override
    public void monsterStarter() {
        this.monster.setSpeed(0);

        long now = System.currentTimeMillis();
        lilly_delta += (now - lilly_lastTime) / 1000.0;
        lilly_lastTime = now;

        if (lilly_delta > 2) {
            this.monster.setSpeed(Monster.DEFAULT_SPEED);
            if (isFirstTime) {
                isFirstTime = false;
                setChaseState();
            }
        }
    }

    @Override
    public void setChaseCoordinates() {
        if (handler.getPacman().getCurrentDirection() == Pacman.UP) { // if pacman moves upwards
            chasePosX = handler.getPacman().getCollisionBox().x;
            chasePosY = handler.getPacman().getCollisionBox().y - Tile.HEIGHT * 4;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.DOWN) { // if pacman moves downwards
            chasePosX = handler.getPacman().getCollisionBox().x;
            chasePosY = handler.getPacman().getCollisionBox().y + Tile.HEIGHT * 4;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.LEFT) { // if pacman moves leftwards
            chasePosX = handler.getPacman().getCollisionBox().x - Tile.WIDTH * 4;
            chasePosY = handler.getPacman().getCollisionBox().y;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.RIGHT) { // if pacman moves rightwards
            chasePosX = handler.getPacman().getCollisionBox().x + Tile.WIDTH * 4;
            chasePosY = handler.getPacman().getCollisionBox().y;
        } else {
            throw new RuntimeException("Unknown direction of Pacman!");
        }
    }
}
