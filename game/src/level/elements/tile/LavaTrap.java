package level.elements.tile;

import level.elements.ILevel;
import level.tools.Coordinate;
import level.tools.DesignLabel;

public class LavaTrap extends TrapTile{
    /**
     * Creates a new Tile.
     *
     * @param texturePath    Path to the texture of the tile.
     * @param globalPosition Position of the tile in the global system.
     * @param designLabel    Design of the Tile
     * @param level          The level this Tile belongs to
     */
    public LavaTrap(String texturePath, Coordinate globalPosition, DesignLabel designLabel, ILevel level) {
        super(texturePath, globalPosition, designLabel, level);
        texturePath="dungeon/default/floor/floor_lava.png";
    }
}