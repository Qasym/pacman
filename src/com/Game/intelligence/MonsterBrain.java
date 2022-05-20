package com.Game.intelligence;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

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
    private enum State {
        CHASE,
        SCATTER,
        FRIGHTENED,
        EATEN
    }
    private State currentState; // a variable to track the monster state

    protected Handler handler;

    protected enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    public Direction currentDirection;

    // variables necessary for the monster intelligence
    protected final int scatterPosX, scatterPosY; // direct monster in SCATTER state
    private int chasePosX, chasePosY; // direct monster in CHASE state

    // time tracking for switching the phases
    public final int SCATTER_TIME = 7, CHASE_TIME = 20, FRIGHTENED_TIME = 5;
    private long lastTime;
    private double delta;

    public MonsterBrain(Handler handler, int scatterPosX, int scatterPosY) {
        currentState = State.CHASE;
        this.scatterPosY = scatterPosY;
        this.scatterPosX = scatterPosX;
        setChaseCoordinates(handler.getPacman());
        lastTime = System.nanoTime();
        currentDirection = Direction.DOWN;
    }

    /*
    * This method is needed for monster to decide
    * its next move
    *
    * This method is abstract since each monster has unique
    * decisions to make
    * */
    public abstract void decision(Monster monster);

    /*
    * In CHASE state, we assign chasePosX and chasePosY every tick
    * since pacman constantly moves, but since each monster
    * has unique target positions, we leave this method abstract
    * */
    public abstract void setChaseCoordinates(@NotNull Entity pacman);

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

    // checks if a given monster is in tunnel or not
    // if it is in tunnel it means we can't change direction
    public boolean isInTunnel(@NotNull Monster monster) {
        if (currentDirection == Direction.DOWN) {
            
        } else if (currentDirection == Direction.UP) {

        } else if (currentDirection == Direction.LEFT) {

        } else {

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
        currentDirection = Direction.UP;
    }
    public void setDirectionDown() {
        currentDirection = Direction.DOWN;
    }
    public void setDirectionLeft() {
        currentDirection = Direction.LEFT;
    }
    public void setDirectionRight() {
        currentDirection = Direction.RIGHT;
    }
}
