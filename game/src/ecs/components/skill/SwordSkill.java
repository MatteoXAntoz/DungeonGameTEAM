package ecs.components.skill;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import tools.Point;

/** Calss that implements the specific MeeleSkill SwordSkill */
public class SwordSkill extends DamageMeeleSkill {
    /**
     * Konstruktor for the SwordSkill
     *
     * @param targetSelection how the direction of the sword should be given
     */
    public SwordSkill(ITargetSelection targetSelection) {
        super(
                "knight/attack/",
                new Damage(30, DamageType.PHYSICAL, null),
                new Point(1.5f, 1.5f),
                0.15f,
                10,
                9,
                targetSelection);
    }
}
