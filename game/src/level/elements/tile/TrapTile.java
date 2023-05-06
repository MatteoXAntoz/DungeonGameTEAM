package level.elements.tile;

import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.ILevel;
import level.tools.Coordinate;
import level.tools.DesignLabel;
import level.tools.LevelElement;
import tools.Point;

public class TrapTile extends FloorTile {

    public boolean activated;


    /**
     * Creates a new Tile.
     *
     * @param texturePath Path to the texture of the tile.
     * @param globalPosition Position of the tile in the global system.
     * @param designLabel Design of the Tile
     * @param level The level this Tile belongs to
     */





    public TrapTile(
        String texturePath, Coordinate globalPosition, DesignLabel designLabel, ILevel level) {
        super(texturePath, globalPosition, designLabel, level);
        getName(); // dem Objekt wird anhand der zugewiesenen Textur einen Namen zugewiesen
        levelElement = LevelElement.TRAP;

    }


    public void getName() {
        String mouseTrap = "dungeon/default/floor/floor_mouseTrap.png";
        String poisonTrap = "dungeon/default/floor/floor_poison.png";
        String lavaTrap = "dungeon/default/floor/floor_lava.png";


        if (getTexturePath().equals(mouseTrap)) {
            this.name = "MOUSETRAP";
        } else if (getTexturePath().equals(lavaTrap)) {
            this.name = "LAVATRAP";
        } else if (getTexturePath().equals(poisonTrap)) {
            this.name = "POISONTRAP";

        }


    }


    @Override
    public void onEntering(Entity element) {


    }

    @Override
    public boolean isAccessible() {
        return levelElement.getValue();
    }




}
