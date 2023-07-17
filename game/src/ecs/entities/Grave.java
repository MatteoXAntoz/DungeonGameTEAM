package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import graphic.Animation;
import starter.Game;

public class Grave extends Entity implements IInteraction {

    ecs.entities.Ghost ghost;

    Animation idle;
    InteractionComponent interactionComponent;

    Hero hero = Game.hero;

    private final String idlePath = "grave-no_background.png";

    public Grave() {
        super();
        setupAnimationComponent();
        setupPosition();
        setupInteraction();
        setupInteractionComponent();
    }

    public void setupPosition() {
        positionComponent = new PositionComponent(this);
    }

    private void setupInteraction() {
        interactionComponent = new InteractionComponent(this, 0.5f, false, this);
    }

    private void setupAnimationComponent() {

        idle = AnimationBuilder.buildAnimation(idlePath);

        new AnimationComponent(this, idle);
    }

    private void setupInteractionComponent() {
        interactionComponent = new InteractionComponent(this, 0.5f, false, this);
    }

    @Override
    public void onInteraction(Entity entity) {

        new Ghost();
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }
}
