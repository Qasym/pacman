package com.Game.states;

import com.Game.audio.AudioManager;
import com.Game.gfx.Assets;
import com.Game.ui.ImageButton;
import com.Game.ui.UIManager;
import com.Game.utils.Handler;

import java.awt.*;

public class SettingsState extends State {
    protected UIManager uiManager;

    public SettingsState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUiManager(uiManager);

        // turn music on/off
        uiManager.addObject(new ImageButton(320, 200, 200, 50, Assets.getMusicButton(),() -> {
            if (AudioManager.isMusicOn()) {
                AudioManager.setMusic(false); // turn off the music
                AudioManager.stopMusic();
            } else {
                AudioManager.setMusic(true);
                AudioManager.playMenuMusic(); // turn on the music
            }
        }));

        // show collision boxes instead of sprites of entities
        uiManager.addObject(new ImageButton(320, 300, 200, 50, Assets.getEntityCollisionsButton(), () -> {
            Handler.DEBUG = !Handler.DEBUG;
        }));

        // show back button to return to menu
        uiManager.addObject(new ImageButton(320, 400, 200, 50, Assets.getBackButton(), () -> {
            handler.getMouseManager().setUiManager(((MenuState)handler.getGame().menuState).getUiManager());
            State.setState(handler.getGame().menuState);
        }));
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
