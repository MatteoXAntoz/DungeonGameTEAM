package level.elements.tile;

import ecs.entities.Entity;
import level.elements.ILevel;
import level.tools.Coordinate;
import level.tools.DesignLabel;
import level.tools.LevelElement;

/**
 * class to create a riddle hint tile
 * @author Moritz Luetzkendorf
 *
 */
public class RiddleHintTile extends Tile {
    private boolean activated;
    /**
     * Creates a new Tile.
     *
     * @param texturePath Path to the texture of the tile.
     * @param globalPosition Position of the tile in the global system.
     * @param designLabel Design of the Tile
     * @param level The level this Tile belongs to
     */
    public RiddleHintTile(
            String texturePath, Coordinate globalPosition, DesignLabel designLabel, ILevel level) {
        super(texturePath, globalPosition, designLabel, level);
        levelElement = LevelElement.RIDDLE;
    }

    @Override
    public void onEntering(Entity element) {}

    @Override
    public boolean isAccessible() {
        return levelElement.getValue();
    }
    /** method to check if a tile was activated or not */
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
