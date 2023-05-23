package ecs.entities.items;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import graphic.Animation;

/**
 * Die Klasse Item repräsentiert ein Item in der Spielwelt.
 */
public class Item extends Entity {

    /**
     * Die Positionskomponente des Items.
     *
     * @see PositionComponent
     */
    public PositionComponent positionComponent;

    /**
     * Der Pfad zum Bild des Items.
     */
    protected String path;

    /**
     * Der Name des Items.
     */
    public String name;

    /**
     * Gibt an, ob das Item eingesammelt wurde.
     */
    public boolean collected = false;

    /**
     * Die Animation des Items.
     */
    Animation animation;

    /**
     * Erstellt eine neue Instanz der Item-Klasse.
     */
    public Item() {
        super();
        setupPositionComponent();
    }

    /**
     * Bereitet die Animation des Items vor.
     */
    protected void setupAnimation() {
        animation = AnimationBuilder.buildAnimation(path);
        new AnimationComponent(this, animation);
    }

    /**
     * Bereitet die Positionskomponente des Items vor.
     */
    protected void setupPositionComponent() {
        if (positionComponent == null) {
            positionComponent = new PositionComponent(this);
        }
    }

    /**
     * Erzeugt ein zufälliges Item.
     *
     * @return Das erzeugte zufällige Item.
     */
    public static Item ranItem() {
        int ranValue = (int) (Math.random() * 4);
        if (ranValue == 0) {
            return new FoodBag();
        }
        if (ranValue == 1) {
            return new PotionBag();
        }
        if (ranValue == 2) {
            return new Food();
        }
        if (ranValue == 3) {
            return new Potion();
        }
        return new Item();
    }

    /**
     * Gibt die Positionskomponente des Items zurück.
     *
     * @return Die Positionskomponente des Items.
     */
    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    /**
     * Setzt die Positionskomponente des Items.
     *
     * @param positionComponent Die zu setzende Positionskomponente.
     */
    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    /**
     * Gibt den Namen des Items zurück.
     *
     * @return Der Name des Items.
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Items.
     *
     * @param name Der zu setzende Name des Items.
     */
    public void setName(String name) {
        this.name = name;
    }
}
