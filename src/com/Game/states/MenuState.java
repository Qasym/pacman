package com.Game.states;

import com.Game.audio.AudioManager;
import com.Game.gfx.Assets;
import com.Game.launcher.Game;
import com.Game.ui.ImageButton;
import com.Game.ui.UIManager;
import com.Game.utils.Handler;

import java.awt.*;

/*
* This class is responsible for everything that is happening in the menu
* */
public class MenuState extends State {
    private final UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);

        AudioManager.playMenuMusic(); // static method of audio manager

        uiManager = new UIManager(handler); // to manage our UIElements
        /*
        * Creation of button is done below
        * We use ImageButton class that is defined in com.Game.ui and pass necessary
        * arguments to the constructor
        * The last argument is a lambda expression for the ClickListener interface, which is basically
        * the piece of code that defines what to do when the button is pressed
        * */
        uiManager.addObject(new ImageButton(300f, 300f, 300, 150, Assets.getPlayButton(), () -> {
            AudioManager.stopMusic();
            handler.getMouseManager().setUiManager(null);
            ((GameState)handler.getGame().gameState).reinitializeState();
            State.setState(handler.getGame().gameState);
        }));
        uiManager.addObject(new ImageButton(300f, 700f, 300, 150, Assets.getOptionsButton(), () -> {
            handler.getMouseManager().setUiManager(((SettingsState)(handler.getGame().settingsState)).getUiManager());
            State.setState(handler.getGame().settingsState);
        }));

        handler.getMouseManager().setUiManager(uiManager);
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }

    public UIManager getUiManager() {
        return uiManager;
    }
}
