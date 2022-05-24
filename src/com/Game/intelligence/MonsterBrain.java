package com.Game.intelligence;

import com.Game.entity.Entity;
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
    public abstract void decide();

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
        delta += (now - lastTime) / 1e9; // count how many seconds passed
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

    /*
    * The algorithm here is simple
    * In order to check if monster can go to a certain direction,
    * without loss of generality let's say it's right,
    * we need to take top-right and bottom-right corners to see
    * if they are able to move rightwards, if either of the corners
    * collide with the tile to the right, it means monster can't go right
    *
    * x1, y1 - are the coordinates of the first corner
    * x2, y2 - are the coordinates of the second corner
    *
    * We also have to take into account that monsters can't reverse their direction
    * For that reason some directions despite them being collision free
    * have to be ignored
    * */
    public void setAvailableDirections() {
        int x1, y1, x2, y2;
        x1 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH + monster.getSpeed()) / Tile.TILE_WIDTH; // top-right x
        y1 = monster.getCollisionBox().y / Tile.TILE_WIDTH; // top-right y
        x2 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH + monster.getSpeed()) / Tile.TILE_WIDTH; // bottom-right x
        y2 = (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT) / Tile.TILE_WIDTH; // bottom-right y
        availableDirections[RIGHT] = !monster.collidesWithTile(x1, y1) && !monster.collidesWithTile(x2, y2) && currentDirection != LEFT;

        x1 = (monster.getCollisionBox().x - monster.getSpeed()) / Tile.TILE_WIDTH; // top-left x
        y1 = monster.getCollisionBox().y / Tile.TILE_WIDTH; // top-left y
        x2 = (monster.getCollisionBox().x - monster.getSpeed()) / Tile.TILE_WIDTH; // bottom-left x
        y2 = (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT) / Tile.TILE_WIDTH; // bottom-left y
        availableDirections[LEFT] = !monster.collidesWithTile(x1, y1) && !monster.collidesWithTile(x2, y2) && currentDirection != RIGHT;

        x1 = monster.getCollisionBox().x / Tile.TILE_HEIGHT; // top-left x
        y1 = (monster.getCollisionBox().y - monster.getSpeed()) / Tile.TILE_HEIGHT; // top-left y
        x2 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH) / Tile.TILE_HEIGHT; // top-right x
        y2 = (monster.getCollisionBox().y - monster.getSpeed()) / Tile.TILE_HEIGHT; // top-right y
        availableDirections[UP] = !monster.collidesWithTile(x1, y1) && !monster.collidesWithTile(x2, y2) && currentDirection != DOWN;

        x1 = monster.getCollisionBox().x / Tile.TILE_HEIGHT; // bottom-left x
        y1 = (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT + monster.getSpeed()) / Tile.TILE_HEIGHT; // bottom-left y
        x2 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH) / Tile.TILE_HEIGHT; // bottom-right x
        y2 = (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT + monster.getSpeed()) / Tile.TILE_HEIGHT; // bottom-right y
        availableDirections[DOWN] = !monster.collidesWithTile(x1, y1) && !monster.collidesWithTile(x2, y2) && currentDirection != UP;
    }
    /*
    * returns the manhattan distance from the current position of a monster
    * to a given posit
}ion
    * */
    public double getDistance(int x1, int y1, int x2, int y2) {
        int x, y;
        x = Math.abs(x1 - x2);
        y = Math.abs(y1 - y2);
        return Math.sqrt(x * x + y * y);
    }

    /*
    * When there are several directions available, this method will calculate
    * the best direction by moving to which monsters will get the shortest
    * manhattan distance to the target
    * */
    public int calculateBestDirection() {
        double[] values = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}; // since we have 4 directions
        if (currentState == State.CHASE) {
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(), chasePosX, chasePosY);
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(), chasePosX, chasePosY);
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y, chasePosX, chasePosY);
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y, chasePosX, chasePosY);
            }
        } else if (currentState == State.SCATTER) {
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(), scatterPosX, scatterPosY);
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(), scatterPosX, scatterPosY);
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y, scatterPosX, scatterPosY);
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y, scatterPosX, scatterPosY);
            }
        } else if (currentState == State.FRIGHTENED) { // monster moves randomly in the frightened state
            return new Random().nextInt(4);
        } else {
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(), (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y, (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y, (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
        }

        // regardless of state, the best direction is the one with the shortest value
        int i = 0, minIdx = -1;
        double minValue = Double.MAX_VALUE;
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
