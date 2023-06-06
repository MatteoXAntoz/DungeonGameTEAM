package ecs.components.ai.idle;

import ecs.components.skill.*;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

import java.util.logging.Logger;

public class FireAttack {

    private final Logger fireAttack_Logger = Logger.getLogger(this.getClass().getName());
    private float fireballCooldown = 2;
    private float spitfireCoolDown = 1;


    public FireAttack() {
    }

    public Skill fireballSkill;
    public Skill spitFire;

    public Skill spitFireAgressive;

    public TeleportSkill teleportSkill;


    //FireballSkill is set up
    public void setupFireballSkill() {

        fireballSkill = new Skill(new FireballSkill(new ITargetSelection() {
            @Override
            public Point selectTargetPoint() {
                return Game.hero.positionComponent.getPosition();
            }
        }), fireballCooldown);
        fireAttack_Logger.info("FireballSkill wurde initialisiert.");
    }

    //SpitFire is set up
    public void setupSpitfire() {
        spitFire = new Skill(new Spitfire(new ITargetSelection() {
            @Override
            public Point selectTargetPoint() {
                return Game.hero.positionComponent.getPosition();
            }
        }), spitfireCoolDown);
        fireAttack_Logger.info("SpitFire wurde initialisiert.");

    }

    //SpitFireAgressive is set up
    public void setupSpitFireAgressive() {
        spitFireAgressive = new Skill(new Spitfire(new ITargetSelection() {
            @Override
            public Point selectTargetPoint() {
                return Game.hero.positionComponent.getPosition();
            }
        }), 0);

        fireAttack_Logger.info("SpitFireAgressive wurde initialisiert.");

    }

    //TeleportSkill is set up
    public void setupTeleportSkill() {
        teleportSkill = new TeleportSkill(new ISkillFunction() {
            @Override
            public void execute(Entity entity) {
                teleportSkill.teleport(entity, Game.hero.positionComponent.getPosition());
            }
        }, 3);
        fireAttack_Logger.info("Teleport wurde initialisiert.");
    }

    /**
     * @param entity
     * @return
     */
    public boolean isInRadius(Entity entity) {
        // Check if the entity is within a certain radius around the hero.

        float radius = 5; // The radius that defines how far the entity can be from the hero
        float heroX = Game.hero.positionComponent.getPosition().x; // The x-coordinate of the hero's position
        float heroY = Game.hero.positionComponent.getPosition().y; // The y-coordinate of the hero's position
        float entityX = entity.positionComponent.getPosition().x; // The x-coordinate of the entity's position
        float entityY = entity.positionComponent.getPosition().y; // The y-coordinate of the entity's position

// Calculate the squared distance between the coordinates
        float distanceSquared = (entityX - heroX) * (entityX - heroX) + (entityY - heroY) * (entityY - heroY);
        float radiusSquared = radius * radius; // Square of the radius

// Check if the distance is less than or equal to the square of the radius
        return distanceSquared <= radiusSquared;

    }

    /**
     *
     * @param entity
     * @return checks if the entity is colliding with the Hero
     */
    public boolean isColliding(Entity entity) {
        float heroHitBox = 2f;
        return (entity.positionComponent.getPosition().x + heroHitBox > Game.hero.positionComponent.getPosition().x
            && entity.positionComponent.getPosition().x < Game.hero.positionComponent.getPosition().x + heroHitBox
            && entity.positionComponent.getPosition().y + heroHitBox > Game.hero.positionComponent.getPosition().y
            && entity.positionComponent.getPosition().y < Game.hero.positionComponent.getPosition().y + heroHitBox);
    }

    //Getters and Setters
    public float getFireballCooldown() {
        return fireballCooldown;
    }

    public void setFireballCooldown(float fireballCooldown) {
        this.fireballCooldown = fireballCooldown;
    }

    public float getSpitfireCoolDown() {
        return spitfireCoolDown;
    }

    public void setSpitfireCoolDown(float spitfireCoolDown) {
        this.spitfireCoolDown = spitfireCoolDown;
    }

    public Skill getFireballSkill() {
        return fireballSkill;
    }

    public void setFireballSkill(Skill fireballSkill) {
        this.fireballSkill = fireballSkill;
    }

    public Skill getSpitFire() {
        return spitFire;
    }

    public void setSpitFire(Skill spitFire) {
        this.spitFire = spitFire;
    }

    public Skill getSpitFireAgressive() {
        return spitFireAgressive;
    }

    public void setSpitFireAgressive(Skill spitFireAgressive) {
        this.spitFireAgressive = spitFireAgressive;
    }

    public TeleportSkill getTeleportSkill() {
        return teleportSkill;
    }

    public void setTeleportSkill(TeleportSkill teleportSkill) {
        this.teleportSkill = teleportSkill;
    }
}
