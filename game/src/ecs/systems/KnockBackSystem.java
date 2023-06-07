package ecs.systems;

import ecs.components.*;
import ecs.entities.Entity;
import starter.Game;

/**
 * reduces Duration of Knockback Components, sets Velocitys and delets them.
 */
public class KnockBackSystem extends ECS_System {
    private record HSData(Entity e, KnockBackComponent kbc, VelocityComponent vc) {}

    @Override
    public void update() {
        Game.getEntities().stream()
            // Consider only entities that have a KnockBackComponent
            .flatMap(e -> e.getComponent(KnockBackComponent.class).stream())
            // Form triples (e, kbc, vc)
            .map(kbc -> buildDataObject((KnockBackComponent) kbc))
            // ApplyKnockback
            .map(this::applyKnockback)
            // Reduce Cooldowns
            .map(this::reduceDuration)
            // Filter finished Knockbacks
            .filter(hsd -> !hsd.kbc.isAktive())
            // remove Knockback
            .forEach(this::removeKnockback);
    }

    private HSData applyKnockback(HSData hsd) {
        hsd.vc.setCurrentXVelocity(hsd.kbc.getXVelocity());
        hsd.vc.setCurrentYVelocity(hsd.kbc.getYVelocity());
        return hsd;
    }

    private HSData reduceDuration(HSData hsd) {
        hsd.kbc.reduceDuration();
        return hsd;
    }

    private void removeKnockback(HSData hsd) {
        hsd.e.removeComponent(KnockBackComponent.class);
    }

    private KnockBackSystem.HSData buildDataObject(KnockBackComponent kbc) {
        Entity e = kbc.getEntity();

        VelocityComponent vc =
            (VelocityComponent)
                e.getComponent(VelocityComponent.class)
                    .orElseThrow(() -> new MissingComponentException("VelocityComponent"));

        return new KnockBackSystem.HSData(e, kbc, vc);
    }
}
