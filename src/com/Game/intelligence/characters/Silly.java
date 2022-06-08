package com.Game.intelligence.characters;

import com.Game.entity.Entity;
import com.Game.intelligence.MonsterBrain;
import com.Game.tile.Tile;
import com.Game.utils.Handler;

/*
* The unique feature of Silly's targeting is that it has two
* separate modes which he constantly switches back and forth
* between, based on his proximity to Pac-Man. Whenever Silly
* needs to determine his target tile, he first calculates his
* distance from Pac-Man. If he is farther than eight tiles
* away, his targeting is identical to Billy's, using Pac-Man's
* current tile as his target. However, as soon as his distance
* to Pac-Man becomes less than eight tiles, Silly's target is
* set to the same tile as his fixed one in Scatter mode, just
* outside the bottom-left corner of the maze.
*
* I am going to use Manhattan distance for this, since it is
* not specified how 8 tiles are calculated
* */
public class Silly extends MonsterBrain {
    private long lastTime;
    private double delta;

    private boolean isFirstTime = true; // flag to properly initialize Silly's states

    public Silly(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
        lastTime = System.currentTimeMillis();
        setName("Silly");
    }

    // When 15 seconds pass, we release the ghost
    @Override
    public void activateMonster() {
        delta += (System.currentTimeMillis() - lastTime) / 1000.0;
        lastTime = System.currentTimeMillis();
        if (delta < 15) {
            this.monster.setSpeed(0);
        } else {
            if (!handler.getPacman().hasSpeedBuff()) {
                this.monster.resetSpeed();
            } else {
                this.monster.slowMeDown();
            }
            if (isFirstTime) {
                isFirstTime = false;
                setChaseState();
            }
        }
    }

    @Override
    public void setChaseCoordinates() {
        int horizontal = (handler.getPacman().getCollisionBox().x - this.monster.getCollisionBox().x) / Tile.WIDTH;
        int vertical = (handler.getPacman().getCollisionBox().y - this.monster.getCollisionBox().y) / Tile.HEIGHT;
        if (horizontal + vertical < 8) {
            chasePosX = handler.getPacman().getCollisionBox().x;
            chasePosY = handler.getPacman().getCollisionBox().y;
        } else {
            chasePosX = scatterPosX;
            chasePosY = scatterPosY;
        }
    }
}
