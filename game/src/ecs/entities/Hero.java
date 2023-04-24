package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.systems.HealthSystem;
import graphic.Animation;


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
    public SprintSkill sprintSkill;
    public HealingSkill healingSkill;

    public VelocityComponent velocityComponent;


    public HealthComponent healthComponent = new HealthComponent(this);

    /**
     * Entity with Components
     */
    public Hero() {
        super();
        new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupSprintSkill();
        setupHealingSkill();
        pc.setSkillSlot1(sprintSkill);
        pc.setSkillSlot2(healingSkill);

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
        }, 1);
    }

    private void setupHealingSkill() {
        healingSkill = new HealingSkill(new ISkillFunction() {
            @Override
            public void execute(Entity entity) {

                healingSkill.active = true;


            }
        }, 1);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
            this,
            (you, other, direction) -> System.out.println("heroCollisionEnter"),
            (you, other, direction) -> System.out.println("heroCollisionLeave"));
    }
}
