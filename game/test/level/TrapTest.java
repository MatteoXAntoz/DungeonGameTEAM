package level;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ecs.components.*;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Painter;
import level.elements.TileLevel;
import level.elements.tile.*;
import level.generator.IGenerator;
import level.tools.Coordinate;
import level.tools.DesignLabel;
import level.tools.LevelElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tools.Point;

public class TrapTest {

    SpriteBatch spriteBatch;
    IGenerator iGenerator;
    IOnLevelLoader iOnLevelLoader;
    Painter painter = Mockito.mock(Painter.class);
    Coordinate coordinate;

    Hero hero;
    private LevelAPI levelAPI;

    @Before
    public void setup() {

        hero = Mockito.mock(Hero.class);

        coordinate = new Coordinate(5, 5);

        spriteBatch = Mockito.mock(SpriteBatch.class);
        iGenerator = Mockito.mock(IGenerator.class);
        iOnLevelLoader = Mockito.mock(IOnLevelLoader.class);
        levelAPI = new LevelAPI(spriteBatch, painter, iGenerator, iOnLevelLoader);
    }

    // Test für die Erzeugung einer Lava-Falle
    @Test
    public void test_shouldSpawnLavaTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);
        level.getRandomTile().setLevelElement(LevelElement.LAVA);

        assertSame(level.getRandomTile().getLevelElement(), LevelElement.LAVA);
    }

    // Test für die Erzeugung einer Mausefalle
    @Test
    public void test_shouldSpawnMouseTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);
        level.getRandomTile().setLevelElement(LevelElement.MOUSETRAP);

        assertSame(level.getRandomTile().getLevelElement(), LevelElement.MOUSETRAP);
    }

    // Test für die Erzeugung einer Giftfalle
    @Test
    public void test_shouldSpawnPoisonTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);
        level.getRandomTile().setLevelElement(LevelElement.POISON);

        assertSame(level.getRandomTile().getLevelElement(), LevelElement.POISON);
    }

    // Test für die Nicht-Erzeugung einer Lava-Falle
    @Test
    public void test_shouldNotSpawnLavaTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        assertNotSame(level.getRandomTile().getLevelElement(), LevelElement.LAVA);
    }

    // Test für die Nicht-Erzeugung einer Mausefalle
    @Test
    public void test_shouldNotSpawnMouseTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        assertNotSame(level.getRandomTile().getLevelElement(), LevelElement.MOUSETRAP);
    }

    // Test für die Nicht-Erzeugung einer Giftfalle
    @Test
    public void test_shouldNotSpawnPoisonTrap() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        assertNotSame(level.getRandomTile().getLevelElement(), LevelElement.POISON);
    }

    // Test für die Aktivierung einer Bodenfliese nach Kollision mit einer Falle
    @Test
    public void test_activationAfterCollision() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        FloorTile floorTile = levelAPI.getCurrentLevel().getFloorTiles().get(0);
        when(hero.isCollidingWithTrapTile(any())).thenReturn(true);
        if (hero.isCollidingWithTrapTile(any())) {
            floorTile.setActivated(true);
        }
        assertTrue(floorTile.isActivated());
    }

    // Test für die Nicht-Aktivierung einer Bodenfliese nach Kollision mit einer Falle
    @Test
    public void test_activationAfterCollisionShouldNotWork() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        FloorTile floorTile = levelAPI.getCurrentLevel().getFloorTiles().get(0);
        when(hero.isCollidingWithTrapTile(any())).thenReturn(false);
        if (hero.isCollidingWithTrapTile(any())) {
            floorTile.setActivated(false);
        }
        assertFalse(floorTile.isActivated());
    }

    // Test für die Kollision zwischen Entity und Bodenfliese
    @Test
    public void test_collision() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        FloorTile floorTile = levelAPI.getCurrentLevel().getFloorTiles().get(0);
        Entity entity = new Entity();
        entity.positionComponent = new PositionComponent(entity, new Point(0, 0));
        assertTrue(entity.isCollidingWithTrapTile(floorTile));
    }

    // Test für das Nicht-Vorkommen einer Kollision zwischen Entity und Bodenfliese
    @Test
    public void test_collisionShouldNotWork() {
        LevelElement[][] levelElement = new LevelElement[][] {{LevelElement.FLOOR}};
        TileLevel level = new TileLevel(levelElement, DesignLabel.DEFAULT);
        levelAPI.setLevel(level);

        FloorTile floorTile = levelAPI.getCurrentLevel().getFloorTiles().get(0);
        Entity entity = new Entity();
        entity.positionComponent = new PositionComponent(entity, new Point(10, 10));
        assertFalse(entity.isCollidingWithTrapTile(floorTile));
    }

    // Test für die maximale Anzahl von Fallen (zwei)
    @Test
    public void test_maxSizeOfTrapsShouldBeTwo() {
        levelAPI.addTraps();
        int amount = levelAPI.trapElements.size();
        assertEquals(2, amount);
    }

    // Grenzwert: 3
    // Test für die maximale Anzahl von Fallen (nicht drei)
    @Test
    public void test_maxSizeOfTrapsShouldNotBe3() {
        levelAPI.addTraps();
        int amount = levelAPI.trapElements.size();
        assertNotEquals(3, amount);
    }
}
