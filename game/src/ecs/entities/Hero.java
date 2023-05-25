package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.items.*;
import ecs.systems.PlayerSystem;
import graphic.Animation;
import level.SaveLoadGame;
import level.elements.tile.FloorTile;
import starter.Game;

/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {

    public boolean playerHasBag;

    Game game;
    PlayerSystem playerSystem;
    public float xSpeed = 0.3f;
    public float ySpeed = 0.3f;

    private MyInventory myInventory;

    public String bagName;
    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";

    //
    public SprintSkill sprintSkill;

    //
    public HealingSkill healingSkill;
    public SkillComponent skillComponent = new SkillComponent(this);

    public VelocityComponent velocityComponent;

    public HitboxComponent hitboxComponent;

    public HealthComponent healthComponent;

    public PositionComponent positionComponent = new PositionComponent(this);

    /** Entity with Components */
    public Hero() {
        super();

        setupInventory();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);

        setupSprintSkill();
        setupHealingSkill();

        pc.setSkillSlot1(sprintSkill);
        pc.setSkillSlot2(healingSkill);

        skillComponent.addSkill(sprintSkill);
        skillComponent.addSkill(healingSkill);

        setupHealthComponent();

        // Der Inventarplatz vom Spieler wird auf wird auf 10 gesetzt
        getMyInventory().setMaxSpace(10);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupSprintSkill() {
        sprintSkill =
                new SprintSkill(
                        new ISkillFunction() {
                            @Override
                            public void execute(Entity entity) {
                                sprintSkill.active = true;
                            }
                        },
                        5);
    }

    private void setupHealingSkill() {

        healingSkill =
                new HealingSkill(
                        new ISkillFunction() {
                            @Override
                            public void execute(Entity entity) {

                                if (healingSkill.potion > 0) {
                                    healthComponent.setCurrentHealthpoints(
                                            healthComponent.getCurrentHealthpoints()
                                                    + healingSkill.healingBoost);
                                    healingSkill.removePotion();
                                }
                            }
                        },
                        2);
    }

    private void setupHitboxComponent() {
        hitboxComponent =
                new HitboxComponent(
                        this,
                        (you, other, direction) -> System.out.println("heroCollisionEnter"),
                        (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }

    private void setupHealthComponent() {
        healthComponent = new HealthComponent(this);

        healthComponent.setMaximalHealthpoints(100);

        if (!SaveLoadGame.isEmpty(SaveLoadGame.PATH, SaveLoadGame.PLAYER_DATA)) {
            healthComponent.setCurrentHealthpoints(SaveLoadGame.loadHeroHealth());
        } else {
            healthComponent.setCurrentHealthpoints(100);
        }
    }

    public boolean isCollidingWithTrapTile(FloorTile tile) {
        float hitBoxScale = 0.6f;

        return (positionComponent.getPosition().x + hitBoxScale > tile.getCoordinateAsPoint().x
                && positionComponent.getPosition().x < tile.getCoordinateAsPoint().x + hitBoxScale
                && positionComponent.getPosition().y + hitBoxScale > tile.getCoordinateAsPoint().y
                && positionComponent.getPosition().y < tile.getCoordinateAsPoint().y + hitBoxScale);
    }

    public boolean isCollidingWithItems(Item item) {
        float hitBoxScale = 0.6f;
        return (positionComponent.getPosition().x + hitBoxScale
                        > item.getPositionComponent().getPosition().x
                && positionComponent.getPosition().x
                        < item.getPositionComponent().getPosition().x + hitBoxScale
                && positionComponent.getPosition().y + hitBoxScale
                        > item.getPositionComponent().getPosition().y
                && positionComponent.getPosition().y
                        < item.getPositionComponent().getPosition().y + hitBoxScale);
    }

    private void setupInventory() {
        myInventory = new MyInventory();
    }

    public MyInventory getMyInventory() {
        return myInventory;
    }

    public void setMyInventory(MyInventory myInventory) {
        this.myInventory = myInventory;
    }
}
