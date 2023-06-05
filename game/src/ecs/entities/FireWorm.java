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

public class FireWorm extends BossMonster implements IFightAI, ITransition {


    private SkillComponent skillComponent; // Skill-Komponente für die FireWorm-Entität.
    private FireAttack fireAttack; // Instanz der FireAttack-Klasse für den Angriff der FireWorm-Entität.
    private GoToLadder goToLadder; // Instanz der GoToLadder-Klasse für das Verhalten der FireWorm-Entität.
    private Animation walkLeft, walkRight; // Animationen für das Gehen nach links und rechts.
    private AnimationComponent animationComponent; // Komponente zur Verwaltung der Animationen der FireWorm-Entität.
    private String pathToWalkingLeft; // Pfad zur Animation für das Gehen nach links.
    private String pathToWalkingRight; // Pfad zur Animation für das Gehen nach rechts.

    /**
     *
     */

    public FireWorm() {


        fireAttack = new FireAttack();
        goToLadder = new GoToLadder();
        pathToIdleLeft = "fire-worm/idleLeft";
        pathToIdleRight = "fire-worm/idleRight";

        pathToWalkingLeft = "fire-worm/walkLeft";
        pathToWalkingRight = "fire-worm/walkRight";

        setupHealthcomponent();
        setupAnimation();
        setupPosition();
        setupVelocity();
        setupAi();
        setupSkillComponent();


    }

    //Setup of skillComponent and Skills
    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);
        fireAttack.setupSpitfire();
        fireAttack.setupFireballSkill();
        fireAttack.setupTeleportSkill();
        fireAttack.setupSpitFireAgressive();
        skillComponent.addSkill(fireAttack.getFireballSkill());
        skillComponent.addSkill(fireAttack.getSpitFire());
        skillComponent.addSkill(fireAttack.getTeleportSkill());
        skillComponent.addSkill(fireAttack.getSpitFireAgressive());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void idle(Entity entity) {
        goToLadder.idle(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setupPosition() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setupVelocity() {
        xSpeed = 0.4f;
        ySpeed = 0.4f;
        velocityComponent = new VelocityComponent(this, xSpeed, ySpeed, walkLeft, walkRight);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setupAnimation() {
        walkLeft = AnimationBuilder.buildAnimation(pathToWalkingLeft);
        walkRight = AnimationBuilder.buildAnimation(pathToWalkingRight);

        animationComponent = new AnimationComponent(this, walkLeft, walkRight);

    }

    /**
     * {@inheritDoc}
     * MaxHealth is set up to 250
     * CurrentHealth is set up to 250 as well
     */
    @Override
    protected void setupHealthcomponent() {
        healthComponent = new HealthComponent(this);
        healthComponent.setMaximalHealthpoints(250);
        healthComponent.setCurrentHealthpoints(250);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setupAi() {
        new AIComponent(this, this, this, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fight(Entity entity) {

        if (!isSecondStage()) {
            if (getFireAttack().isInRadius(entity) && getFireAttack().isColliding(entity)) {
                getFireAttack().getSpitFire().execute(entity);
            } else if (getFireAttack().isInRadius(entity) && !getFireAttack().isColliding(entity)) {
                getFireAttack().getFireballSkill().execute(entity);
            }
        } else {
            velocityComponent.setXVelocity(0.08f);
            velocityComponent.setYVelocity(0.08f);
            if (getFireAttack().isInRadius(entity) && getFireAttack().isColliding(entity)) {
                getFireAttack().getSpitFireAgressive().execute(entity);
            }
        }
        System.out.println(isSecondStage());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInFightMode(Entity entity) {
        if (getFireAttack().isInRadius(entity)) {
            return true;
        } else if (getFireAttack().isColliding(entity)) {
            return true;
        }
        return false;
    }

    //Checks if the secondStage is active
    private boolean isSecondStage() {
        return (healthComponent.getCurrentHealthpoints() < (healthComponent.getMaximalHealthpoints() / 2));
    }

    /**
     *
     * @return skillComponent
     */
    public SkillComponent getSkillComponent() {
        return skillComponent;
    }
    /**
     *
     * @param skillComponent
     */
    public void setSkillComponent(SkillComponent skillComponent) {
        this.skillComponent = skillComponent;
    }

    /**
     *
     * @return fireAttack
     */
    public FireAttack getFireAttack() {
        return fireAttack;
    }

    /**
     *
     * @param fireAttack
     */
    public void setFireAttack(FireAttack fireAttack) {
        this.fireAttack = fireAttack;
    }

    /**
     *
     * @return goToLadder
     */
    public GoToLadder getGoToLadder() {
        return goToLadder;
    }

    /**
     *
     * @param goToLadder
     */
    public void setGoToLadder(GoToLadder goToLadder) {
        this.goToLadder = goToLadder;
    }

    /**
     *
     * @return walkLeft
     */
    public Animation getWalkLeft() {
        return walkLeft;
    }

    /**
     *
     * @param walkLeft
     */
    public void setWalkLeft(Animation walkLeft) {
        this.walkLeft = walkLeft;
    }

    /**
     *
     * @return walkRight
     */
    public Animation getWalkRight() {
        return walkRight;
    }

    /**
     *
     * @param walkRight
     */
    public void setWalkRight(Animation walkRight) {
        this.walkRight = walkRight;
    }

    /**
     *
     * @return animationComponent
     */
    public AnimationComponent getAnimationComponent() {
        return animationComponent;
    }

    /**
     *
     * @param animationComponent
     */
    public void setAnimationComponent(AnimationComponent animationComponent) {
        this.animationComponent = animationComponent;
    }

    /**
     *
     * @return pathToWalkingLeft
     */
    public String getPathToWalkingLeft() {
        return pathToWalkingLeft;
    }

    /**
     *
     * @param pathToWalkingLeft
     */
    public void setPathToWalkingLeft(String pathToWalkingLeft) {
        this.pathToWalkingLeft = pathToWalkingLeft;
    }

    /**
     *
     * @return pathToWalkingRight
     */
    public String getPathToWalkingRight() {
        return pathToWalkingRight;
    }

    /**
     *
     * @param pathToWalkingRight
     */
    public void setPathToWalkingRight(String pathToWalkingRight) {
        this.pathToWalkingRight = pathToWalkingRight;
    }
}
