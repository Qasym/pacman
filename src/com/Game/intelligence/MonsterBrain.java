package com.Game.intelligence;

import com.Game.entity.Entity;
import com.Game.entity.moving.Monster;
import com.Game.gfx.Assets;
import com.Game.tile.Tile;
import com.Game.utils.Handler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
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
*
* This class deals with the "psychological" aspect of the monsters
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

    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    protected boolean[] availableDirections = new boolean[4]; // default value of elements in the array is false

    // variables necessary for the monster intelligence
    protected final int scatterPosX, scatterPosY; // direct monster in SCATTER state
    protected int chasePosX, chasePosY; // direct monster in CHASE state

    // time tracking for switching the phases
    public final int SCATTER_TIME = 7, CHASE_TIME = 15;
    private long lastTime;
    private double delta;

    // Monster reference
    protected Monster monster = null;
    protected String name = null; // variable to store monster's name for debugging purposes

    // Turning points
    protected static Point[] turningPoints = null;
    private Point lastTurningPoint; // point at which monster turned last time

    public MonsterBrain(Handler handler, int scatterPosX, int scatterPosY) {
        currentState = State.CHASE;
        this.scatterPosY = scatterPosY;
        this.scatterPosX = scatterPosX;
        lastTime = System.currentTimeMillis();
        currentDirection = DOWN;
        availableDirections[currentDirection] = true;
        this.handler = handler;
    }

    /*
     * This method is needed for monster to decide
     * its next move
     * */
    public void decide() {
        trackState(monster);
        setAvailableDirections();
        setChaseCoordinates();
        this.currentDirection = calculateBestDirection();
        activateMonster();
    }

    /*
     * This method has to be called every tick so that we can
     * properly track when we have to switch out states
     * */
    public void trackState(Monster monster) {
        long now = System.currentTimeMillis();
        delta += (now - lastTime) / 1000.0; // count how many seconds passed
        lastTime = now;

        if (currentState == State.CHASE) {
            if (delta > (double) CHASE_TIME) {
                setScatterState();
            }
        } else if (currentState == State.SCATTER) {
            if (delta > (double) SCATTER_TIME) {
                setChaseState();
            }
        } else if (currentState == State.FRIGHTENED) {
            if (!handler.getPacman().hasPowerBuff()) {
                setChaseState();
            }
        } else { // EATEN state
            if (isAtSpawn(monster)) {
                setChaseState();
            }
        }
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
        availableDirections[RIGHT] = checkDirection(RIGHT, currentState == State.FRIGHTENED);
        availableDirections[LEFT] = checkDirection(LEFT, currentState == State.FRIGHTENED);
        availableDirections[UP] = checkDirection(UP, currentState == State.FRIGHTENED);
        availableDirections[DOWN] = checkDirection(DOWN, currentState == State.FRIGHTENED);
    }

    /*
     * In CHASE state, we assign chasePosX and chasePosY every tick
     * since pacman constantly moves, but since each monster
     * has unique target positions, we leave this method abstract
     * */
    public abstract void setChaseCoordinates();

    /*
     * When there are several directions available, this method will calculate
     * the best direction by moving to which monsters will get the shortest
     * manhattan distance to the target
     * */
    public int calculateBestDirection() {
        if (!isTurningPoint()) {
            return currentDirection;
        }

        double[] values = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}; // since we have 4 directions
        if (currentState == State.CHASE) {
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(),
                                         chasePosX, chasePosY);
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(),
                                           chasePosX, chasePosY);
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y,
                                           chasePosX, chasePosY);
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y,
                                            chasePosX, chasePosY);
            }

        } else if (currentState == State.SCATTER) {
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(),
                                         scatterPosX, scatterPosY);
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(),
                                           scatterPosX, scatterPosY);
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y,
                                           scatterPosX, scatterPosY);
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y,
                                            scatterPosX, scatterPosY);
            }

        } else if (currentState == State.FRIGHTENED) { // monster moves randomly in the frightened state
            Random rand = new Random();
            int num;
            do {
                num = rand.nextInt(4);
            } while (!availableDirections[num]);

            return num;

        } else { // currentState == State.EATEN
            if (availableDirections[UP]) {
                values[UP] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y - monster.getSpeed(),
                                         (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[DOWN]) {
                values[DOWN] = getDistance(monster.getCollisionBox().x, monster.getCollisionBox().y + monster.getSpeed(),
                                           (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[LEFT]) {
                values[LEFT] = getDistance(monster.getCollisionBox().x - monster.getSpeed(), monster.getCollisionBox().y,
                                           (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
            }
            if (availableDirections[RIGHT]) {
                values[RIGHT] = getDistance(monster.getCollisionBox().x + monster.getSpeed(), monster.getCollisionBox().y,
                                            (int) monster.getSpawnPosX(), (int) monster.getSpawnPosY());
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

    /*
    * Some monsters start their movement after certain event
    * happens, such as player scoring 60 or when some certain
    * amount of time passes
    *
    * This method ensures that monsters start the chase properly
    * as the player progresses
    * */
    public abstract void activateMonster();


    protected boolean checkDirection(int direction, boolean isFrightened) {
        int x1, y1, x2, y2;
        if (direction == RIGHT) {
            x1 = (monster.getCollisionBox().x +
                  Entity.DEFAULT_COLLISION_BOUNDS_WIDTH + Tile.WIDTH) / Tile.WIDTH; // top-right x
            y1 = monster.getCollisionBox().y / Tile.HEIGHT; // top-right y
            x2 = (monster.getCollisionBox().x +
                  Entity.DEFAULT_COLLISION_BOUNDS_WIDTH + Tile.WIDTH) / Tile.WIDTH; // bottom-right x
            y2 = (monster.getCollisionBox().y +
                  Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT) / Tile.HEIGHT; // bottom-right y
            if (isFrightened) {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2);
            } else {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2) &&
                       currentDirection != LEFT;
            }
        } else if (direction == LEFT) {
            x1 = (monster.getCollisionBox().x - Tile.WIDTH) / Tile.WIDTH; // top-left x
            y1 = monster.getCollisionBox().y / Tile.HEIGHT; // top-left y
            x2 = (monster.getCollisionBox().x - Tile.WIDTH) / Tile.WIDTH; // bottom-left x
            y2 = (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT) / Tile.HEIGHT; // bottom-left y
            if (isFrightened) {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2);
            } else {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2) &&
                       currentDirection != RIGHT;
            }
        } else if (direction == UP) {
            x1 = monster.getCollisionBox().x / Tile.WIDTH; // top-left x
            y1 = (monster.getCollisionBox().y - Tile.HEIGHT) / Tile.HEIGHT; // top-left y
            x2 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH) / Tile.WIDTH; // top-right x
            y2 = (monster.getCollisionBox().y - Tile.HEIGHT) / Tile.HEIGHT; // top-right y
            if (isFrightened) {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2);
            } else {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2) &&
                       currentDirection != DOWN;
            }
        } else if (direction == DOWN) {
            x1 = monster.getCollisionBox().x / Tile.WIDTH; // bottom-left x
            y1 = (monster.getCollisionBox().y +
                  Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT + Tile.HEIGHT) / Tile.HEIGHT; // bottom-left y
            x2 = (monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH) / Tile.WIDTH; // bottom-right x
            y2 = (monster.getCollisionBox().y +
                  Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT + Tile.HEIGHT) / Tile.HEIGHT; // bottom-right y
            if (isFrightened) {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2);
            } else {
                return !monster.collidesWithTile(x1, y1) &&
                       !monster.collidesWithTile(x2, y2) &&
                       currentDirection != UP;
            }
        } else {
            throw new RuntimeException("Invalid direction");
        }
    }

    // checks if a given monster is at spawn
    public boolean isAtSpawn(@NotNull Monster monster) {
        // collision box coordinates should be used because they're guaranteed to be inside non-solid tiles
        return monster.getCollisionBox().x / Tile.WIDTH == (int) monster.getSpawnPosX() / Tile.WIDTH &&
               monster.getCollisionBox().y / Tile.HEIGHT == (int) monster.getSpawnPosY() / Tile.HEIGHT;
    }

    /*
    * returns the straight line distance from the x1, y1 position
    * to a given x2, y2 position
    * */
    public double getDistance(int x1, int y1, int x2, int y2) {
        int x, y;
        x = Math.abs(x1 - x2);
        y = Math.abs(y1 - y2);
        return Math.sqrt(x * x + y * y);
    }

    public void setMonster(Monster monster_) {
        if (monster == null) {
            monster = monster_;
        } else {
            throw new RuntimeException("Monster is already assigned!");
        }
    }

    public static void setTurningPoints(Point[] points) {
        if (turningPoints == null) {
            turningPoints = points;
        } else {
            throw new RuntimeException("Turning points are already ready!");
        }
    }

    // This method returns true if this monster is currently able to turn
    protected boolean isTurningPoint() {
        if (monster == null) {
            throw new RuntimeException("Monster is not set!");
        } else if (turningPoints == null) {
            throw new RuntimeException("turningPoints are not set");
        }
        // top-left corner of the monster's collision box
        Point point1 = new Point(monster.getCollisionBox().x / Tile.WIDTH,
                                 monster.getCollisionBox().y / Tile.HEIGHT);
        // bottom-right corner of the monster's collision box
        Point point2 = new Point((monster.getCollisionBox().x + Entity.DEFAULT_COLLISION_BOUNDS_WIDTH) / Tile.WIDTH,
                                 (monster.getCollisionBox().y + Entity.DEFAULT_COLLISION_BOUNDS_HEIGHT) / Tile.HEIGHT);

        // point1 and point2 have to be on the same tile, since otherwise turning will cause collision
        if (!point1.equals(point2)) {
            return false;
        }

        for (Point p : turningPoints) {
            if (p.equals(point1) && !p.equals(lastTurningPoint)) {
                lastTurningPoint = p;
                return true;
            }
        }
        return false;
    }

    public void setChaseState() {
        delta = 0;
        currentState = State.CHASE;
    }

    public void setScatterState() {
        delta = 0;
        currentState = State.SCATTER;
    }

    public void setFrightenedState() {
        if (currentState != State.FRIGHTENED) {
            delta = 0;
            currentState = State.FRIGHTENED;
        }
    }

    public void setEatenState() {
        delta = 0;
        currentState = State.EATEN;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public int getChasePosX() {
        return chasePosX;
    }

    public int getChasePosY() {
        return chasePosY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getCurrentSprite() {
        if (currentDirection == UP) {
            if (currentState == State.CHASE || currentState == State.SCATTER) {
                return Assets.getMonsterUp();
            } else if (currentState == State.FRIGHTENED) {
                return Assets.getMonsterFrightenedUp();
            } else if (currentState == State.EATEN) {
                return Assets.getMonsterEatenUp();
            } else {
                throw new RuntimeException("Invalid state");
            }
        } else if (currentDirection == DOWN) {
            if (currentState == State.CHASE || currentState == State.SCATTER) {
                return Assets.getMonsterDown();
            } else if (currentState == State.FRIGHTENED) {
                return Assets.getMonsterFrightenedDown();
            } else if (currentState == State.EATEN) {
                return Assets.getMonsterEatenDown();
            } else {
                throw new RuntimeException("Invalid state");
            }
        } else if (currentDirection == LEFT) {
            if (currentState == State.CHASE || currentState == State.SCATTER) {
                return Assets.getMonsterLeft();
            } else if (currentState == State.FRIGHTENED) {
                return Assets.getMonsterFrightenedLeft();
            } else if (currentState == State.EATEN) {
                return Assets.getMonsterEatenLeft();
            } else {
                throw new RuntimeException("Invalid state");
            }
        } else if (currentDirection == RIGHT) {
            if (currentState == State.CHASE || currentState == State.SCATTER) {
                return Assets.getMonsterRight();
            } else if (currentState == State.FRIGHTENED) {
                return Assets.getMonsterFrightenedRight();
            } else if (currentState == State.EATEN) {
                return Assets.getMonsterEatenRight();
            } else {
                throw new RuntimeException("Invalid state");
            }
        } else {
            throw new RuntimeException("Invalid direction");
        }
    }

}
