package level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ecs.entities.*;
import ecs.items.*;
import graphic.Painter;
import graphic.PainterConfig;
import java.util.*;
import java.util.logging.Logger;
import level.elements.ILevel;
import level.elements.tile.*;
import level.generator.IGenerator;
import level.myQuest.LevelManager;
import level.myQuest.MyQuestConfig;
import level.tools.DesignLabel;
import level.tools.LevelElement;
import level.tools.LevelSize;
import starter.Game;

/** Manages the level. */
public class LevelAPI {

    private final SpriteBatch batch;
    private final Painter painter;
    private final IOnLevelLoader onLevelLoader;
    private IGenerator gen;
    private ILevel currentLevel;
    private final Logger levelAPI_logger = Logger.getLogger(this.getClass().getName());

    public static int levelID;

    MyQuestConfig myQuestConfig;
    LevelManager levelManager;

    public ArrayList<LevelElement> trapElements = new ArrayList<>();

    Grave grave;

    /**
     * @param batch Batch on which to draw.
     * @param painter Who draws?
     * @param generator Level generator
     * @param onLevelLoader Object that implements the onLevelLoad method.
     */
    public LevelAPI(
            SpriteBatch batch,
            Painter painter,
            IGenerator generator,
            IOnLevelLoader onLevelLoader) {
        this.gen = generator;
        this.batch = batch;
        this.painter = painter;
        this.onLevelLoader = onLevelLoader;
        levelManager = LevelManager.getInstance();
    }

    /**
     * Load a new Level
     *
     * @param size The size that the level should have
     * @param label The design that the level should have
     */
    public void loadLevel(LevelSize size, DesignLabel label) {

        currentLevel = gen.getLevel(label, size);
        onLevelLoader.onLevelLoad();
        levelID += 1;
        levelAPI_logger.info("Level " + levelID + " was loaded.");

        levelManager.setLevelSurvivedWithoutDamage(
                levelManager.getLevelSurvivedWithoutDamage() + 1);
        levelAPI_logger.info(levelManager.getLevelSurvivedWithoutDamage() + " Level" + " survived");

        grave = new Grave();
        grave.positionComponent.setPosition(
                currentLevel.getRandomFloorTile().getCoordinateAsPoint());

        /**
         * die Methode spawnItems ist für das Spawnen der Item zuständig, entweder werden sie
         * geladen oder neue generiert.
         */
        spawnItems();
        /**
         * die Methode spawnTraps ist für das Spawnen der Traps zuständig, entweder werden sie
         * geladen oder neue generiert.
         */
        spawnTraps();
        /**
         * die Methode spawnMonsters ist für das Spawnen der Monster zuständig, entweder werden sie
         * geladen oder neue generiert.
         */
        spawnMonsters();

        String heroInfo = String.valueOf(Game.hero.healthComponent.getCurrentHealthpoints());
        levelAPI_logger.info(heroInfo);
    }

    /**
     * Load a new level with random size and the given desing
     *
     * @param designLabel The design that the level should have
     */
    public void loadLevel(DesignLabel designLabel) {
        loadLevel(LevelSize.randomSize(), designLabel);
    }

    /**
     * Load a new level with the given size and a random desing
     *
     * @param size wanted size of the level
     */
    public void loadLevel(LevelSize size) {
        loadLevel(size, DesignLabel.randomDesign());
    }

    /** Load a new level with random size and random design. */
    public void loadLevel() {
        loadLevel(LevelSize.randomSize(), DesignLabel.randomDesign());
    }

    /** Draw level */
    public void update() {
        drawLevel();
        levelManager.update();
        updateTrapCollider();
    }

    /**
     * @return The currently loaded level.
     */
    public ILevel getCurrentLevel() {
        return currentLevel;
    }

    protected void drawLevel() {
        Map<String, PainterConfig> mapping = new HashMap<>();

        Tile[][] layout = currentLevel.getLayout();
        for (int y = 0; y < layout.length; y++) {
            for (int x = 0; x < layout[0].length; x++) {
                Tile t = layout[y][x];
                if (t.getLevelElement() != LevelElement.SKIP) {
                    String texturePath = t.getTexturePath();
                    if (!mapping.containsKey(texturePath)) {
                        mapping.put(texturePath, new PainterConfig(texturePath));
                    }
                    painter.draw(
                            t.getCoordinate().toPoint(), texturePath, mapping.get(texturePath));
                }
            }
        }
    }

    /**
     * @return The currently used Level-Generator
     */
    public IGenerator getGenerator() {
        return gen;
    }

    /**
     * Set the level generator
     *
     * @param generator new level generator
     */
    public void setGenerator(IGenerator generator) {
        gen = generator;
    }

    /**
     * Sets the current level to the given level and calls onLevelLoad().
     *
     * @param level The level to be set.
     */
    public void setLevel(ILevel level) {
        currentLevel = level;
        onLevelLoader.onLevelLoad();
    }

