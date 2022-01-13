package com.Game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
* This class is needed for input handling
* */
public class KeyManager implements KeyListener {
    private boolean[] keys; //variable to store which key on keyboard was pressed
    public boolean up, down, left, right;

    public KeyManager() {
        keys = new boolean[256];
        up = false; down = false;
        left = false; right = false;
    }

    public void tick() {
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_D];
        left = keys[KeyEvent.VK_A];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    /*
    * keyPressed() is called everytime when key on the keyboard is pressed.
    *
    * This method below only sets true for one of WASD keys,
    * meaning that only they can cause anything in our code
    * other keys are ignored (except for escape)
    * */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_S || code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            for (int i = 0; i < 256; i++) {
                keys[i] = false;
            }
            keys[code] = true;
        } else if (code == KeyEvent.VK_ESCAPE) { //close the game when 'Esc' is pressed
            System.exit(123);
        }
    }

    /*
    * keyReleased is called everytime the pressed key is released
    * */
    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }
}
