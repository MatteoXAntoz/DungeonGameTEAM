package ecs.entities;

import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.idle.IIdleAI;
import graphic.Animation;

/**
 * abstract class to create a template to build monster
 */
public abstract class Monster extends Entity implements IIdleAI {
    public Monster()
    {
        super();
        setUpHitboxComponent();
    }

    protected float xSpeed;
    protected float ySpeed;
    protected Animation idleLeft, idleRight;
    protected VelocityComponent velocityComponent;
    protected HealthComponent healthComponent;

    protected int damage;

    /**
     * String to the path of the assets for the animation
     */
    protected String pathToIdleLeft;

    /**
     * String to the path of the assets for the animation
     */
    protected String pathToIdleRight;

    protected abstract void setupPosition();

    protected abstract void setupVelocity();

    protected abstract void setupAnimation();
    protected abstract void setupHealthComponent();

    protected abstract void setupAI();

    protected void setUpHitboxComponent() {
        new HitboxComponent(this);
    }


    /**
     * method to check if monster is colliding with hero
     *
     * @param hero
     * @return position
     */
    protected boolean isCollidingWithHero(Hero hero) {
        float hitBoxScale = 0.6f;

        return (positionComponent.getPosition().x + hitBoxScale
            > hero.positionComponent.getPosition().x
            && positionComponent.getPosition().x
            < hero.positionComponent.getPosition().x + hitBoxScale
            && positionComponent.getPosition().y + hitBoxScale
            > hero.positionComponent.getPosition().y
            && positionComponent.getPosition().y
            < hero.positionComponent.getPosition().y + hitBoxScale);
    }

    /**
     * method to return a random monster either of the type demon, mouse or chort
     *
     * @return a monster of type Monster
     */
    public static Monster getRandomMonster() {
        int ranValue = (int) (Math.random() * 3);
        if (ranValue == 1) {
            return new Mouse();
        }
        if (ranValue == 2) {
            return new Chort();
        } else {
            return new Demon();
        }
    }



}
