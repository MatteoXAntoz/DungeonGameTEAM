package ecs.systems;

import ecs.components.HealthComponent;
import ecs.components.skill.SkillComponent;
import graphic.hud.HeroUI;
import starter.Game;

public class HeroUISystem extends ECS_System {
    private HealthComponent hc;
//    private ManaComponent mc;
//    private XPComponent xpc;
    private SkillComponent sc;

    public void HeroUISystem() {
        hc = (HealthComponent) Game.hero.
            getComponent(HealthComponent.class).get();

//        mc = (ManaComponent) Game.hero.
//            getComponent(ManaComponent.class).get();

//        xpc = (XPComponent) Game.hero.
//            getComponent(XPComponent.class).get();

        sc = (SkillComponent) Game.hero.
            getComponent(SkillComponent.class).get();
    }
    @Override
    public void update() {
        HeroUI heroUI = HeroUI.getInstance();
        heroUI.setHealth(hc.getCurrentHealthpoints());

        heroUI.update();
    }
}
