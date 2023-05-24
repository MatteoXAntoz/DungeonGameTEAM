package ecs.entities;

import ecs.components.VelocityComponent;
import ecs.components.ai.idle.IIdleAI;
import graphic.Animation;


public abstract class Monster extends Entity implements IIdleAI {

    float xSpeed;
    float ySpeed;
    Animation idleLeft,idleRight;
    VelocityComponent velocityComponent;

    int damage;


    protected   String pathToIdleLeft;
    protected   String pathToIdleRight;

    protected abstract void setupPosition();
    protected abstract void setupVelocity();

    protected abstract void setupAnimation();

    protected boolean isCollidingWithHero(Hero hero) {
        float hitBoxScale = 0.6f;

        return (positionComponent.getPosition().x + hitBoxScale >  hero.positionComponent.getPosition().x  &&
            positionComponent.getPosition().x < hero.positionComponent.getPosition().x + hitBoxScale &&
            positionComponent.getPosition().y + hitBoxScale > hero.positionComponent.getPosition().y &&
            positionComponent.getPosition().y < hero.positionComponent.getPosition().y + hitBoxScale);
    }

    public static Monster getRandomMonster(){
        int ranValue = (int) (Math.random()*3);
        if(ranValue==1){
            return new Mouse();
        }
        if(ranValue==2){
            return new Chort();
        }else{
            return new Demon();
        }
    }



}
