package com.Game.intelligence;

import com.Game.entity.moving.Monster;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/*
* This is the main class for the intelligence of the Monsters
* We keep this method abstract since each Monster has its own
* unique brain, those abstract functions are implemented in
* child classes
*
* Monsters attack in waves, they attack, retreat, and attack again
* To simulate that reason states are implemented
*
* Time tracking is also implemented since we need to switch
* states from time to time
*
* Monsters can't turn back, for that reason we also track monsters'
* direction
* */
public abstract class MonsterBrain {
    /*
    * State enum is needed to track the state of a monster
    * CHASE state is when monster actively chases pacman
    * SCATTER state is when monster goes to the scatter position
    * FRIGHTENED state is when monster moves randomly
    * EATEN state is when pacman eats monster, and monster heads to its spawn point
    *
    * Scatter position is a position on the map to which monsters
    * head when they are in SCATTER state, this is needed to
    * simulate wave attacks (not constant chasing of pacman)
    * */
    protected enum State {
        CHASE,
        SCATTER,
        FRIGHTENED,
        EATEN
    }
    protected State currentState; // a variable to track the monster state

    protected Handler handler;

    protected int currentDirection;

    public final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    protected boolean[] availableDirections = new boolean[4];

    // variables necessary for the monster intelligence
    protected final int scatterPosX, scatterPosY; // direct monster in SCATTER state
    protected int chasePosX, chasePosY; // direct monster in CHASE state

    // time tracking for switching the phases
    public final int SCATTER_TIME = 7, CHASE_TIME = 20, FRIGHTENED_TIME = 5;
    private long lastTime;
    private double delta;

    // Monster reference
    protected Monster monster = null;

    public MonsterBrain(Handler handler, int scatterPosX, int scatterPosY) {
        currentState = State.CHASE;
        this.scatterPosY = scatterPosY;
        this.scatterPosX = scatterPosX;
        lastTime = System.nanoTime();
        currentDirection = DOWN;
        this.handler = handler;
    }

    /*
    * This method is needed for monster to decide
    * its next move
    *
    * This method is abstract since each monster has unique
    * decisions to make
    * */
    public abstract void decision();

    /*
    * In CHASE state, we assign chasePosX and chasePosY every tick
    * since pacman constantly moves, but since each monster
    * has unique target positions, we leave this method abstract
    * */
    public abstract void setChaseCoordinates();

    /*
    * This method has to be called every tick so that we can
    * properly track when we have to switch out states
    * */
    public void trackState(Monster monster) {
        long now = System.nanoTime();
        delta += (lastTime - now) / 1e9; // count how many seconds passed
        lastTime = now;

        if (currentState == State.CHASE) {
            if (delta > (double) CHASE_TIME) {
                delta = 0;
                setScatterState();
            }
        } else if (currentState == State.SCATTER) {
            if (delta > (double) SCATTER_TIME) {
                delta = 0;
                setChaseState();
            }
        } else if (currentState == State.FRIGHTENED) {
            if (delta > (double) FRIGHTENED_TIME) {
                delta = 0;
                setChaseState();
            }
        } else { // EATEN state
            // we constantly set delta to zero, state changes when monster reaches spawn point
            delta = 0;
            if (isAtSpawn(monster)) {
                setChaseState();
            }
        }
    }

    // checks if a given monster is at spawn
    public boolean isAtSpawn(@NotNull Monster monster) {
        return monster.getX() == monster.getSpawnPosX() && monster.getY() == monster.getSpawnPosY();
    }

    public void setAvailableDirections() {
        availableDirections[RIGHT] = !monster.collidesWithTile((int) (monster.getCenterX() + Tile.TILE_WIDTH) / Tile.TILE_WIDTH,
                                                               (int) monster.getCenterY() / Tile.TILE_HEIGHT);
        availableDirections[UP] = !monster.collidesWithTile((int) monster.getCenterX() / Tile.TILE_WIDTH,
                                                            (int) (monster.getCenterY() - Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT);
        availableDirections[LEFT] = !monster.collidesWithTile((int) (monster.getCenterX() - Tile.TILE_WIDTH) / Tile.TILE_WIDTH,
                                                              (int) monster.getCenterY() / Tile.TILE_HEIGHT);
        availableDirections[DOWN] = !monster.collidesWithTile((int) monster.getCenterX() / Tile.TILE_WIDTH,
                                                              (int) (monster.getCenterY() + Tile.TILE_HEIGHT) / Tile.TILE_HEIGHT);
    }
    /*
    * returns the manhattan distance from the current position of a monster
    * to a given posit
}ion
    * */
    public int getDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /*
    * When there are several directions available, this method will calculate
    * the best direction by moving to which monsters will get the shortest
    * manhattan distance to the target
    * */
    public int calculateBestDirection() {
        int[] values = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}; // since we have 4 directions
        if (currentState == State.CHASE) {
            if (availableDirections[UP]) {
                values[UP] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() - monster.speed), chasePosX, chasePosY);
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() + monster.speed), chasePosX, chasePosY);
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance((int) (monster.getCenterX() - monster.speed), (int) monster.getCenterY(), chasePosX, chasePosY);
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance((int) (monster.getCenterX() + monster.speed), (int) monster.getCenterY(), chasePosX, chasePosY);
            }
        } else if (currentState == State.SCATTER) {
            if (availableDirections[UP]) {
                values[UP] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() - monster.speed), scatterPosX, scatterPosY);
            }
            if (availableDirections[DOWN]) {
                values[UP] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() + monster.speed), scatterPosX, scatterPosY);
            }
            if (availableDirections[LEFT]) {
                values[UP] = getDistance((int) (monster.getCenterX() - monster.speed), (int) monster.getCenterY(), scatterPosX, scatterPosY);
            }
            if (availableDirections[RIGHT]) {
                values[UP] = getDistance((int) (monster.getCenterX() + monster.speed), (int) monster.getCenterY(), scatterPosX, scatterPosY);
            }
        } else if (currentState == State.FRIGHTENED) { // monster moves randomly in frightened state
            return new Random().nextInt(4);
        } else {
            if (availableDirections[UP]) {
                values[UP] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() - monster.speed), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[DOWN]) {
                values[UP] = getDistance((int) monster.getCenterX(), (int) (monster.getCenterY() + monster.speed), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[LEFT]) {
                values[UP] = getDistance((int) (monster.getCenterX() - monster.speed), (int) monster.getCenterY(), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[RIGHT]) {
                values[UP] = getDistance((int) (monster.getCenterX() + monster.speed), (int) monster.getCenterY(), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
        }

        // regardless of state, the best direction is the one with the shortest value
        int i = 0, minIdx = 0, minValue = values[0];
        while (i < 4) {
            if (availableDirections[i] && values[i] < minValue) {
                minValue = values[i];
                minIdx = i;
            }
            i++;
        }
        return minIdx;
    }

    public void setMonster(Monster monster_) {
        if (monster == null) {
            monster = monster_;
        }
    }

    public void setChaseState() {
        currentState = State.CHASE;
    }

    public void setScatterState() {
        currentState = State.SCATTER;
    }

    public void setFrightenedState() {
        currentState = State.FRIGHTENED;
    }

    public void setEatenState() {
        currentState = State.EATEN;
    }

    public void setDirectionUp() {
        currentDirection = UP;
    }
    public void setDirectionDown() {
        currentDirection = DOWN;
    }
    public void setDirectionLeft() {
        currentDirection = LEFT;
    }
    public void setDirectionRight() {
        currentDirection = RIGHT;
    }
    public int getCurrentDirection() {
        return currentDirection;
    }
}
