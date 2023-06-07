package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.Point;

/** abstract class to implement a meele attack */
public abstract class DamageMeeleSkill implements ISkillFunction {

    private String pathToTextures;
    private final Damage meeleDamage;
    // Meele damage only does damage once, for that damageDealt.
    private boolean damageDealt;
    private final Point meeleHitboxSize;
    private final float knockBackVelocity;
    private final int knockBackDuration;
    private final int durationInFrames;
    private static Logger meeleLogger = Logger.getLogger(DamageMeeleSkill.class.getName());

    private ITargetSelection selectionFunction;
    private ICollide collideFunktion;

    /**
     * Super konstruktor for meele skills
     *
     * @param pathToTextures Textures of the aeele attack.
     * @param meeleDamage the anmount and type of damage the attack does
     * @param meeleHitboxSize Size and Orientation of the Meele Hitbox, oriented North.
     * @param knockBackVelocity Velocity the enemy/ies is/are pushed back with
     * @param knockBackDuration Duration the enemy/ies is/are pushed back for
     * @param durationInFrames Duration the meele is Visible
     * @param selectionFunction How the Target is targeted. Has to return a Point that works as a
     *     vektor
     */
    public DamageMeeleSkill(
            String pathToTextures,
            Damage meeleDamage,
            Point meeleHitboxSize,
            float knockBackVelocity,
            int knockBackDuration,
            int durationInFrames,
            ITargetSelection selectionFunction) {

        this.pathToTextures = pathToTextures;
        this.meeleDamage = meeleDamage;
        this.meeleHitboxSize = meeleHitboxSize;
        this.knockBackVelocity = knockBackVelocity;
        this.knockBackDuration = knockBackDuration;
        this.durationInFrames = durationInFrames;
        this.selectionFunction = selectionFunction;
        meeleLogger.setLevel(Level.INFO);
    }

    @Override
    public void execute(Entity entity) {

        // Values that are needed
        // #########################

        // Entity that represents the Meele attack
        Entity meele = new Entity();

        // Vector for the direction
        // Attack is executed in mouse direction either, north, south, east or west
        Point direction = selectionFunction.selectTargetPoint(entity);

        // HitboxComponent of the Entity using the Meele Attack
        HitboxComponent ehc =
                (HitboxComponent)
                        entity.getComponent(HitboxComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("HitboxComponent"));

        meeleLogger.fine(
                "Entity Hitbox: BL-" + ehc.getBottomLeft() + " TR-" + ehc.getTopRight() + "\n");

        // Hitbox is rotated if direction is east or west, in case it is not a square
        // Since its start orientation is north

        Point hitboxSize = meeleHitboxSize;
        if (direction.x != 0) hitboxSize = new Point(meeleHitboxSize.y, meeleHitboxSize.x);

        // setUpMethods for all the Components
        // ####################################
        this.setUpPositionComponent(meele, ehc, direction, hitboxSize);

        this.setUpHitboxcomponent(entity, meele, hitboxSize);

        this.setUpAnimationComponent(meele, direction);

        new MeeleComponent(meele, durationInFrames);
    }

    // creates a new PositionComponent calculating the right position with calculateHitboxPosition()
    private void setUpPositionComponent(
            Entity meele, HitboxComponent ehc, Point direction, Point hitboxSize) {
        new PositionComponent(meele, this.calculateHitboxPosition(ehc, direction, hitboxSize));
    }

    // creates a AnimationComponent with the right animation coresponding to the direction
    private void setUpAnimationComponent(Entity meele, Point direction) {
        Animation animation = this.getAnimations(direction, durationInFrames / 3);
        new AnimationComponent(meele, animation);
    }

    // Sets the hitboxComponent with its ICollide function and its knockback
    private void setUpHitboxcomponent(Entity entity, Entity meele, Point hitboxSize) {

        // ICollide Function describing what happens when an Entity gets git with meele
        damageDealt = false;
        ICollide collide =
                (self, other, from) -> {
                    if (other != entity && !damageDealt) {
                        other.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            // entity gets damage that was set
                                            ((HealthComponent) hc).receiveHit(meeleDamage);
                                            damageDealt = true;
                                        });

                        // Gives git entity knockback
                        setKnockBack(other, entity);
                    }
                };

        HitboxComponent hc =
                new HitboxComponent(meele, new Point(0f, 0f), hitboxSize, collide, null);

        meeleLogger.fine("Meele Hitbox: BL-" + hc.getBottomLeft() + " TR-" + hc.getTopRight());
    }

    // Sets KnockbackComponent to "getting" entity, in direction away from "giving" entity
    private void setKnockBack(Entity getting, Entity giving) {

        // Position components of both entities
        PositionComponent getPc =
                (PositionComponent) (giving.getComponent(PositionComponent.class).get());
        PositionComponent givPc =
                (PositionComponent) (getting.getComponent(PositionComponent.class).get());

        // calculates the specific x and y velocities
        Point velocity =
                SkillTools.calculateVelocity(
                        getPc.getPosition(), givPc.getPosition(), knockBackVelocity);
        // adds Component to "getting" Entity
        getting.addComponent(
                new KnockBackComponent(getting, velocity.x, velocity.y, knockBackDuration));
    }

    // gets right animatin path according to direction
    // Note: All MeeleAttacks must have 4 subfolders with exactly those names
    private Animation getAnimations(Point direction, int frameTime) {
        String sDirection;
        if (direction.y == 1) sDirection = "attack_Up/";
        else if (direction.y == -1) sDirection = "attack_Down/";
        else if (direction.x == 1) sDirection = "attack_Right/";
        else sDirection = "attack_Left/";

        return AnimationBuilder.buildAnimation("knight/attack/" + sDirection, frameTime);
    }

    // Calculates the right position for PositionComponent and therefore the Hitbox.
    // Hitbox should be alligned and centered with the Entity Hitbox
    private Point calculateHitboxPosition(HitboxComponent ehc, Point direction, Point hitboxSize) {
        meeleLogger.finer("Direction: " + direction);

        // Center of the Entity as Start Point
        Point eCenter = ehc.getCenter();

        // gets the size of the Entity as a Vector(Point)
        Point eSize =
                new Point(
                        ehc.getTopRight().x - ehc.getBottomLeft().x,
                        ehc.getTopRight().y - ehc.getBottomLeft().y);
        meeleLogger.finer("Entity Hitbox Size: " + eSize);

        // gets the Center of the new hitbox by adding the sizes of the hitboxes to the center of
        // the Entity.
        // And then multiplying them with the direction Vector
        Point mCenter =
                new Point(
                        eCenter.x + (eSize.x + hitboxSize.x) * direction.x / 2,
                        eCenter.y + (eSize.y + hitboxSize.y) * direction.y / 2);

        // goes from the center of the hitbox to the bottom Left corner where the positionComponent
        // will be.
        Point mPosition = new Point(mCenter.x + hitboxSize.x / -2, mCenter.y + hitboxSize.y / -2);
        return mPosition;
    }

    //    private void cloneEntityVelocity(Entity entity, Entity meele) {
    //        VelocityComponent ehc =
    //            (VelocityComponent)
    //                entity.getComponent(VelocityComponent.class)
    //                    .orElseThrow(
    //                        () -> new MissingComponentException("VelocityComponent"));
    //
    //        meele.addComponent(ehc);
    //    }
}
