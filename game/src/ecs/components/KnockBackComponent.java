package ecs.components;

import ecs.entities.Entity;

/**
 * Class holds information to apply knockback on an Entity
 */
public class KnockBackComponent extends Component {
    private final float xVelocity;
    private final float yVelocity;
    private final int duration;
    private int currentDuration;

    /**
     * Konstruktor
     *
     * @param entity that gets the Component
     * @param xVelocity Velocity the Entity gets thrown in x Direction
     * @param yVelocity Velocity the Entity gets thrown in y Direction
     * @param duration the Entity is thrown back
     */
    public KnockBackComponent(Entity entity, float xVelocity, float yVelocity, int duration) {
        super(entity);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.duration = duration;
        this.currentDuration = duration;
    }

    /**
     * reduces The current duration by one Frame.
     */
    public void reduceDuration() {
        currentDuration = Math.max(0, --currentDuration);
    }

    /**
     * Gives Information about aktiveness of the Knockback Process
     *
     * @return if the Knockback is still happening
     */
    public boolean isAktive() {
        if(currentDuration == 0)
            return false;
        return true;
    }

    /**
     * @return y  Velocity of the Knockback Component
     */
    public float getYVelocity() {
        return this.yVelocity;
    }

    /**
     * @return x  Velocity of the Knockback Component
     */
    public float getXVelocity() {
        return this.xVelocity;
    }
}
