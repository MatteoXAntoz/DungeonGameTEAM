package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

public class Spitfire extends DamageProjectileSkill {
    /**
     * 
     * @param targetSelection
     */
    public Spitfire(ITargetSelection targetSelection) {
        super(
                "skills/Spitfire/",
                0.007f,
                new Damage(1, DamageType.FIRE, null),
                new Point(1, 1),
                targetSelection,
                1);
    }
}
