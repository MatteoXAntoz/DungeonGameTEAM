package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.systems.HealthSystem;
import graphic.Animation;
import level.elements.TileLevel;
import level.elements.tile.Tile;
import level.elements.tile.TrapTile;
import level.tools.LevelElement;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity {



    public float xSpeed = 0.3f;
    public float ySpeed = 0.3f;


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




    public HealthComponent healthComponent = new HealthComponent(this);

    public PositionComponent positionComponent = new PositionComponent(this);


    /**
     * Entity with Components
     */
    public Hero() {
        super();

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

        healthComponent.setMaximalHealthpoints(100);
        healthComponent.setCurrentHealthpoints(100);


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
        sprintSkill = new SprintSkill(new ISkillFunction() {
            @Override
            public void execute(Entity entity) {
                sprintSkill.active = true;
            }
        }, 5);
    }

    private void setupHealingSkill() {

        healingSkill = new HealingSkill(new ISkillFunction() {
            @Override
            public void execute(Entity entity) {

                if (healingSkill.potion > 0) {
                    healthComponent.setCurrentHealthpoints(healthComponent.getCurrentHealthpoints() + healingSkill.healingBoost);
                    healingSkill.removePotion();
                }

            }
        }, 2);
    }

    private void setupHitboxComponent() {
        hitboxComponent = new HitboxComponent(this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));

    }

    public boolean isCollidingWithTrapTile(TrapTile tile) {
        float hitBoxScale = 0.6f;

        return (positionComponent.getPosition().x + hitBoxScale > tile.getCoordinateAsPoint().x &&
            positionComponent.getPosition().x < tile.getCoordinateAsPoint().x + hitBoxScale &&
            positionComponent.getPosition().y + hitBoxScale > tile.getCoordinateAsPoint().y &&
            positionComponent.getPosition().y < tile.getCoordinateAsPoint().y + hitBoxScale);
    }





}
