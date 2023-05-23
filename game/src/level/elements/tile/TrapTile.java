package level.elements.tile;

import ecs.entities.Hero;
import level.elements.ILevel;
import level.tools.Coordinate;
import level.tools.DesignLabel;

public class TrapTile extends FloorTile{
    /**
     * Creates a new Tile.
     *
     * @param texturePath    Path to the texture of the tile.
     * @param globalPosition Position of the tile in the global system.
     * @param designLabel    Design of the Tile
     * @param level          The level this Tile belongs to
     */
    public TrapTile(String texturePath, Coordinate globalPosition, DesignLabel designLabel, ILevel level) {
        super(texturePath, globalPosition, designLabel, level);
    }

    public boolean isCollidingWithHero(Hero hero) {
        float hitBoxScale = 0.6f;

        return (hero.positionComponent.getPosition().x + hitBoxScale > getCoordinateAsPoint().x &&
            hero.positionComponent.getPosition().x < getCoordinateAsPoint().x + hitBoxScale &&
            hero.positionComponent.getPosition().y + hitBoxScale > getCoordinateAsPoint().y &&
            hero.positionComponent.getPosition().y < getCoordinateAsPoint().y + hitBoxScale);
    }

}
