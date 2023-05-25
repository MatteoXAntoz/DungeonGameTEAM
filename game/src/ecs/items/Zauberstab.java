package ecs.items;

import ecs.entities.Entity;
import level.elements.tile.TrapTile;
import starter.Game;
import tools.Point;

public class Zauberstab extends Item {

    public Zauberstab() {
        super();
        name = "Zauberstab";
        path = "stab-no_background.png";
        setupAnimation();
        setupPositionComponent();
    }

    public Entity getEntity() {
        return positionComponent.getEntity();
    }

    public Point getPosition() {
        return positionComponent.getPosition();
    }

    public void setPosition(Point position) {
        positionComponent.setPosition(position);
    }

    public static void REMOVETRAPS() {
        for (TrapTile tile : Game.currentLevel.getTrapTiles()) {
            tile.setTexturePath("dungeon/default/floor/floor_1.png");
            tile.name = "None";
        }
    }
}
