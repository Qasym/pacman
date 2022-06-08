package com.Game.entity;

import com.Game.entity.moving.Monster;
import com.Game.entity.moving.Pacman;
import com.Game.utils.Handler;

import java.awt.*;
import java.util.ArrayList;

/*
* EntityManager is class that has a self-descriptive name
* It manages all the entities I have in a pacman game
* */
public class EntityManager {
    private Handler handler;
    private Pacman pacman;
    private final ArrayList<Entity> entities;
    private final ArrayList<Monster> monsters;

    public EntityManager(Handler handler, Pacman pacman) {
        this.handler = handler;
        this.pacman = pacman;
        entities = new ArrayList<>(31 * 28); // 31 * 28 because of the world dimensions
        monsters = new ArrayList<>(4);
        addEntity(pacman);
    }

    public void tick() {
        Entity entity;
        for (int i = 0; i < entities.size(); i++) {
            entity = entities.get(i);
            entity.tick();
        }
    }

    public void render(Graphics g) {
        Entity entity;
        for (int i = 0; i < entities.size(); i++) {
            entity = entities.get(i);
            entity.render(g);
        }
    }

    public void addEntity(Entity e) {
        entities.add(e);

        // This way we sort all entities in ascending order by the bottom line of their sprites
        // This is needed to draw entities that are below, after the entities that are above
        // Reference for a better visualisation:
        // https://www.youtube.com/watch?v=zWDCmH21G30&list=PLah6faXAgguMnTBs3JnEJY0shAc18XYQZ&index=28
        entities.sort((o1, o2) -> (int)((o1.y + o1.height) - (o2.y + o2.height)));
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
        addEntity(monster);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public Handler getHandler() {
        return handler;
    }

    public Pacman getPacman() {
        return pacman;
    }
}