    // Laesst bei jedem Levelaufruf neue Items spawnen
    public void spawnRandomItems() {

        Game.items.clear();

        int maxItems = 5;
        for (int i = 0; i < maxItems; i++) {
            Game.items.add(Item.ranItem());
        }
    }

    public LevelElement getRandomTraps() {
        int ranValue = (int) (Math.random() * 3 + 1);
        if (ranValue == 1) {
            //  System.out.println("Lava");
            return LevelElement.LAVA;
        }
        if (ranValue == 2) {
            // System.out.println("MOUSETRAP");
            return LevelElement.MOUSETRAP;
        }
        if (ranValue == 3) {
            //  System.out.println("POISON");
            return LevelElement.POISON;
        }
        return null;
    }

    public void spawnTraps() {

        trapElements.clear();
        if (!SaveLoadGame.isEmpty(SaveLoadGame.PATH, SaveLoadGame.TRAP_DATA) && levelID == 1) {
            levelAPI_logger.info("Fallen wurden geladen");
            trapElements = SaveLoadGame.loadTraps();
        } else {
            for (int i = 0; i < 2; i++) {
                trapElements.add(getRandomTraps());
            }
        }

        for (int i = 0; i < trapElements.size(); i++) {
            if (trapElements.get(i) == LevelElement.MOUSETRAP) {
                getCurrentLevel()
                        .getRandomTile(LevelElement.FLOOR)
                        .setLevelElement(LevelElement.MOUSETRAP);
            } else if (trapElements.get(i) == LevelElement.LAVA) {
                getCurrentLevel()
                        .getRandomTile(LevelElement.FLOOR)
                        .setLevelElement(LevelElement.LAVA);
            } else if (trapElements.get(i) == LevelElement.POISON) {
                getCurrentLevel()
                        .getRandomTile(LevelElement.FLOOR)
                        .setLevelElement(LevelElement.POISON);
            }
        }
        for (FloorTile floorTile : currentLevel.getFloorTiles()) {
            if (floorTile.getLevelElement() == LevelElement.MOUSETRAP) {
                floorTile.setTexturePath("dungeon/default/floor/floor_mouseTrap.png");
            } else if (floorTile.getLevelElement() == LevelElement.POISON) {
                floorTile.setTexturePath("dungeon/default/floor/floor_poison.png");
            } else if (floorTile.getLevelElement() == LevelElement.LAVA) {
                floorTile.setTexturePath("dungeon/default/floor/floor_lava.png");
            }
        }
    }

    public void spawnItems() {
        if (levelID == 1 && !SaveLoadGame.isEmpty(SaveLoadGame.PATH, SaveLoadGame.ITEM_DATA)) {
            levelAPI_logger.info("Items wurden geladen");
            Game.items = SaveLoadGame.loadItems();
        } else {
            spawnRandomItems();
        }
    }

    public void updateTrapCollider() {
        for (FloorTile floorTile : Game.currentLevel.getFloorTiles()) {
            if (Game.hero.isCollidingWithTrapTile(floorTile)
                    && floorTile.getLevelElement() == LevelElement.POISON
                    && !floorTile.isActivated()) {
                int duration = 2;
                int damage = (int) (Math.random() * 5);
                while (duration >= 0) {
                    Game.hero.healthComponent.setCurrentHealthpoints(
                            Game.hero.healthComponent.getCurrentHealthpoints() - damage);
                    duration -= 1;
                }
                duration = 2;

                floorTile.setTexturePath("dungeon/default/floor/floor_poison_deactivated.png");
                floorTile.setActivated(true);

            } else if (Game.hero.isCollidingWithTrapTile(floorTile)
                    && floorTile.getLevelElement() == LevelElement.LAVA
                    && !floorTile.isActivated()) {
                int duration = 2;
                int damage = 4;
                while (duration >= 0) {
                    Game.hero.healthComponent.setCurrentHealthpoints(
                            Game.hero.healthComponent.getCurrentHealthpoints() - damage);
                    duration -= 1;
                }
                duration = 2;

                floorTile.setTexturePath("dungeon/default/floor/floor_lava_deactivated.png");
                floorTile.setActivated(true);

            } else if (Game.hero.isCollidingWithTrapTile(floorTile)
                    && floorTile.getLevelElement() == LevelElement.MOUSETRAP
                    && !floorTile.isActivated()) {

                int duration = 100000000;
                while (duration > 0) {
                    Game.hero.velocityComponent.setCurrentYVelocity(0);
                    Game.hero.velocityComponent.setCurrentXVelocity(0);
                    duration -= 0.0001;
                }
                if (duration == 0) {
                    floorTile.setActivated(true);
                }

                floorTile.setTexturePath("dungeon/default/floor/floor_mouseTrap_deactivated.png");
            }
        }
    }

    public void spawnMonsters() {
        if (!SaveLoadGame.isEmpty(SaveLoadGame.PATH, SaveLoadGame.MONSTER_DATA) && levelID == 1) {
            levelAPI_logger.info("Monster wurden geladen");
            levelManager.monster = SaveLoadGame.loadMonsters();
        } else {
            levelManager.setMonster(levelID);
        }
    }
}
