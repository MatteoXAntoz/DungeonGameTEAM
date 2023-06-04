package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.ISkillFunction;
import ecs.entities.Entity;
import ecs.entities.items.Item;
import level.elements.tile.ExitTile;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import level.tools.LevelElement;
import starter.Game;
import tools.Constants;
import tools.Point;

import javax.swing.plaf.PanelUI;

public class GoToLadder implements IIdleAI {

    int coolDownInFrames;
    int currentCoolDownInFrames;
    boolean ladderReached;
    public GraphPath<Tile> graphLadderPath;
    public GraphPath<Tile> graphRandomPoint;
    Point randomPoint;
    Tile randomTile;

    PatrouilleWalk patrouilleWalk;


    public GoToLadder() {
        randomTile = Game.currentLevel.getExitTiles().get(0);
        randomPoint = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
        setCoolDown(10);
        activateCoolDown();
    }


    @Override
    public void idle(Entity entity) {

        if(isOnCoolDown()){
            graphRandomPoint = null;
            if (graphLadderPath == null) {
                graphLadderPath = AITools.calculatePath(entity.positionComponent.getPosition(),
                    randomTile.getCoordinate().toPoint());
            }
            AITools.move(entity, graphLadderPath);
            reduceCoolDown();

            reduceCoolDown();
        }else {
            graphLadderPath = null;
            if (graphRandomPoint == null) {
                graphRandomPoint = AITools.calculatePath(entity.positionComponent.getPosition(),
                    randomPoint);
            }
            AITools.move(entity, graphRandomPoint);
            if(AITools.pathFinished(entity,graphRandomPoint)){
                randomPoint = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
                setCoolDown(10);
                activateCoolDown();
            }
        }




    }


    public void setCoolDown(float coolDownInSeconds) {
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;

    }

    public boolean isOnCoolDown() {
        System.out.println(currentCoolDownInFrames);
        return currentCoolDownInFrames > 0;
    }

    /**
     * activate cool down
     */
    public void activateCoolDown() {
        currentCoolDownInFrames = coolDownInFrames;
    }

    public void reduceCoolDown() {
        currentCoolDownInFrames = Math.max(0, --currentCoolDownInFrames);
    }

}
