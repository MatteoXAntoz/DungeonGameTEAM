package ecs.components;

import ecs.entities.Entity;

/**
 * Component that Manages the deletion of MeeleAttack Entities
 */
public class MeeleComponent extends Component {
    private final int animationFramesDuration;
    private int currentDuration;

    /**
     * Manages the removal of temporary meele Entities.
     *
     * @param entity meele
     * @param animationFramesDuration time in Frames until the entity gets removed,
     *                                should be equal to the frames the animation takes.
     */
    public MeeleComponent(Entity entity, int animationFramesDuration) {
        super(entity);
        this.animationFramesDuration = animationFramesDuration;
        this.currentDuration = animationFramesDuration;
    }

    /**
     * reduces The current duration by one Frame.
     */
    public void reduceDuration() {
        currentDuration = Math.max(0, --currentDuration);
    }

    /**
     * Gives Information about aktiveness of the meele Atack
     *
     * @return if the meele Atack is Aktive
     */
    public boolean isAktive() {
        if(currentDuration == 0) {
            return false;
        }
        return true;
    }
}
