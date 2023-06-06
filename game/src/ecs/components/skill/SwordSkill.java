package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class SwordSkill extends DamageMeeleSkill {
    public SwordSkill(ITargetSelection targetSelection) {
        super(
            "knight/attack/",
            new Damage(30, DamageType.PHYSICAL, null),
            new Point(1.0f, 1.5f),
            targetSelection
        );
    }
}
