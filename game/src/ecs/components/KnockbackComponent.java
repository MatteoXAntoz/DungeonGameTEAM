package ecs.components;

import ecs.entities.Entity;

public class KnockbackComponent extends Component {
    private final float xVelocity;
    private final float yVelocity;
    private final int duration;
    private int currentDuration;
    public KnockbackComponent(Entity entity, float xVelocity, float yVelocity, int duration) {
        super(entity);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.duration = duration;
        this.currentDuration = duration;
    }

    public void reduceDuration() {
        currentDuration = Math.max(0, --currentDuration);
    }

    public boolean isAktive() {
        if(currentDuration == 0)
            return false;
        return true;
    }

    public float getYVelocity() {
        return this.yVelocity;
    }
    public float getXVelocity() {
        return this.xVelocity;
    }
}
