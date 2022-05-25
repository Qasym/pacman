package com.Game.intelligence.characters;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.intelligence.MonsterBrain;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

/*
* Tilly is the Inky from the original game
* To locate Tilly's target, we first start by
* selecting the position two tiles in front of Pacman
* in his current direction of travel, similar to Lilly's targeting method.
* From there, imagine drawing a vector from Billy's position to this tile,
* and then doubling the length of the vector. The tile that this new,
* extended vector ends on will be Tilly's actual target.
* */
public class Tilly extends MonsterBrain {
    public Tilly(Handler handler, int scatterPosX, int scatterPosY) {
        super(handler, scatterPosX, scatterPosY);
    }

    @Override
    public void setChaseCoordinates() {
        int billyX, billyY; // position of Billy
        int pacmanX, pacmanY; // position of a tile in front of Pacman

        billyX = handler.getBilly().getCollisionBox().x;
        billyY = handler.getBilly().getCollisionBox().y;

        if (handler.getPacman().getCurrentDirection() == Pacman.UP) { // if pacman moves upwards
            pacmanX = handler.getPacman().getCollisionBox().x;
            pacmanY = handler.getPacman().getCollisionBox().y - Tile.HEIGHT * 2;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.DOWN) { // if pacman moves downwards
            pacmanX = handler.getPacman().getCollisionBox().x;
            pacmanY = handler.getPacman().getCollisionBox().y + Tile.HEIGHT * 2;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.LEFT) { // if pacman moves leftwards
            pacmanX = handler.getPacman().getCollisionBox().x - Tile.WIDTH * 2;
            pacmanY = handler.getPacman().getCollisionBox().y;
        } else if (handler.getPacman().getCurrentDirection() == Pacman.RIGHT) { // if pacman moves rightwards
            pacmanX = handler.getPacman().getCollisionBox().x + Tile.WIDTH * 2;
            pacmanY = handler.getPacman().getCollisionBox().y;
        } else {
            throw new RuntimeException("Unknown direction of Pacman!");
        }
        chasePosX = billyX + 2 * (pacmanX - billyX);
        chasePosY = billyY + 2 * (pacmanY - billyY);
    }
}
