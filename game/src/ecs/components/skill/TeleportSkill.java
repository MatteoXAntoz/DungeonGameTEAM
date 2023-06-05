package ecs.components.skill;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import tools.Point;

public class TeleportSkill extends Skill{
    /**
     * @param skillFunction  Function of this skill
     * @param coolDownInSeconds
     */

    
    public TeleportSkill(ISkillFunction skillFunction, float coolDownInSeconds) {
        super(skillFunction, coolDownInSeconds);
    }

    /**
     *
     * @param from, is the entity which teleport
     * @param to,is the Point which @ from  is teleported to
     */
    public void teleport(Entity from,Point to){
      if(from.getComponent(PositionComponent.class).isPresent()){
          PositionComponent positionComponent = (PositionComponent) from.getComponent(PositionComponent.class).get();
          positionComponent.setPosition(to.toCoordinate().toPoint());
      }
    }

    /**
     *
     * @param from, is the entity which teleport
     * @param to,is the Entity which @ from is teleported to
     */
    public void teleport(Entity from, Entity to){
        if(from.getComponent(PositionComponent.class).isPresent() && to.getComponent(PositionComponent.class).isPresent()){
            PositionComponent positionComponentFrom = (PositionComponent) from.getComponent(PositionComponent.class).get();
            PositionComponent positionComponentTo= (PositionComponent) from.getComponent(PositionComponent.class).get();
            positionComponentFrom.setPosition(positionComponentTo.getPosition());
        }
    }
}
