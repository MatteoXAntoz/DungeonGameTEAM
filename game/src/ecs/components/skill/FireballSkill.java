package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class FireballSkill extends DamageProjectileSkill {

    public FireballSkill(ITargetSelection targetSelection) {
        super(
                "skills/fireball/fireBall_Down/",
                0.04f,
                new Damage(20,
                    DamageType.FIRE,
                    null),
                new Point(1, 1), targetSelection, 7);
    }
}
