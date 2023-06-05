package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class SwordSkill extends DamageMeeleSkill {
    public SwordSkill(ITargetSelection targetSelection) {
        super(
            "animation/",
            new Damage(1000, DamageType.PHYSICAL, null),
            new Point(3f, 1.5f),
            targetSelection
        );
    }
}
