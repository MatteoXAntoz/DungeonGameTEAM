package ecs.items;

import ecs.entities.Entity;
import level.elements.tile.FloorTile;
import level.tools.LevelElement;
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
        for (FloorTile floorTile : Game.currentLevel.getFloorTiles()) {
            if (floorTile.getLevelElement() == LevelElement.LAVA
                    || floorTile.getLevelElement() == LevelElement.POISON
                    || floorTile.getLevelElement() == LevelElement.MOUSETRAP) {
                floorTile.setLevelElement(LevelElement.FLOOR);
                floorTile.setTexturePath("dungeon/default/floor/floor_1.png");
            }
        }
    }
}
