package ecs.components.ai.idle;

import ecs.components.skill.*;
import ecs.entities.Entity;
import java.util.logging.Logger;
import starter.Game;
import tools.Point;

/**
 * @author MatteoXAntoz
 * A class representing a fire attack.
 */
public class FireAttack {

    private final Logger fireAttack_Logger = Logger.getLogger(this.getClass().getName());
    private float fireballCooldown = 2;
    private float spitfireCoolDown = 1;


    /**
     * Constructs a new instance of the FireAttack class.
     */
    public FireAttack() {
    }

    public Skill fireballSkill;
    public Skill spitFire;

    public Skill spitFireAgressive;

    public TeleportSkill teleportSkill;

    // FireballSkill is set up
    public void setupFireballSkill() {

        fireballSkill =
            new Skill(
                new FireballSkill(
                    new ITargetSelection() {
                        @Override
                        public Point selectTargetPoint(Entity entity) {
                            return Game.hero.hitboxComponent.getCenter();
                        }
                    }),
                fireballCooldown);
        fireAttack_Logger.info("FireballSkill wurde initialisiert.");
    }

    /**
     * SpitFire Skill is set up
     */
    public void setupSpitfire() {
        spitFire =
            new Skill(
                new Spitfire(
                    new ITargetSelection() {
                        @Override
                        public Point selectTargetPoint(Entity entity) {
                            return Game.hero.hitboxComponent.getCenter();
                        }
                    }),
                spitfireCoolDown);
        fireAttack_Logger.info("SpitFire wurde initialisiert.");
    }

    /**
     * Set up the FireAgressive skill
     */
    public void setupSpitFireAgressive() {
        spitFireAgressive =
            new Skill(
                new Spitfire(
                    new ITargetSelection() {
                        @Override
                        public Point selectTargetPoint(Entity entity) {
                            return Game.hero.hitboxComponent.getCenter();
                        }
                    }),
                0);

        fireAttack_Logger.info("SpitFireAgressive wurde initialisiert.");
    }

    /**
     * Set up the Teleport skill
     */
    public void setupTeleportSkill() {
        teleportSkill =
            new TeleportSkill(
                new ISkillFunction() {
                    @Override
                    public void execute(Entity entity) {
                        teleportSkill.teleport(
                            entity, Game.hero.positionComponent.getPosition());
                    }
                },
                3);
        fireAttack_Logger.info("Teleport wurde initialisiert.");
    }

    /**
     * Checks if the entity is within a certain radius around the hero.
     *
     * @param entity
     * @return
     */
    public boolean isInRadius(Entity entity) {

        float radius = 5; // The radius that defines how far the entity can be from the hero
        float heroX =
            Game.hero.positionComponent.getPosition()
                .x; // The x-coordinate of the hero's position
        float heroY =
            Game.hero.positionComponent.getPosition()
                .y; // The y-coordinate of the hero's position
        float entityX =
            entity.positionComponent.getPosition()
                .x; // The x-coordinate of the entity's position
        float entityY =
            entity.positionComponent.getPosition()
                .y; // The y-coordinate of the entity's position

        // Calculate the squared distance between the coordinates
        float distanceSquared =
            (entityX - heroX) * (entityX - heroX) + (entityY - heroY) * (entityY - heroY);
        float radiusSquared = radius * radius; // Square of the radius

        // Check if the distance is less than or equal to the square of the radius
        return distanceSquared <= radiusSquared;
    }

    /**
     * @param entity
     * @return checks if the entity is colliding with the Hero
     */
    public boolean isColliding(Entity entity) {
        float heroHitBox = 2f;
        return (entity.positionComponent.getPosition().x + heroHitBox
            > Game.hero.positionComponent.getPosition().x
            && entity.positionComponent.getPosition().x
            < Game.hero.positionComponent.getPosition().x + heroHitBox
            && entity.positionComponent.getPosition().y + heroHitBox
            > Game.hero.positionComponent.getPosition().y
            && entity.positionComponent.getPosition().y
            < Game.hero.positionComponent.getPosition().y + heroHitBox);
    }

    // Getters and Setters

    /**
     * Returns the cooldown of the Fireball skill.
     *
     * @return The cooldown of the Fireball skill.
     */
    public float getFireballCooldown() {
        return fireballCooldown;
    }

    /**
     * Sets the cooldown of the Fireball skill.
     *
     * @param fireballCooldown The new cooldown of the Fireball skill.
     */
    public void setFireballCooldown(float fireballCooldown) {
        this.fireballCooldown = fireballCooldown;
    }

    /**
     * Returns the cooldown of the Spitfire skill.
     *
     * @return The cooldown of the Spitfire skill.
     */
    public float getSpitfireCoolDown() {
        return spitfireCoolDown;
    }

    /**
     * Sets the cooldown of the Spitfire skill.
     *
     * @param spitfireCoolDown The new cooldown of the Spitfire skill.
     */
    public void setSpitfireCoolDown(float spitfireCoolDown) {
        this.spitfireCoolDown = spitfireCoolDown;
    }

    /**
     * Returns the Skill instance of the Fireball skill.
     *
     * @return The Skill instance of the Fireball skill.
     */
    public Skill getFireballSkill() {
        return fireballSkill;
    }

    /**
     * Sets the Skill instance of the Fireball skill.
     *
     * @param fireballSkill The new Skill instance of the Fireball skill.
     */
    public void setFireballSkill(Skill fireballSkill) {
        this.fireballSkill = fireballSkill;
    }

    /**
     * Returns the Skill instance of SpitFire.
     *
     * @return The Skill instance of SpitFire.
     */
    public Skill getSpitFire() {
        return spitFire;
    }

    /**
     * Sets the Skill instance of SpitFire.
     *
     * @param spitFire The new Skill instance of SpitFire.
     */
    public void setSpitFire(Skill spitFire) {
        this.spitFire = spitFire;
    }

    /**
     * Returns the Skill instance of Aggressive SpitFire.
     *
     * @return The Skill instance of Aggressive SpitFire.
     */
    public Skill getSpitFireAgressive() {
        return spitFireAgressive;
    }

    /**
     * Sets the Skill instance of Aggressive SpitFire.
     *
     * @param spitFireAgressive The new Skill instance of Aggressive SpitFire.
     */
    public void setSpitFireAgressive(Skill spitFireAgressive) {
        this.spitFireAgressive = spitFireAgressive;
    }

    /**
     * Returns the TeleportSkill instance.
     *
     * @return The TeleportSkill instance.
     */
    public TeleportSkill getTeleportSkill() {
        return teleportSkill;
    }

    /**
     * Sets the TeleportSkill instance.
     *
     * @param teleportSkill The new TeleportSkill instance.
     */
    public void setTeleportSkill(TeleportSkill teleportSkill) {
        this.teleportSkill = teleportSkill;
    }

}
