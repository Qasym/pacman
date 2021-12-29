package com.Game.states;

import java.awt.*;

/*
 * A game state is a phase at which our player is currently at.
 * It may be a main menu, settings or the game itself.
 * We have to be able to manage those states and Game State Manager will help us to do that
 */
public abstract class State {
    /* Below code is needed for our Game State Managing*/
    ///---GAME STATE MANAGER---///
    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }
    ///---GAME STATE MANAGER---///
    
    public abstract void tick();
    public abstract void render(Graphics g);
}
