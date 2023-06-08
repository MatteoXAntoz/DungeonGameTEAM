package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

/**
 * Implements specific Projektile Skill that respresents a rush of fire
 */
public class Spitfire extends DamageProjectileSkill {
    /**
     * Konstruktor
     * @param targetSelection how the direction of fire rush is selected
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
