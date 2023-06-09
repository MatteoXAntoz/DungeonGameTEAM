package ecs.systems;

import ecs.components.MeeleComponent;
import starter.Game;

/** reduces Duration of MeeleComponents and removes them */
public class MeeleSystem extends ECS_System {

    @Override
    public void update() {
        Game.getEntities().stream()
                // Consider only entities that have a MeeleComponent
                .flatMap(e -> e.getComponent(MeeleComponent.class).stream())
                // Remove all dead entities
                .forEach(
                        mc -> {
                            ((MeeleComponent) mc).reduceDuration();
                            if (!((MeeleComponent) mc).isAktive())
                                Game.removeEntity(mc.getEntity());
                        });
    }
}
