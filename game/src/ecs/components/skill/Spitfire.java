package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Spitfire extends DamageProjectileSkill{


    public Spitfire(ITargetSelection targetSelection) {
        super(
            "skills/Spitfire/",
            0.02f,
            new Damage(1,
                DamageType.FIRE,
                null),
            new Point(10, 10), targetSelection, 0.5f);
    }
}
