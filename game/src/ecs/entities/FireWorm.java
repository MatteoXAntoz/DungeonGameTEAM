package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.idle.FireAttack;
import ecs.components.ai.idle.GoToLadder;
import ecs.components.ai.transition.ITransition;
import ecs.components.skill.*;
import graphic.Animation;
import java.util.logging.Logger;

/**
 * FireWorm class extends BossMonster
 *
 * @author MatteoXAntoz
 */
public class FireWorm extends BossMonster implements IFightAI, ITransition {

    private final Logger fireWorm_Logger = Logger.getLogger(this.getClass().getName());
    private SkillComponent skillComponent; // Skill-Komponente für die FireWorm-Entität.
    private FireAttack
            fireAttack; // Instanz der FireAttack-Klasse für den Angriff der FireWorm-Entität.
    private GoToLadder
            goToLadder; // Instanz der GoToLadder-Klasse für das Verhalten der FireWorm-Entität.
    private Animation walkLeft, walkRight; // Animationen für das Gehen nach links und rechts.
    private AnimationComponent
            animationComponent; // Komponente zur Verwaltung der Animationen der FireWorm-Entität.
    private String pathToWalkingLeft; // Pfad zur Animation für das Gehen nach links.
    private String pathToWalkingRight; // Pfad zur Animation für das Gehen nach rechts.

    /**
     * Constructor for the FireWorm class. Creates an instance of FireWorm and initializes its
     * properties.
     */
    public FireWorm() {
        super();

        fireAttack = new FireAttack();
        goToLadder = new GoToLadder();
        pathToIdleLeft = "fire-worm/idleLeft";
        pathToIdleRight = "fire-worm/idleRight";

        pathToWalkingLeft = "fire-worm/walkLeft";
        pathToWalkingRight = "fire-worm/walkRight";

        setupHealthComponent();
        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAI();
        setupSkillComponent();
    }

    // Setup of skillComponent and Skills
    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);
        fireAttack.setupSpitfire();
        fireAttack.setupFireballSkill();
        fireAttack.setupTeleportSkill();
        fireAttack.setupSpitFireAgressive();
        skillComponent.addSkill(fireAttack.getFireballSkill());
        fireWorm_Logger.info("FireballSkill was added");
        skillComponent.addSkill(fireAttack.getSpitFire());
        fireWorm_Logger.info("SpitFire was added");
        skillComponent.addSkill(fireAttack.getTeleportSkill());
        fireWorm_Logger.info("TeleportSkill was added");
        skillComponent.addSkill(fireAttack.getSpitFireAgressive());
        fireWorm_Logger.info("SpitFireAgressive was added");
    }

    /** {@inheritDoc} */
    @Override
    public void idle(Entity entity) {
        goToLadder.idle(entity);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupPosition() {}

    /** {@inheritDoc} */
    @Override
    protected void setupVelocity() {
        xSpeed = 0.4f;
        ySpeed = 0.4f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, walkLeft, walkRight);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupAnimation() {
        walkLeft = AnimationBuilder.buildAnimation(pathToWalkingLeft);
        walkRight = AnimationBuilder.buildAnimation(pathToWalkingRight);

        animationComponent = new AnimationComponent(this, walkLeft, walkRight);
    }

    /** {@inheritDoc} MaxHealth is set up to 250 CurrentHealth is set up to 250 as well */
    @Override
    protected void setupHealthComponent() {
        healthComponent = new HealthComponent(this);
        healthComponent.setMaximalHealthpoints(120);
        healthComponent.setCurrentHealthpoints(120);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupAI() {
        new AIComponent(this, this, this, this);
    }

    /**
     * {@inheritDoc} Initiates a fight with the specified entity.
     *
     * @param entity The entity to fight against.
     */
    @Override
    /**
     * Initiates a fight with the specified entity.
     *
     * @param entity The entity to fight against.
     */
    public void fight(Entity entity) {
        if (!isSecondStage()) {
            // If in range and colliding with the entity, execute SpitFire attack
            if (getFireAttack().isInRadius(entity) && getFireAttack().isColliding(entity)) {
                getFireAttack().getSpitFire().execute(entity);
            }
            // If in range but not colliding with the entity, execute FireballSkill attack
            else if (getFireAttack().isInRadius(entity) && !getFireAttack().isColliding(entity)) {
                getFireAttack().getFireballSkill().execute(entity);
            }
        } else {
            // Set the X and Y velocity to 0.05f in second stage
            velocityComponent.setXVelocity(0.05f);
            velocityComponent.setYVelocity(0.05f);
            // If in range and colliding with the entity, execute aggressive SpitFire attack
            if (getFireAttack().isInRadius(entity) && getFireAttack().isColliding(entity)) {
                getFireAttack().getSpitFireAgressive().execute(entity);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isInFightMode(Entity entity) {
        if (getFireAttack().isInRadius(entity)) {
            return true;
        } else if (getFireAttack().isColliding(entity)) {
            return true;
        }
        return false;
    }

    // isSecondStage checks, if the monster lose half of the healthpoints
    private boolean isSecondStage() {
        return (healthComponent.getCurrentHealthpoints()
                < (healthComponent.getMaximalHealthpoints() / 2));
    }

    /**
     * @return skillComponent
     */
    public SkillComponent getSkillComponent() {
        return skillComponent;
    }

    /**
     * @param skillComponent
     */
    public void setSkillComponent(SkillComponent skillComponent) {
        this.skillComponent = skillComponent;
    }

    /**
     * @return fireAttack
     */
    public FireAttack getFireAttack() {
        return fireAttack;
    }

    /**
     * @param fireAttack
     */
    public void setFireAttack(FireAttack fireAttack) {
        this.fireAttack = fireAttack;
    }

    /**
     * @return goToLadder
     */
    public GoToLadder getGoToLadder() {
        return goToLadder;
    }

    /**
     * @param goToLadder
     */
    public void setGoToLadder(GoToLadder goToLadder) {
        this.goToLadder = goToLadder;
    }

    /**
     * @return walkLeft
     */
    public Animation getWalkLeft() {
        return walkLeft;
    }

    /**
     * @param walkLeft
     */
    public void setWalkLeft(Animation walkLeft) {
        this.walkLeft = walkLeft;
    }

    /**
     * @return walkRight
     */
    public Animation getWalkRight() {
        return walkRight;
    }

    /**
     * @param walkRight
     */
    public void setWalkRight(Animation walkRight) {
        this.walkRight = walkRight;
    }

    /**
     * @return animationComponent
     */
    public AnimationComponent getAnimationComponent() {
        return animationComponent;
    }

    /**
     * @param animationComponent
     */
    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent = animationComponent;
    }

    /**
     * @return pathToWalkingLeft
     */
    public String getPathToWalkingLeft() {
        return pathToWalkingLeft;
    }

    /**
     * @param pathToWalkingLeft
     */
    public void setPathToWalkingLeft(String pathToWalkingLeft) {
        this.pathToWalkingLeft = pathToWalkingLeft;
    }

    /**
     * @return pathToWalkingRight
     */
    public String getPathToWalkingRight() {
        return pathToWalkingRight;
    }

    /**
     * @param pathToWalkingRight
     */
    public void setPathToWalkingRight(String pathToWalkingRight) {
        this.pathToWalkingRight = pathToWalkingRight;
    }
}
